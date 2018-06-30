package com.werfad.finder

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.TextRange
import com.werfad.Mark

interface Finder {
    /**
     * @return null - need more input to locate.
     *         not null - can be locate some data, empty represent without any matches.
     */
    fun start(e: Editor, s: String, visibleRange: TextRange): List<Mark>?

    /**
     * @return same with [start]
     */
    fun input(e: Editor, c: Char, lastMarks: List<Mark>): List<Mark>?
}

/**
 * @return Return the new marks whose start character is removed.
 */
fun matchInputAndCreateMarks(c: Char, marks: List<Mark>): List<Mark> {
    val newMarks = ArrayList<Mark>()
    for (mark in marks) {
        if (mark.keyTag[0] == c) {
            if (mark.keyTag.length == 1) {
                newMarks.add(mark)
                return newMarks
            } else {
                mark.keyTag = mark.keyTag.substring(1, mark.keyTag.length)
                newMarks.add(mark)
            }
        }
    }
    return newMarks
}

