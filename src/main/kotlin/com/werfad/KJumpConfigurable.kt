package com.werfad

import com.intellij.openapi.options.Configurable
import javax.swing.JComponent

class KJumpConfigurable : Configurable {
    private lateinit var config: UserConfig.DataBean
    private lateinit var ui: ConfigUI

    override fun isModified(): Boolean {
        return ui.characters != config.characters
                || ui.fontColor != config.fontColor
                || ui.bgColor != config.bgColor
                || ui.caseSensitive != config.caseSensitive
    }

    override fun getDisplayName(): String {
        return "KJump"
    }

    override fun apply() {
        config.characters = ui.characters
        config.fontColor =
                if (ui.fontColor == null)
                    UserConfig.DEFAULT_FONT_COLOR else ui.fontColor!!
        config.bgColor =
                if (ui.bgColor == null)
                    UserConfig.DEFAULT_BG_COLOR else ui.bgColor!!
        config.caseSensitive = ui.caseSensitive
    }

    override fun reset() {
        fillUI()
    }

    override fun createComponent(): JComponent? {
        config = UserConfig.getDataBean()
        ui = ConfigUI()
        fillUI()
        return ui.rootPanel
    }

    private fun fillUI() {
        ui.characters = config.characters
        ui.setFontColor(config.fontColor)
        ui.setBgColor(config.bgColor)
        ui.caseSensitive = config.caseSensitive
    }
}
