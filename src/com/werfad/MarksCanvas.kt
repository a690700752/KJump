package com.werfad

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.colors.EditorFontType
import com.intellij.openapi.editor.colors.removeSchemeMetaInfo
import java.awt.Color
import java.awt.Graphics
import java.awt.Point
import java.awt.Rectangle
import javax.swing.JComponent

class MarksCanvas : JComponent() {
    private lateinit var mMarks: List<Mark>
    private lateinit var mEditor: Editor

    fun sync(e: Editor) {
        val visibleArea = e.scrollingModel.visibleArea
        setBounds(visibleArea.x, visibleArea.y, visibleArea.width, visibleArea.height)
        mEditor = e
    }

    fun setData(marks: List<Mark>) {
        mMarks = marks
        repaint()
    }

    override fun paint(g: Graphics) {
        mMarks.forEach {
        }
        g.color = Color.RED
        g.drawString("Hello world", 400, 300)
        super.paint(g)
    }

    private fun checkAndCalcCoordinate(g: Graphics) {
        val font = mEditor.colorsScheme.getFont(EditorFontType.BOLD)
        val fontMetrics = mEditor.contentComponent.getFontMetrics(font)

        mMarks.forEach {
            if (it.strBounds == null) {
                it.strBounds = fontMetrics.getStringBounds(it.keyBinding, g).bounds
            }

            if (it.MarkStart == null) {
                val bounds = fontMetrics.getStringBounds(it.keyBinding.substring(0, it.keyBinding.length), g).bounds
                mEditor
            }
        }

        fontMetrics.getStringBounds()
    }

}

class Mark(val keyBinding: String, private val offset: Int) {
    var strBounds: Rectangle? = null
    var MarkStart: Point? = null
}
