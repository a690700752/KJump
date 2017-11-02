package com.werfad

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorActionManager
import com.intellij.openapi.editor.actionSystem.TypedActionHandler
import com.werfad.utils.findAllInVisibleArea

object JumpHandler : TypedActionHandler {
    private val mTypedAction = EditorActionManager.getInstance().typedAction
    private val mOldHandler = mTypedAction.rawHandler
    private val mMarksCanvas = MarksCanvas()

    private val STATE_WAIT_SEARCH_CHAR = 0
    private val STATE_WAIT_KEY = 1
    private var state: Int = STATE_WAIT_SEARCH_CHAR

    private lateinit var mMarks: List<Mark>

    override fun execute(e: Editor, c: Char, dc: DataContext) {
        when(state) {
            STATE_WAIT_SEARCH_CHAR -> {
                mMarksCanvas.sync(e)
                e.contentComponent.add(mMarksCanvas)
                e.contentComponent.repaint()

                val allOffsets = e.findAllInVisibleArea(c).sortedBy { Math.abs(it - e.caretModel.offset) }
                val tags = KeyTagsGenerator.createTagsTree(allOffsets.size, "abcdefghijklmnopqrstuvwxyz;")
                mMarks = allOffsets.mapIndexed { index, offset -> Mark(tags[index], offset) }
                mMarksCanvas.setData(mMarks)
                state = STATE_WAIT_KEY
            }
            STATE_WAIT_KEY -> {
                mMarks = filterMarksByKey(c)
                if (mMarks.size == 1 ) {
                    e.caretModel.moveToOffset(mMarks[0].offset)
                    stop(e)
                    state = STATE_WAIT_SEARCH_CHAR
                } else {
                    mMarksCanvas.setData(mMarks)
                }
            }
        }
    }

    private fun filterMarksByKey(c: Char): List<Mark> {
        val newMarks = ArrayList<Mark>()
        for (mark in mMarks) {
            if (mark.keyBinding[0] == c) {
                if (mark.keyBinding.length == 1) {
                    newMarks.add(mark)
                    return newMarks
                } else {
                    mark.keyBinding = mark.keyBinding.substring(1, mark.keyBinding.length)
                    newMarks.add(mark)
                }
            }
        }
        return newMarks
    }

    fun start() {
        mTypedAction.setupRawHandler(this)
    }

    private fun stop(e: Editor) {
        mTypedAction.setupRawHandler(mOldHandler)
        e.contentComponent.remove(mMarksCanvas)
        e.contentComponent.repaint()
    }
}