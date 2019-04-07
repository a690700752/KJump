package com.werfad;

import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class ConfigUI {
    private JPanel rootPanel;
    private JTextField charactersTF;
    private JTextField fontColorTF;
    private JTextField bgTF;
    private JCheckBox smartcaseCheckBox;

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public String getCharacters() {
        return charactersTF.getText();
    }

    public void setCharacters(String s) {
        charactersTF.setText(s);
    }

    @Nullable
    public Integer getFontColor() {
        try {
            return Integer.parseUnsignedInt(fontColorTF.getText(), 16);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public void setFontColor(int c) {
        fontColorTF.setText(Integer.toHexString(c));
    }

    @Nullable
    public Integer getBgColor() {
        try {
            return Integer.parseUnsignedInt(bgTF.getText(), 16);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public void setBgColor(int c) {
        bgTF.setText(Integer.toHexString(c));
    }

    public Boolean isSmartcase() {
        return smartcaseCheckBox.isSelected();
    }

    public void setSmartcase(boolean caseSensitive) {
        smartcaseCheckBox.setSelected(caseSensitive);
    }
}

