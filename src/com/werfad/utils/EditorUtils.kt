package com.werfad.utils

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.LogicalPosition
import com.intellij.openapi.util.TextRange
import java.awt.Point

fun Editor.getVisibleBorderOffset(): IntArray {
    val visibleArea = scrollingModel.visibleArea

    val startLog = xyToLogicalPosition(Point(0, visibleArea.y))
    val lastLog = xyToLogicalPosition(Point(0, visibleArea.y + visibleArea.height))

    val startOff = logicalPositionToOffset(startLog)
    val endOff = logicalPositionToOffset(LogicalPosition(lastLog.line + 1, lastLog.column))
    return intArrayOf(startOff, endOff)
}

fun Editor.findCharsInVisibleArea(find: String): List<Int> {
    val borderOffset = getVisibleBorderOffset()
    val text = document.getText(TextRange(borderOffset[0], borderOffset[1]))

    return text.findAll(find)
}
