package com.werfad.utils

import com.intellij.openapi.fileEditor.TextEditor
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx
import com.intellij.openapi.project.Project
import com.werfad.KeyTagsGenerator
import com.werfad.MarksCanvas
import com.werfad.UserConfig
import kotlin.math.abs

/**
 * Collects marks from all editors, prioritizing marks closest to the caret in the currently active editor.
 *
 * @receiver the current project instance, or null.
 * @param pattern the regex used to find marks within the visible text of the editors.
 * @return a list of marks with key tags, sorted by their distance from the active editors caret
 */
fun Project?.getMarksFromAllEditors(pattern: Regex): List<MarksCanvas.Mark> {
    if (this == null) return emptyList()

    // 1. Get all visible editors
    val fileEditorManagerEx = FileEditorManagerEx.getInstanceEx(this)
    val allEditors = fileEditorManagerEx.allEditors
        .filterIsInstance<TextEditor>()
        .map { it.editor }
        .distinct()

    val userConfigCharacters = UserConfig.getDataBean().characters
    require(userConfigCharacters.isNotEmpty()) { "Character set for key tags is empty." }

    val activeEditor = fileEditorManagerEx.selectedTextEditor
    val activeCaretOffset = activeEditor?.caretModel?.offset ?: 0

    // 2. Sort marks by distance from the caret
    val sortedOffsets = allEditors.flatMap { editor ->
        val visibleRange = editor.getVisibleRangeOffset()
        val visibleText = editor.document.getText(visibleRange)

        pattern.findAll(visibleText).map { matchResult ->
            val absoluteOffset = matchResult.range.first + visibleRange.startOffset
            editor to absoluteOffset
        }
    }.sortedWith(compareBy { (editor, offset) ->
        if (editor == activeEditor) abs(offset - activeCaretOffset) else Int.MAX_VALUE
    })

    val tags = KeyTagsGenerator.createTagsTree(sortedOffsets.size, userConfigCharacters)

    return sortedOffsets.zip(tags) { (editor, offset), tag ->
        MarksCanvas.Mark(tag, offset, editor = editor)
    }
}
