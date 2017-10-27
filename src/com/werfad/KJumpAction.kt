package com.werfad

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys

class KJumpAction : AnAction() {
    override fun update(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR)
        e.presentation.isEnabled = editor != null
    }

    override fun actionPerformed(anActionEvent: AnActionEvent) {
        MainHandler().start()
    }
}
