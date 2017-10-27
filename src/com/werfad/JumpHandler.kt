package com.werfad

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorActionManager
import com.intellij.openapi.editor.actionSystem.TypedActionHandler

object JumpHandler : TypedActionHandler {
    private val mTypedAction = EditorActionManager.getInstance().typedAction
    private val mOldHandler = mTypedAction.handler
    private val mMarksCanvas = MarksCanvas()

    override fun execute(e: Editor, c: Char, dc: DataContext) {
        mMarksCanvas.setBounds(0, 0, e.contentComponent.width, e.contentComponent.height)
        e.contentComponent.add(mMarksCanvas)
        e.contentComponent.repaint()
        stop()
    }

    fun start() {
        mTypedAction.setupHandler(this)
    }

    private fun stop() {
        mTypedAction.setupHandler(mOldHandler)
    }
}