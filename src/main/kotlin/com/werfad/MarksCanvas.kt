package com.werfad

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.colors.EditorFontType
import com.werfad.UserConfig.DataBean
import com.werfad.utils.offsetToXYCompat
import java.awt.*
import javax.swing.JComponent

class MarksCanvas : JComponent() {
    private val config: DataBean = UserConfig.getDataBean()
    private var mMarks: List<Mark> = emptyList()
    private lateinit var mEditor: Editor
    private lateinit var mFont: Font
    private lateinit var mFontMetrics: FontMetrics

    fun sync(e: Editor) {
        val visibleArea = e.scrollingModel.visibleArea
        setBounds(visibleArea.x, visibleArea.y, visibleArea.width, visibleArea.height)
        mEditor = e
        mFont = e.colorsScheme.getFont(EditorFontType.BOLD)
        mFontMetrics = e.contentComponent.getFontMetrics(mFont)
    }

    fun setData(marks: List<Mark>) {
        mMarks = marks
        repaint()
    }

    override fun paint(g: Graphics) {
        val g2d = g as Graphics2D
        g2d.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        )

        val coordinates = mMarks
            .map { mEditor.offsetToXYCompat(it.offset) }
            .toList()

        g2d.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER)
        mMarks.zip(coordinates)
            .sortedBy { it.second.x }
            .forEach {
                g2d.color = Color(config.backgroundColor, true)
                val keyTag = it.first.keyTag
                val bounds = mFontMetrics.getStringBounds(keyTag.substring(it.first.advanceIndex), g).bounds

                g2d.fillRect(it.second.x - x, it.second.y - y, bounds.width, bounds.height)
                g2d.font = mFont

                val xInCanvas = it.second.x - x
                val yInCanvas = it.second.y - y + bounds.height - mFontMetrics.descent
                if (keyTag.length == 2) {
                    if (it.first.advanceIndex == 0) {
                        val midX = xInCanvas + bounds.width / 2

                        // first char
                        g2d.color = Color(config.hit2Color0, true)
                        g2d.drawString(keyTag[0].toString(), xInCanvas, yInCanvas)

                        // second char
                        g2d.color = Color(config.hit2Color1, true)
                        g2d.drawString(keyTag[1].toString(), midX, yInCanvas)
                    } else {
                        g2d.color = Color(config.hit2Color1, true)
                        g2d.drawString(keyTag[1].toString(), xInCanvas, yInCanvas)
                    }
                } else {
                    g2d.color = Color(config.hit1Color, true)
                    g2d.drawString(keyTag[0].toString(), xInCanvas, yInCanvas)
                }
            }
        super.paint(g)
    }

    class Mark(val keyTag: String, val offset: Int, val advanceIndex: Int = 0)
}