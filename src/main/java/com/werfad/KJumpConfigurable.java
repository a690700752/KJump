package com.werfad;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public final class KJumpConfigurable implements Configurable {
    private UserConfig.DataBean config;
    private ConfigUI ui;

    @Override
    public boolean isModified() {
        return (!ui.getCharacters().equals(config.getCharacters()))
                || (ui.getFontColor() == null)
                || (ui.getFontColor() != config.getFontColor2())
                || (ui.getBgColor() == null)
                || (ui.getBgColor() != config.getBgColor2())
                || (ui.isSmartcase() != config.isSmartcase());
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "KJump";
    }

    @Override
    public void apply() {
        config.setCharacters(ui.getCharacters());

        Integer uiFontColor = ui.getFontColor();
        config.setFontColor2(uiFontColor == null ? UserConfig.DEFAULT_FONT_COLOR : uiFontColor);

        Integer uiBgColor = ui.getBgColor();
        config.setBgColor2(uiBgColor == null ? UserConfig.DEFAULT_BG_COLOR : uiBgColor);

        config.setSmartcase(ui.isSmartcase());
    }

    @Override
    public void reset() {
        this.fillUI();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        config = UserConfig.getDataBean();
        ui = new ConfigUI();
        fillUI();

        return ui.getRootPanel();
    }

    private void fillUI() {
        ui.setCharacters(config.getCharacters());
        ui.setFontColor(config.getFontColor2());
        ui.setBgColor(config.getBgColor2());
        ui.setSmartcase(config.isSmartcase());
    }
}
