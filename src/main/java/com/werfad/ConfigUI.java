package com.werfad;

import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class ConfigUI {
    private JPanel rootPanel;
    private JTextField hit1ColorTF;
    private JTextField hit2ColorTF0;
    private JTextField hit2ColorTF1;
    private JTextField charactersTF;
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
    Integer getHit1FontColor() {
        try {
            return Integer.parseUnsignedInt(hit1ColorTF.getText(), 16);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public void setHit1FontColor(int c) {
        hit1ColorTF.setText(Integer.toHexString(c));
    }

    @Nullable
    Integer[] getHit2FontColors() {
        try {
            return new Integer[]{
                    Integer.parseUnsignedInt(hit2ColorTF0.getText(), 16),
                    Integer.parseUnsignedInt(hit2ColorTF1.getText(), 16)};
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public void setHit2FontColors(int c0, int c1) {
        hit2ColorTF0.setText(Integer.toHexString(c0));
        hit2ColorTF1.setText(Integer.toHexString(c1));
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

