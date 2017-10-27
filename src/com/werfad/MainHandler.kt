package com.werfad

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorActionManager
import com.intellij.openapi.editor.actionSystem.TypedActionHandler

class MainHandler : TypedActionHandler {
    private val typedAction = EditorActionManager.getInstance().typedAction
    private val oldHandler = typedAction.handler

    override fun execute(e: Editor, c: Char, dc: DataContext) {
        println(hashCode())
        stop()
    }

    fun start() {
        typedAction.setupHandler(this)
    }

    private fun stop() {
        typedAction.setupHandler(oldHandler)
    }
}