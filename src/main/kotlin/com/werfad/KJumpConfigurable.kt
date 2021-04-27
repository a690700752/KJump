package com.werfad

import com.intellij.openapi.options.Configurable
import com.werfad.UserConfig.DataBean
import javax.swing.JComponent

class KJumpConfigurable : Configurable {
    private lateinit var config: DataBean
    private lateinit var ui: ConfigUI

    override fun isModified(): Boolean {
        return ui.characters != config.characters || ui.hit1FontColor != config.hit1Color
                || ui.hit2FontColors == null || ui.hit2FontColors!![0] != config.hit2Color0 || ui.hit2FontColors!![1] != config.hit2Color1 || ui.bgColor != config.backgroundColor
    }

    override fun getDisplayName(): String {
        return "KJump"
    }

    override fun apply() {
        config.characters = ui.characters.orEmpty()
        val hit1Color = ui.hit1FontColor
        config.hit1Color = hit1Color
        val hit2Colors = ui.hit2FontColors
        if (hit2Colors == null) {
            config.hit2Color0 = UserConfig.DEFAULT_FONT_COLOR
            config.hit2Color1 = UserConfig.DEFAULT_FONT_COLOR
        } else {
            config.hit2Color0 = hit2Colors[0]
            config.hit2Color1 = hit2Colors[1]
        }
        val uiBgColor = ui.bgColor
        config.backgroundColor = uiBgColor
    }

    override fun reset() {
        fillUI()
    }

    override fun createComponent(): JComponent {
        config = UserConfig.getDataBean()
        ui = ConfigUI()
        fillUI()
        return ui.rootPanel
    }

    private fun fillUI() {
        ui.characters = config.characters
        ui.hit1FontColor = config.hit1Color
        ui.hit2FontColors = intArrayOf(config.hit2Color0, config.hit2Color1)
        ui.bgColor = config.backgroundColor
    }
}