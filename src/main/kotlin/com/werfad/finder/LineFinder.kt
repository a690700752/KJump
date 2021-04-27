package com.werfad.finder

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.TextRange
import com.werfad.KeyTagsGenerator
import com.werfad.MarksCanvas
import com.werfad.UserConfig
import kotlin.math.abs

private val pattern = Regex("(?m)^")

class LineFinder : Finder {

    override fun start(e: Editor, s: String, visibleRange: TextRange): List<MarksCanvas.Mark> {
        val cOffset = e.caretModel.offset

        val offsets = pattern.findAll(s)
            .map { it.range.first + visibleRange.startOffset }
            .sortedBy { abs(it - cOffset) }
            .toList()

        val tags = KeyTagsGenerator.createTagsTree(offsets.size, UserConfig.getDataBean().characters)
        return offsets.zip(tags)
            .map { MarksCanvas.Mark(it.second, it.first) }
            .toList()
    }

    override fun input(e: Editor, c: Char, lastMarks: List<MarksCanvas.Mark>): List<MarksCanvas.Mark> {
        return advanceMarks(c, lastMarks)
    }
}