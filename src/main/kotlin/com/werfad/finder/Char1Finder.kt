package com.werfad.finder

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.TextRange
import com.werfad.KeyTagsGenerator
import com.werfad.Mark
import com.werfad.utils.findAll

class Char1Finder : Finder {
    private val STATE_WAIT_SEARCH_CHAR = 0
    private val STATE_WAIT_KEY = 1
    private var state = STATE_WAIT_SEARCH_CHAR

    private lateinit var s: String
    private lateinit var visibleRange: TextRange

    override fun start(e: Editor, s: String, visibleRange: TextRange): List<Mark>? {
        this.s = s
        this.visibleRange = visibleRange
        state = STATE_WAIT_SEARCH_CHAR
        return null
    }

    override fun input(e: Editor, c: Char, lastMarks: List<Mark>): List<Mark> {
        return when (state) {
            STATE_WAIT_SEARCH_CHAR -> {
                val offsets = s.findAll(c)
                        .map { it + visibleRange.startOffset }
                        .sortedBy { Math.abs(it - e.caretModel.offset) }
                val tags = KeyTagsGenerator.createTagsTree(offsets.size,
                        "abcdefghijklmnopqrstuvwxyz;")
                state = STATE_WAIT_KEY
                offsets.mapIndexed { index, offset ->
                    Mark(tags[index], offset)
                }
            }
            STATE_WAIT_KEY -> matchInputAndCreateMarks(c, lastMarks)
            else -> throw RuntimeException("Impossible.")
        }
    }
}