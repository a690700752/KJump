package com.werfad.finder

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.TextRange
import com.werfad.MarksCanvas
import com.werfad.utils.getMarksFromAllEditors

private val pattern = Regex("(?i)\\b\\w")

class GlobalWord0Finder : Finder {
    override fun start(e: Editor, s: String, visibleRange: TextRange): List<MarksCanvas.Mark> {
        return e.project.getMarksFromAllEditors(pattern)
    }

    override fun input(e: Editor, c: Char, lastMarks: List<MarksCanvas.Mark>): List<MarksCanvas.Mark> {
        return advanceGlobalMarks(c, lastMarks)
    }
}