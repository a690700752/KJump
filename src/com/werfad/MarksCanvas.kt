package com.werfad

import java.awt.Color
import java.awt.Graphics
import javax.swing.JComponent
import javax.swing.JFrame

class MarksCanvas : JComponent() {

    override fun paint(g: Graphics) {
        g.color = Color.RED
        g.drawString("Hello world", 400, 300)
        super.paint(g)
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val frame = JFrame("Hello world")
            frame.setSize(800, 600)
            frame.add(MarksCanvas())
            frame.isVisible = true
        }
    }

}
