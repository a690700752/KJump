package com.werfad.finder

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.TextRange
import com.werfad.DEFAULT_TAGS_KEYMAP
import com.werfad.KeyTagsGenerator
import com.werfad.Mark
import com.werfad.utils.findAllRegex
import kotlin.math.abs

class LineFinder : Finder {
    override fun start(e: Editor, s: String, visibleRange: TextRange): List<Mark>? {
        val offsets = s.findAllRegex("(?m)^")
                .map { it + visibleRange.startOffset }
                .sortedBy { abs(it - e.caretModel.offset) }
        val tags = KeyTagsGenerator.createTagsTree(offsets.size, DEFAULT_TAGS_KEYMAP)
        return offsets.mapIndexed { index, offset ->
            Mark(tags[index], offset)
        }
    }

    override fun input(e: Editor, c: Char, lastMarks: List<Mark>): List<Mark> {
        return matchInputAndCreateMarks(c, lastMarks)
    }
}