package com.werfad

import java.awt.Graphics
import javax.swing.JComponent
import javax.swing.JFrame

class MarksComponent : JComponent() {

    override fun paint(g: Graphics) {
        g.drawString("Hello world", 400, 300)
        super.paint(g)
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val frame = JFrame("Hello world")
            frame.setSize(800, 600)
            frame.add(MarksComponent())
            frame.isVisible = true
        }
    }

}
