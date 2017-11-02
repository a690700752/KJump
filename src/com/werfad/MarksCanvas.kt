package com.werfad

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.colors.EditorFontType
import java.awt.*
import javax.swing.JComponent

class MarksCanvas : JComponent() {
    private lateinit var mMarks: List<Mark>
    private lateinit var mEditor: Editor
    private lateinit var mFont: Font
    private lateinit var mFontMetrics: FontMetrics

    fun sync(e: Editor) {
        val visibleArea = e.scrollingModel.visibleArea
        setBounds(visibleArea.x, visibleArea.y, visibleArea.width, visibleArea.height)
        mEditor = e
        mFont = mEditor.colorsScheme.getFont(EditorFontType.BOLD)
        mFontMetrics = mEditor.contentComponent.getFontMetrics(mFont)
    }

    fun setData(marks: List<Mark>) {
        mMarks = marks
        repaint()
    }

    override fun paint(g: Graphics) {

        mMarks.forEach {
            calcCoordinate(g, it)
            g.color = Color.RED
            g.drawRect(it.markStart!!.x - x, it.markStart!!.y - y, it.strBounds!!.width, it.strBounds!!.height)

            g.font = mFont
            g.color = Color.GREEN
            g.drawString(it.keyBinding, it.markStart!!.x - x, it.markStart!!.y - y + it.strBounds!!.height)
        }

        super.paint(g)
    }

    private fun calcCoordinate(g: Graphics, m: Mark) {
        if (m.strBounds == null) {
            m.strBounds = mFontMetrics.getStringBounds(m.keyBinding, g).bounds
        }

        if (m.markStart == null) {
            m.markStart = mEditor.offsetToXY(m.offset)
        }
    }

}

class Mark(keyBinding: String, val offset: Int) {
    var keyBinding: String = keyBinding
        set(value) {
            markStart = null
            field = value
        }
    var strBounds: Rectangle? = null
    var markStart: Point? = null

}
