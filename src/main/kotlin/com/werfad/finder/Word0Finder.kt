package com.werfad.finder

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.TextRange
import com.werfad.Mark
import com.werfad.utils.findAllRegex

class Word0Finder : Finder {
    override fun start(e: Editor, s: String, visibleRange: TextRange): List<Mark>? {
        val res = ArrayList<Mark>()
        s.findAllRegex("\\b\\w")
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return res
    }

    override fun input(e: Editor, c: Char, lastMarks: List<Mark>): List<Mark> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}