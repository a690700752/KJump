package com.werfad

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.DumbAwareAction
import com.werfad.JumpHandler.MODE_CHAR1
import com.werfad.JumpHandler.MODE_LINE
import com.werfad.JumpHandler.MODE_WORD0
import com.werfad.JumpHandler.MODE_WORD1

class KJumpAction : DumbAwareAction() {
    override fun update(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR)
        e.presentation.isEnabled = editor != null
    }

    override fun actionPerformed(event: AnActionEvent) {
        JumpHandler.start(event.getData(CommonDataKeys.EDITOR)!!, MODE_CHAR1)
    }
}

class Word0Action : DumbAwareAction() {
    override fun update(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR)
        e.presentation.isEnabled = editor != null
    }

    override fun actionPerformed(event: AnActionEvent) {
        JumpHandler.start(event.getData(CommonDataKeys.EDITOR)!!, MODE_WORD0)
    }
}

class Word1Action : DumbAwareAction() {
    override fun update(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR)
        e.presentation.isEnabled = editor != null
    }

    override fun actionPerformed(event: AnActionEvent) {
        JumpHandler.start(event.getData(CommonDataKeys.EDITOR)!!, MODE_WORD1)
    }
}

class LineAction : DumbAwareAction() {
    override fun update(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR)
        e.presentation.isEnabled = editor != null
    }

    override fun actionPerformed(event: AnActionEvent) {
        JumpHandler.start(event.getData(CommonDataKeys.EDITOR)!!, MODE_LINE)
    }
}
