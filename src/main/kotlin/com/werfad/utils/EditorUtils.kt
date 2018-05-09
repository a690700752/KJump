package com.werfad.utils

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.LogicalPosition
import com.intellij.openapi.util.TextRange
import java.awt.Point


fun Editor.getVisibleRangeOffset(): TextRange {
    val visibleArea = scrollingModel.visibleArea

    val startLog = xyToLogicalPosition(Point(0, visibleArea.y))
    val lastLog = xyToLogicalPosition(Point(0, visibleArea.y + visibleArea.height))

    val startOff = logicalPositionToOffset(startLog)
    val endOff = logicalPositionToOffset(LogicalPosition(lastLog.line + 1, lastLog.column))
    return TextRange(startOff, endOff)
}

fun Editor.offsetToXYCompat(offset: Int): Point {
    return offsetToXYCompat(offset, false, false)
}

fun Editor.offsetToXYCompat(offset: Int, leanForward: Boolean, beforeSoftWrap: Boolean): Point {
    val visualPosition = offsetToVisualPosition(offset, leanForward, beforeSoftWrap)
    return visualPositionToXY(visualPosition)
}

