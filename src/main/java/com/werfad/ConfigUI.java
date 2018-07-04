package com.werfad;

import javax.annotation.Nullable;
import javax.swing.*;

public class ConfigUI {
    private JPanel rootPanel;
    private JTextField charactersTF;
    private JTextField fontColorTF;
    private JTextField bgTF;
    private JCheckBox caseSensitiveCheckBox;

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
            return Integer.parseInt(fontColorTF.getText(), 16);
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
            return Integer.parseInt(bgTF.getText(), 16);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public void setBgColor(int c) {
        bgTF.setText(Integer.toHexString(c));
    }

    public Boolean getCaseSensitive() {
        return caseSensitiveCheckBox.isSelected();
    }

    public void setCaseSensitive(boolean caseSensitive) {
        caseSensitiveCheckBox.setSelected(caseSensitive);
    }
}

