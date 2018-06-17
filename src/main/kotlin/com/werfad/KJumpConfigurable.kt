package com.werfad

import com.intellij.openapi.options.Configurable
import javax.swing.JComponent

class KJumpConfigurable : Configurable {
    private lateinit var config: UserConfig
    private lateinit var ui: ConfigUI

    override fun isModified(): Boolean {
        return ui.characters != config.getCharacters()
                || ui.fontColor != config.getFontColor()
                || ui.bgColor != config.getBgColor()
    }

    override fun getDisplayName(): String {
        return "KJump"
    }

    override fun apply() {
        config.setCharacters(ui.characters)
        config.setFontColor(
                if (ui.fontColor == null)
                    UserConfig.DEFAULT_FONT_COLOR else ui.fontColor!!)
        config.setBgColor(
                if (ui.bgColor == null)
                    UserConfig.DEFAULT_BG_COLOR else ui.bgColor!!)
    }

    override fun reset() {
        fillUI()
    }

    override fun createComponent(): JComponent? {
        config = UserConfig.getInstance()
        ui = ConfigUI()
        fillUI()
        return ui.rootPanel
    }

    private fun fillUI() {
        ui.characters = config.getCharacters()
        ui.setFontColor(config.getFontColor())
        ui.setBgColor(config.getBgColor())
    }
}
