package com.werfad;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public final class KJumpConfigurable implements Configurable {
    private UserConfig.DataBean config;
    private ConfigUI ui;

    public boolean isModified() {
        return (!ui.getCharacters().equals(config.getCharacters()))
                || (ui.getFontColor() == null)
                || (ui.getFontColor() != config.getFontColor())
                || (ui.getBgColor() == null)
                || (ui.getBgColor() != config.getBgColor())
                || (ui.isSmartcase() != config.isSmartcase());
    }

    @NotNull
    public String getDisplayName() {
        return "KJump";
    }

    public void apply() {
        config.setCharacters(ui.getCharacters());

        Integer uiFontColor = ui.getFontColor();
        config.setFontColor(uiFontColor == null ? UserConfig.DEFAULT_FONT_COLOR : uiFontColor);

        Integer uiBgColor = ui.getBgColor();
        config.setBgColor(uiBgColor == null ? UserConfig.DEFAULT_BG_COLOR : uiBgColor);

        config.setSmartcase(ui.isSmartcase());
    }

    public void reset() {
        this.fillUI();
    }

    @Nullable
    public JComponent createComponent() {
        config = UserConfig.getDataBean();
        ui = new ConfigUI();
        fillUI();

        return ui.getRootPanel();
    }

    private void fillUI() {
        ui.setCharacters(config.getCharacters());
        ui.setFontColor(config.getFontColor());
        ui.setBgColor(config.getBgColor());
        ui.setSmartcase(config.isSmartcase());
    }
}
