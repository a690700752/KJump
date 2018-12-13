package com.werfad.utils;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.editor.ScrollingModel;
import com.intellij.openapi.editor.VisualPosition;
import com.intellij.openapi.util.TextRange;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public final class EditorUtils {
    @NotNull
    public static TextRange getVisibleRangeOffset(@NotNull Editor editor) {
        ScrollingModel scrollingModel = editor.getScrollingModel();
        Rectangle visibleArea = scrollingModel.getVisibleArea();

        LogicalPosition startLog = editor.xyToLogicalPosition(new Point(0, visibleArea.y));
        LogicalPosition lastLog = editor.xyToLogicalPosition(new Point(0, visibleArea.y + visibleArea.height));

        int startOff = editor.logicalPositionToOffset(startLog);
        int endOff = editor.logicalPositionToOffset(new LogicalPosition(lastLog.line + 1, lastLog.column));
        return new TextRange(startOff, endOff);
    }

    @NotNull
    public static Point offsetToXYCompat(@NotNull Editor editor, int offset) {
        return offsetToXYCompat(editor, offset, false, false);
    }

    @NotNull
    public static Point offsetToXYCompat(@NotNull Editor editor, int offset, boolean leanForward, boolean beforeSoftWrap) {
        VisualPosition visualPosition = editor.offsetToVisualPosition(offset, leanForward, beforeSoftWrap);
        return editor.visualPositionToXY(visualPosition);
    }
}
