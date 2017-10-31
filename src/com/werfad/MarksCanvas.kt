package com.werfad

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.colors.EditorFontType
import java.awt.*
import javax.swing.JComponent

class MarksCanvas : JComponent() {
    private lateinit var mMarks: List<Mark>
    private lateinit var mEditor: Editor
    private lateinit var mFont: Font

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
        calcCoordinate(g)

        mMarks.forEach {
            g.color = Color.RED
            g.drawRect(it.MarkStart!!.x - x, it.MarkStart!!.y - y, it.strBounds!!.width, it.strBounds!!.height)

            g.font = mFont
            g.color = Color.GREEN
            g.drawString(it.keyBinding, it.MarkStart!!.x - x, it.MarkStart!!.y - y + it.strBounds!!.height)
        }

        super.paint(g)
    }

    private fun calcCoordinate(g: Graphics) {
        mFont = mEditor.colorsScheme.getFont(EditorFontType.BOLD)
        val fontMetrics = mEditor.contentComponent.getFontMetrics(font)

        mMarks.forEach {
            if (it.strBounds == null) {
                it.strBounds = fontMetrics.getStringBounds(it.keyBinding, g).bounds
            }

            if (it.MarkStart == null) {
                val bounds = fontMetrics.getStringBounds(it.keyBinding.substring(0, it.keyBinding.length - 1), g).bounds
                val offsetToXY = mEditor.offsetToXY(it.offset)
                it.MarkStart = Point(offsetToXY.x - bounds.width, offsetToXY.y)
            }
        }
    }

}

class Mark(val keyBinding: String, val offset: Int) {
    var strBounds: Rectangle? = null
    var MarkStart: Point? = null
}
