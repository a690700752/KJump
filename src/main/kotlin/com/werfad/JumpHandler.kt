package com.werfad

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.actionSystem.IdeActions
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorActionHandler
import com.intellij.openapi.editor.actionSystem.EditorActionManager
import com.intellij.openapi.editor.actionSystem.TypedActionHandler
import com.werfad.utils.findAllInVisibleArea

object JumpHandler : TypedActionHandler {
    private lateinit var mOldTypedHandler: TypedActionHandler
    private var mOldEscActionHandler: EditorActionHandler? = null
    private val mMarksCanvas = MarksCanvas()

    private val STATE_WAIT_SEARCH_CHAR = 0
    private val STATE_WAIT_KEY = 1
    private var state = 0
    private var isStart = false

    private lateinit var mMarks: List<Mark>

    override fun execute(e: Editor, c: Char, dc: DataContext) {
        when (state) {
            STATE_WAIT_SEARCH_CHAR -> {
                mMarksCanvas.sync(e)
                e.contentComponent.add(mMarksCanvas)
                e.contentComponent.repaint()

                val allOffsets = e.findAllInVisibleArea(c).sortedBy { Math.abs(it - e.caretModel.offset) }
                if (allOffsets.isEmpty()) {
                    stop()
                }

                val tags = KeyTagsGenerator.createTagsTree(allOffsets.size, "abcdefghijklmnopqrstuvwxyz;")

                mMarks = allOffsets.mapIndexed { index, offset -> Mark(tags[index], offset) }
                mMarksCanvas.setData(mMarks)
                state = STATE_WAIT_KEY
            }
            STATE_WAIT_KEY -> {
                mMarks = filterMarksByKey(c)
                if (mMarks.isEmpty()) {
                    stop()
                } else if (mMarks.size == 1) {
                    e.caretModel.moveToOffset(mMarks[0].offset)
                    stop()
                } else {
                    mMarksCanvas.setData(mMarks)
                }
            }
        }
    }

    private fun filterMarksByKey(c: Char): List<Mark> {
        val newMarks = ArrayList<Mark>()
        for (mark in mMarks) {
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

    private val escActionHandler = object : EditorActionHandler() {
        override fun doExecute(editor: Editor, caret: Caret?, dataContext: DataContext?) {
            stop()
        }
    }

    fun start() {
        if (!isStart) {
            isStart = true
            val manager = EditorActionManager.getInstance()
            val typedAction = manager.typedAction

            mOldTypedHandler = typedAction.rawHandler
            typedAction.setupRawHandler(this)

            mOldEscActionHandler = manager.getActionHandler(IdeActions.ACTION_EDITOR_ESCAPE)
            manager.setActionHandler(IdeActions.ACTION_EDITOR_ESCAPE, escActionHandler)

            state = STATE_WAIT_SEARCH_CHAR
        }
    }

    private fun stop() {
        if (isStart) {
            isStart = false
            val manager = EditorActionManager.getInstance()
            manager.typedAction.setupRawHandler(mOldTypedHandler)
            mOldEscActionHandler?.let { manager.setActionHandler(IdeActions.ACTION_EDITOR_ESCAPE, it) }

            val parent = mMarksCanvas.parent
            parent?.let {
                // Maybe press ESC key to stop before press any key.
                parent.remove(mMarksCanvas)
                parent.repaint()
            }
        }
    }
}