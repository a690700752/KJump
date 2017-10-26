package com.werfad.utils;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.editor.ScrollingModel;
import com.intellij.openapi.util.TextRange;

import java.awt.*;
import java.util.List;

public class EditorUtils {

    public static int[] getVisibleBorderOffset(Editor editor) {
        ScrollingModel scrollingModel = editor.getScrollingModel();
        Rectangle visibleArea = scrollingModel.getVisibleArea();

        LogicalPosition startLog = editor.xyToLogicalPosition(new Point(0, visibleArea.y));
        LogicalPosition lastLog = editor.xyToLogicalPosition(new Point(0, visibleArea.y + visibleArea.height));

        int startOff = editor.logicalPositionToOffset(startLog);
        int endOff = editor.logicalPositionToOffset(new LogicalPosition(lastLog.line + 1, lastLog.column));
        return new int[]{startOff, endOff};
    }

    public static List<Integer> findCharsInVisibleArea(Editor editor, String find) {
        int[] borderOffset = getVisibleBorderOffset(editor);
        String text = editor.getDocument().getText(new TextRange(borderOffset[0], borderOffset[1]));

        return StringUtils.findAll(text, find);
    }

}
