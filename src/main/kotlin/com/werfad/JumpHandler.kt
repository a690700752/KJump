package com.werfad

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.actionSystem.IdeActions
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorActionHandler
import com.intellij.openapi.editor.actionSystem.EditorActionManager
import com.intellij.openapi.editor.actionSystem.TypedActionHandler
import com.werfad.finder.*
import com.werfad.utils.getVisibleRangeOffset

object JumpHandler : TypedActionHandler {
    val MODE_CHAR1 = 0
    val MODE_CHAR2 = 1
    val MODE_WORD0 = 2
    val MODE_WORD1 = 3
    val MODE_LINE = 4

    private lateinit var mOldTypedHandler: TypedActionHandler
    private var mOldEscActionHandler: EditorActionHandler? = null
    private val mMarksCanvas = MarksCanvas()

    private var isStart = false

    private var finder: Finder = Char1Finder()

    private var lastMarks = emptyList<Mark>()
    private var isCanvasAdded = false

    override fun execute(e: Editor, c: Char, dc: DataContext) {
        val marks = finder.input(e, c, lastMarks)
        if (marks != null) {
            lastMarks = marks
            jumpOrShowCanvas(e, lastMarks)
        }
    }

    private val escActionHandler = object : EditorActionHandler() {
        override fun doExecute(editor: Editor, caret: Caret?, dataContext: DataContext?) {
            stop()
        }
    }

    private fun jumpOrShowCanvas(e: Editor, marks: List<Mark>) {
        when {
            marks.isEmpty() -> {
                stop()
            }
            marks.size == 1 -> {
                // only one found, just jump to it
                e.caretModel.moveToOffset(marks[0].offset)
                stop()
            }
            else -> {
                if (!isCanvasAdded) {
                    mMarksCanvas.sync(e)
                    e.contentComponent.add(mMarksCanvas)
                    e.contentComponent.repaint()
                    isCanvasAdded = true
                }
                mMarksCanvas.setData(marks)
            }
        }
    }

    /**
     * start search mode
     * @param mode mode enum, see [MODE_CHAR1] and [MODE_WORD0]
     */
    fun start(e: Editor, mode: Int) {
        if (!isStart) {
            isStart = true
            val manager = EditorActionManager.getInstance()
            val typedAction = manager.typedAction

            mOldTypedHandler = typedAction.rawHandler
            typedAction.setupRawHandler(this)

            mOldEscActionHandler = manager.getActionHandler(IdeActions.ACTION_EDITOR_ESCAPE)
            manager.setActionHandler(IdeActions.ACTION_EDITOR_ESCAPE, escActionHandler)

            finder = when (mode) {
                MODE_CHAR1 -> Char1Finder()
                MODE_CHAR2 -> Char2Finder()
                MODE_WORD0 -> Word0Finder()
                MODE_WORD1 -> Word1Finder()
                MODE_LINE -> LineFinder()
                else -> throw Exception("Invalid start mode: $mode .")
            }

            val visibleBorderOffset = e.getVisibleRangeOffset()
            val visibleString = e.document.getText(visibleBorderOffset)
            val marks = finder.start(e, visibleString, visibleBorderOffset)
            if (marks != null) {
                lastMarks = marks
                jumpOrShowCanvas(e, lastMarks)
            }
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
            isCanvasAdded = false
        }
    }
}