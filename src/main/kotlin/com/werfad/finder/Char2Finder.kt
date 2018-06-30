package com.werfad.finder

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.TextRange
import com.werfad.KeyTagsGenerator
import com.werfad.Mark
import com.werfad.utils.findAll

class Char2Finder : Finder {
    private val STATE_WAIT_SEARCH_CHAR1 = 0
    private val STATE_WAIT_SEARCH_CHAR2 = 1
    private val STATE_WAIT_KEY = 2
    private var state = STATE_WAIT_SEARCH_CHAR1

    private lateinit var s: String
    private lateinit var visibleRange: TextRange

    private var firstChar = ' '

    override fun start(e: Editor, s: String, visibleRange: TextRange): List<Mark>? {
        this.s = s
        this.visibleRange = visibleRange
        state = STATE_WAIT_SEARCH_CHAR1
        return null
    }

    override fun input(e: Editor, c: Char, lastMarks: List<Mark>): List<Mark>? {
        return when (state) {
            STATE_WAIT_SEARCH_CHAR1 -> {
                firstChar = c
                state = STATE_WAIT_SEARCH_CHAR2
                null
            }
            STATE_WAIT_SEARCH_CHAR2 -> {
                val offsets = s.findAll("" + firstChar + c)
                        .map { it + visibleRange.startOffset }
                        .sortedBy { Math.abs(it - e.caretModel.offset) }
                val tags = KeyTagsGenerator.createTagsTree(offsets.size)
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