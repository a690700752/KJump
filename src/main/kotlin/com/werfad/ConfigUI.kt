package com.werfad

import javax.swing.JPanel
import javax.swing.JTextField

class ConfigUI {
    lateinit var rootPanel: JPanel
    private lateinit var hit1ColorTF: JTextField
    private lateinit var hit2ColorTF0: JTextField
    private lateinit var hit2ColorTF1: JTextField
    private lateinit var charactersTF: JTextField
    private lateinit var bgTF: JTextField

    var characters: String?
        get() = charactersTF.text
        set(s) {
            charactersTF.text = s
        }

    var hit1FontColor: Int
        get() = try {
            Integer.parseUnsignedInt(hit1ColorTF.text, 16)
        } catch (e: NumberFormatException) {
            0
        }
        set(c) {
            hit1ColorTF.text = Integer.toHexString(c)
        }

    var hit2FontColors: IntArray?
        get() = try {
            intArrayOf(
                Integer.parseUnsignedInt(hit2ColorTF0.text, 16),
                Integer.parseUnsignedInt(hit2ColorTF1.text, 16)
            )
        } catch (e: NumberFormatException) {
            null
        }
        set(arr) {
            arr?.let {
                hit2ColorTF0.text = Integer.toHexString(it[0])
                hit2ColorTF1.text = Integer.toHexString(it[1])
            }
        }

    var bgColor: Int
        get() = try {
            Integer.parseUnsignedInt(bgTF.text, 16)
        } catch (e: NumberFormatException) {
            0
        }
        set(c) {
            bgTF.text = Integer.toHexString(c)
        }
}