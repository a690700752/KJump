package com.werfad

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.colors.EditorFontType
import com.werfad.utils.offsetToXYCompat
import java.awt.*
import javax.swing.JComponent

class MarksCanvas : JComponent() {
    private lateinit var mMarks: List<Mark>
    private lateinit var mSortedMarks: List<Mark>
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
        mSortedMarks = emptyList()
        repaint()
    }

    override fun paint(g: Graphics) {
        val g2d = g as Graphics2D
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON)

        calcCoordinate(g)
        if (mSortedMarks.isEmpty()) {
            mSortedMarks = mMarks.sortedBy { it.markStart!!.x }
        }

        mSortedMarks.forEach {
            g2d.color = Color(0x007ACC)
            g2d.fillRect(it.markStart!!.x - x, it.markStart!!.y - y,
                    it.strBounds!!.width, it.strBounds!!.height)

            g2d.font = mFont
            g2d.color = Color.WHITE
            g2d.drawString(it.keyTag, it.markStart!!.x - x,
                    it.markStart!!.y - y + it.strBounds!!.height)
        }

        super.paint(g)
    }

    private fun calcCoordinate(g: Graphics) {
        mMarks.forEach {
            if (it.strBounds == null) {
                it.strBounds = mFontMetrics.getStringBounds(it.keyTag, g).bounds
            }

            if (it.markStart == null) {
                it.markStart = mEditor.offsetToXYCompat(it.offset)
            }
        }
    }

}

class Mark(keyTag: String, val offset: Int) {
    var keyTag: String = keyTag
        set(value) {
            strBounds = null
            field = value
        }
    var strBounds: Rectangle? = null
    var markStart: Point? = null
}
