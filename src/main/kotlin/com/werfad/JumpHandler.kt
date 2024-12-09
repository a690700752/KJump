package com.werfad

import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.command.UndoConfirmationPolicy
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.DocCommandGroupId
import com.intellij.openapi.editor.actionSystem.EditorActionHandler
import com.intellij.openapi.editor.actionSystem.EditorActionManager
import com.intellij.openapi.editor.actionSystem.TypedAction
import com.intellij.openapi.editor.actionSystem.TypedActionHandler
import com.intellij.openapi.fileEditor.ex.IdeDocumentHistory
import com.werfad.finder.*
import com.werfad.utils.getVisibleRangeOffset

object JumpHandler : TypedActionHandler {
    private var mOldTypedHandler: TypedActionHandler? = null
    private var mOldEscActionHandler: EditorActionHandler? = null
    private val mMarksCanvas: MarksCanvas by lazy { MarksCanvas() }
    private var isStart = false
    private lateinit var finder: Finder
    private var onJump: (() -> Unit)? = null // Runnable that is called after jump
    private var lastMarks: List<MarksCanvas.Mark> = emptyList()
    private var isCanvasAdded = false

    override fun execute(e: Editor, c: Char, dc: DataContext) {
        val marks = finder.input(e, c, lastMarks)
        if (marks != null) {
            lastMarks = marks
            jumpOrShowCanvas(e, lastMarks)
        }
    }

    private val escActionHandler: EditorActionHandler = object : EditorActionHandler() {
        override fun doExecute(editor: Editor, caret: Caret?, dataContext: DataContext) {
            stop()
        }
    }

    private fun jumpOrShowCanvas(e: Editor, marks: List<MarksCanvas.Mark>) {
        when {
            marks.isEmpty() -> {
                stop()
            }
            marks.size == 1 -> {
                // only one found, just jump to it
                val caret = e.caretModel.currentCaret
                if (caret.hasSelection()) {
                    val downOffset =
                        if (caret.selectionStart == caret.offset)
                            caret.selectionEnd
                        else
                            caret.selectionStart
                    caret.setSelection(downOffset, marks[0].offset)
                }
                // Shamelessly robbed from AceJump: https://github.com/acejump/AceJump/blob/99e0a5/src/main/kotlin/org/acejump/action/TagJumper.kt#L87
                with(e) {
                    project?.let { project ->
                        CommandProcessor.getInstance().executeCommand(
                            project, {
                                with(IdeDocumentHistory.getInstance(project)) {
                                    setCurrentCommandHasMoves()
                                    includeCurrentCommandAsNavigation()
                                    includeCurrentPlaceAsChangePlace()
                                }
                            }, "KJumpHistoryAppender", DocCommandGroupId.noneGroupId(document),
                            UndoConfirmationPolicy.DO_NOT_REQUEST_CONFIRMATION, document
                        )
                    }
                }
                caret.moveToOffset(marks[0].offset)

                stop()
                onJump?.invoke()
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
     *
     * @param mode mode enum, see [.MODE_CHAR1] [.MODE_CHAR2] etc
     */
    fun start(mode: JumpMode, anActionEvent: AnActionEvent) {
        if (isStart) return
        isStart = true
        val editor = anActionEvent.getData(CommonDataKeys.EDITOR) ?: return
        val manager = EditorActionManager.getInstance()
        val typedAction = TypedAction.getInstance()
        mOldTypedHandler = typedAction.rawHandler
        typedAction.setupRawHandler(this)
        mOldEscActionHandler = manager.getActionHandler(IdeActions.ACTION_EDITOR_ESCAPE)
        manager.setActionHandler(IdeActions.ACTION_EDITOR_ESCAPE, escActionHandler)
        onJump = null
        when (mode) {
            JumpMode.Char1 -> finder = Char1Finder()
            JumpMode.Char2 -> finder = Char2Finder()
            JumpMode.Word0 -> finder = Word0Finder()
            JumpMode.Word1 -> finder = Word1Finder()
            JumpMode.Line -> finder = LineFinder()
            JumpMode.Word1Declaration -> {
                finder = Word1Finder()
                onJump = {
                    ActionManager
                        .getInstance()
                        .getAction(IdeActions.ACTION_GOTO_DECLARATION)
                        .actionPerformed(anActionEvent)
                }
            }
        }
        val visibleBorderOffset = editor.getVisibleRangeOffset()
        val visibleString = editor.document.getText(visibleBorderOffset)
        val marks = finder.start(editor, visibleString, visibleBorderOffset)
        if (marks != null) {
            lastMarks = marks
            jumpOrShowCanvas(editor, lastMarks)
        }
    }

    private fun stop() {
        if (isStart) {
            isStart = false
            val manager = EditorActionManager.getInstance()
            TypedAction.getInstance().setupRawHandler(mOldTypedHandler!!)
            if (mOldEscActionHandler != null) {
                manager.setActionHandler(IdeActions.ACTION_EDITOR_ESCAPE, mOldEscActionHandler!!)
            }
            val parent = mMarksCanvas.parent
            if (parent != null) {
                parent.remove(mMarksCanvas)
                parent.repaint()
            }
            isCanvasAdded = false
        }
    }
}