package com.werfad

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.editor.actionSystem.TypedActionHandler
import com.intellij.openapi.project.DumbAwareAction

abstract class BaseAction(private val handler: TypedActionHandler) : DumbAwareAction() {
    override fun update(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR)
        e.presentation.isEnabled = editor != null
    }

    /**
     * JumpHandler is used for actions on the currently focused editor.
     * GlobalJumpHandler is used for actions across all editors.
     */
    override fun actionPerformed(e: AnActionEvent) {
        when (handler) {
            is JumpHandler -> handler.start(getMode(), e)
            is GlobalJumpHandler -> handler.start(getMode(), e)
            else -> error("Unsupported handler: ${handler::class}")
        }
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.EDT
    }

    abstract fun getMode(): JumpMode
}

class KJumpAction : BaseAction(JumpHandler) {
    override fun getMode() = JumpMode.Char1
}

class Char2Action : BaseAction(JumpHandler) {
    override fun getMode() = JumpMode.Char2
}

class Word0Action : BaseAction(JumpHandler) {
    override fun getMode() = JumpMode.Word0
}

class Word1Action : BaseAction(JumpHandler) {
    override fun getMode() = JumpMode.Word1
}

class LineAction : BaseAction(JumpHandler) {
    override fun getMode() = JumpMode.Line
}

class GotoDeclarationWord1Action : BaseAction(JumpHandler) {
    override fun getMode() = JumpMode.Word1Declaration
}

class GlobalWord0Action : BaseAction(GlobalJumpHandler) {
    override fun getMode() = JumpMode.Word0
}