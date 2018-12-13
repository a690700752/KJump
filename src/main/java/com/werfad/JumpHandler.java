package com.werfad;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.IdeActions;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.actionSystem.TypedAction;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import com.intellij.openapi.util.TextRange;
import com.werfad.finder.Char1Finder;
import com.werfad.finder.Char2Finder;
import com.werfad.finder.Finder;
import com.werfad.finder.LineFinder;
import com.werfad.finder.Word0Finder;
import com.werfad.finder.Word1Finder;
import com.werfad.utils.EditorUtils;

import java.awt.Container;
import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class JumpHandler implements TypedActionHandler {
    private static final JumpHandler INSTANCE = new JumpHandler();

    static final int MODE_CHAR1 = 0;
    static final int MODE_CHAR2 = 1;
    static final int MODE_WORD0 = 2;
    static final int MODE_WORD1 = 3;
    static final int MODE_LINE = 4;

    private static TypedActionHandler mOldTypedHandler;
    private static EditorActionHandler mOldEscActionHandler;
    private static final MarksCanvas mMarksCanvas = new MarksCanvas();

    private static boolean isStart;

    private static Finder finder;

    private static List<MarksCanvas.Mark> lastMarks = Collections.emptyList();
    private static boolean isCanvasAdded = false;

    static JumpHandler getInstance() {
        return INSTANCE;
    }

    public void execute(@NotNull Editor e, char c, @NotNull DataContext dc) {
        List<MarksCanvas.Mark> marks = finder.input(e, c, lastMarks);
        if (marks != null) {
            lastMarks = marks;
            jumpOrShowCanvas(e, lastMarks);
        }
    }

    private EditorActionHandler escActionHandler = new EditorActionHandler() {
        @Override
        protected void doExecute(@NotNull Editor editor, @Nullable Caret caret, DataContext dataContext) {
            stop();
        }
    };

    private void jumpOrShowCanvas(Editor e, List<MarksCanvas.Mark> marks) {
        if (marks.isEmpty()) {
            stop();
        } else if (marks.size() == 1) {
            // only one found, just jump to it
            e.getCaretModel().moveToOffset(marks.get(0).getOffset());
            stop();
        } else {
            if (!isCanvasAdded) {
                mMarksCanvas.sync(e);
                e.getContentComponent().add(mMarksCanvas);
                e.getContentComponent().repaint();
                isCanvasAdded = true;
            }

            mMarksCanvas.setData(marks);
        }

    }

    /**
     * start search mode
     *
     * @param mode mode enum, see {@link #MODE_CHAR1} {@link #MODE_CHAR2} etc
     */
    public final void start(@NotNull Editor e, int mode) {
        if (!isStart) {
            isStart = true;
            EditorActionManager manager = EditorActionManager.getInstance();
            TypedAction typedAction = manager.getTypedAction();

            mOldTypedHandler = typedAction.getRawHandler();
            typedAction.setupRawHandler(this);

            mOldEscActionHandler = manager.getActionHandler(IdeActions.ACTION_EDITOR_ESCAPE);
            manager.setActionHandler(IdeActions.ACTION_EDITOR_ESCAPE, escActionHandler);

            switch (mode) {
                case MODE_CHAR1:
                    finder = new Char1Finder();
                    break;
                case MODE_CHAR2:
                    finder = new Char2Finder();
                    break;
                case MODE_WORD0:
                    finder = new Word0Finder();
                    break;
                case MODE_WORD1:
                    finder = new Word1Finder();
                    break;
                case MODE_LINE:
                    finder = new LineFinder();
                    break;
                default:
                    throw new RuntimeException("Invalid start mode: " + mode);
            }

            TextRange visibleBorderOffset = EditorUtils.getVisibleRangeOffset(e);
            String visibleString = e.getDocument().getText(visibleBorderOffset);
            List<MarksCanvas.Mark> marks = finder.start(e, visibleString, visibleBorderOffset);
            if (marks != null) {
                lastMarks = marks;
                this.jumpOrShowCanvas(e, lastMarks);
            }
        }
    }

    private void stop() {
        if (isStart) {
            isStart = false;
            EditorActionManager manager = EditorActionManager.getInstance();
            manager.getTypedAction().setupRawHandler(mOldTypedHandler);
            if (mOldEscActionHandler != null) {
                manager.setActionHandler(IdeActions.ACTION_EDITOR_ESCAPE, mOldEscActionHandler);
            }

            Container parent = mMarksCanvas.getParent();
            if (parent != null) {
                parent.remove(mMarksCanvas);
                parent.repaint();
            }

            isCanvasAdded = false;
        }
    }
}
