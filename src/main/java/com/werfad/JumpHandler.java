package com.werfad;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.actionSystem.TypedAction;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import com.intellij.openapi.util.TextRange;
import com.werfad.finder.*;
import com.werfad.utils.EditorUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.intellij.openapi.actionSystem.IdeActions.ACTION_GOTO_DECLARATION;


public final class JumpHandler implements TypedActionHandler {
    private static final JumpHandler INSTANCE = new JumpHandler();

    static final int MODE_CHAR1 = 0;
    static final int MODE_CHAR2 = 1;
    static final int MODE_WORD0 = 2;
    static final int MODE_WORD1 = 3;
    static final int MODE_LINE = 4;
    static final int MODE_WORD1_DECLARATION = 5;

    private static TypedActionHandler mOldTypedHandler;
    private static EditorActionHandler mOldEscActionHandler;
    private static final MarksCanvas mMarksCanvas = new MarksCanvas();

    private static boolean isStart;

    private static Finder finder;
    private static Optional<Runnable> onJump = Optional.empty(); // Runnable that is called after jump

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

    private final EditorActionHandler escActionHandler = new EditorActionHandler() {
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
            onJump.orElse(() -> {}).run();
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
    public final void start(int mode, AnActionEvent anActionEvent) {
        if (isStart) return;
        isStart = true;
        Editor editor = anActionEvent.getData(CommonDataKeys.EDITOR);
        if (editor == null) return;

        EditorActionManager manager = EditorActionManager.getInstance();
        TypedAction typedAction = manager.getTypedAction();

        mOldTypedHandler = typedAction.getRawHandler();
        typedAction.setupRawHandler(this);

        mOldEscActionHandler = manager.getActionHandler(IdeActions.ACTION_EDITOR_ESCAPE);
        manager.setActionHandler(IdeActions.ACTION_EDITOR_ESCAPE, escActionHandler);

        onJump = Optional.empty();

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
            case MODE_WORD1_DECLARATION:
                finder = new Word1Finder();
                onJump = Optional.of(() -> ActionManager
                        .getInstance()
                        .getAction(ACTION_GOTO_DECLARATION)
                        .actionPerformed(anActionEvent));
                break;
            default:
                throw new RuntimeException("Invalid start mode: " + mode);
        }

        TextRange visibleBorderOffset = EditorUtils.getVisibleRangeOffset(editor);
        String visibleString = editor.getDocument().getText(visibleBorderOffset);
        List<MarksCanvas.Mark> marks = finder.start(editor, visibleString, visibleBorderOffset);
        if (marks != null) {
            lastMarks = marks;
            this.jumpOrShowCanvas(editor, lastMarks);
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
