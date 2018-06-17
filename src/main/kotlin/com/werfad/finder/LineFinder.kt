package com.werfad.finder

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.TextRange
import com.werfad.KeyTagsGenerator
import com.werfad.Mark
import com.werfad.utils.findAllRegex

class LineFinder : Finder {
    override fun start(e: Editor, s: String, visibleRange: TextRange): List<Mark>? {
        val offsets = s.findAllRegex("(?m)^")
                .map { it + visibleRange.startOffset }
                .sortedBy { Math.abs(it - e.caretModel.offset) }
        val tags = KeyTagsGenerator.createTagsTree(offsets.size)
        return offsets.mapIndexed { index, offset ->
            Mark(tags[index], offset)
        }
    }

    override fun input(e: Editor, c: Char, lastMarks: List<Mark>): List<Mark> {
        return matchInputAndCreateMarks(c, lastMarks)
    }
}