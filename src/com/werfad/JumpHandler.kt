package com.werfad

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorActionManager
import com.intellij.openapi.editor.actionSystem.TypedActionHandler
import com.werfad.utils.findAllInVisibleArea

object JumpHandler : TypedActionHandler {
    private val mTypedAction = EditorActionManager.getInstance().typedAction
    private val mOldHandler = mTypedAction.handler
    private val mMarksCanvas = MarksCanvas()

    override fun execute(e: Editor, c: Char, dc: DataContext) {
        mMarksCanvas.sync(e)
        e.contentComponent.add(mMarksCanvas)
        e.contentComponent.repaint()

        val allOffsets = e.findAllInVisibleArea(c)
        val marks = allOffsets.map { offset -> Mark("AB", offset) }
        mMarksCanvas.setData(marks)

        stop()
    }

    fun start() {
        mTypedAction.setupHandler(this)
    }

    private fun stop() {
        mTypedAction.setupHandler(mOldHandler)
    }
}