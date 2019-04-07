package com.werfad;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.colors.EditorFontType;
import com.werfad.utils.EditorUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class MarksCanvas extends JComponent {
    private final UserConfig.DataBean config;
    private List<Mark> mMarks;
    private List<Mark> mSortedMarks;
    private Editor mEditor;
    private Font mFont;
    private FontMetrics mFontMetrics;

    public final void sync(@NotNull Editor e) {
        Rectangle visibleArea = e.getScrollingModel().getVisibleArea();
        setBounds(visibleArea.x, visibleArea.y, visibleArea.width, visibleArea.height);
        mEditor = e;
        mFont = e.getColorsScheme().getFont(EditorFontType.BOLD);
        mFontMetrics = e.getContentComponent().getFontMetrics(mFont);
    }

    public final void setData(@NotNull List<Mark> marks) {
        mMarks = marks;
        mSortedMarks = Collections.emptyList();
        repaint();
    }

    @Override
    public void paint(@NotNull Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        calcCoordinate(g);

        if (mSortedMarks.isEmpty()) {
            mSortedMarks = new ArrayList<>(mMarks);
            mSortedMarks.sort((o1, o2) -> {
                assert o1.markStart != null;
                assert o2.markStart != null;
                return o1.markStart.x - o2.markStart.x;
            });
        }

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        for (Mark mark : mSortedMarks) {
            g2d.setColor(new Color(config.getBgColor2(), true));
            assert mark.markStart != null;
            assert mark.strBounds != null;
            g2d.fillRect(mark.markStart.x - getX(), mark.markStart.y - getY(),
                    mark.strBounds.width, mark.strBounds.height);

            g2d.setFont(mFont);
            g2d.setColor(new Color(config.getFontColor2(), true));
            g2d.drawString(mark.keyTag, mark.markStart.x - getX(),
                    mark.markStart.y - getY() + mark.strBounds.height - mFontMetrics.getDescent());
        }

        super.paint(g);
    }

    private void calcCoordinate(Graphics g) {
        for (Mark it : mMarks) {
            if (it.strBounds == null) {
                it.strBounds = mFontMetrics.getStringBounds(it.keyTag, g).getBounds();
            }

            if (it.markStart == null) {
                it.markStart = EditorUtils.offsetToXYCompat(mEditor, it.offset);
            }
        }
    }

    public MarksCanvas() {
        config = UserConfig.getDataBean();
    }

    public static class Mark {
        private String keyTag;
        private final int offset;
        @Nullable
        private Rectangle strBounds;
        @Nullable
        private Point markStart;

        public Mark(@NotNull String keyTag, int offset) {
            this.keyTag = keyTag;
            this.offset = offset;
        }

        public void setKeyTag(String keyTag) {
            this.keyTag = keyTag;
            strBounds = null;
        }

        public String getKeyTag() {
            return keyTag;
        }

        public int getOffset() {
            return offset;
        }

        @Nullable
        public Rectangle getStrBounds() {
            return strBounds;
        }

        public void setStrBounds(@Nullable Rectangle strBounds) {
            this.strBounds = strBounds;
        }

        @Nullable
        public Point getMarkStart() {
            return markStart;
        }

        public void setMarkStart(@Nullable Point markStart) {
            this.markStart = markStart;
        }
    }
}
