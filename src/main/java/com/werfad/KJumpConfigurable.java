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
        return (!ui.getCharacters().equals(config.getCharacters())) ||
                ui.getHit1FontColor() == null ||
                ui.getHit1FontColor() != config.getHit1Color() ||
                ui.getHit2FontColors() == null ||
                ui.getHit2FontColors()[0] != config.getHit2Color0() ||
                ui.getHit2FontColors()[1] != config.getHit2Color1() ||
                ui.getBgColor() == null ||
                ui.getBgColor() != config.getBackgroundColor() ||
                (ui.isSmartcase() != config.isSmartcase());
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "KJump";
    }

    @Override
    public void apply() {
        config.setCharacters(ui.getCharacters());


        Integer hit1Color = ui.getHit1FontColor();
        config.setHit1Color(hit1Color == null ? UserConfig.DEFAULT_FONT_COLOR : hit1Color);

        Integer[] hit2Colors = ui.getHit2FontColors();
        if (hit2Colors == null) {
            config.setHit2Color0(UserConfig.DEFAULT_FONT_COLOR);
            config.setHit2Color1(UserConfig.DEFAULT_FONT_COLOR);
        } else {
            config.setHit2Color0(hit2Colors[0] == null ? UserConfig.DEFAULT_FONT_COLOR : hit2Colors[0]);
            config.setHit2Color1(hit2Colors[1] == null ? UserConfig.DEFAULT_FONT_COLOR : hit2Colors[1]);
        }

        Integer uiBgColor = ui.getBgColor();
        config.setBackgroundColor(uiBgColor == null ? UserConfig.DEFAULT_BG_COLOR : uiBgColor);

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
        ui.setHit1FontColor(config.getHit1Color());
        ui.setHit2FontColors(config.getHit2Color0(), config.getHit2Color1());
        ui.setBgColor(config.getBackgroundColor());
        ui.setSmartcase(config.isSmartcase());
    }
}
