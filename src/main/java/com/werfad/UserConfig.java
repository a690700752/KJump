package com.werfad;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

@State(name = "KJump", storages = {@Storage("KJump.xml")})
public final class UserConfig implements PersistentStateComponent<UserConfig.DataBean> {
    @NotNull
    public static final String DEFAULT_CHARACTERS = "abcdefghijklmnopqrstuvwxyz;";
    public static final int DEFAULT_FONT_COLOR = 0xFFFFFF;
    public static final int DEFAULT_BG_COLOR = 0x007ACC;
    public static final boolean DEFAULT_SMARTCASE = true;

    private final UserConfig.DataBean dataBean = new UserConfig.DataBean();

    @NotNull
    public UserConfig.DataBean getState() {
        return this.dataBean;
    }

    @Override
    public void loadState(@NotNull UserConfig.DataBean dataBean1) {
        XmlSerializerUtil.copyBean(dataBean1, this.dataBean);
    }

    @NotNull
    public static UserConfig getInstance() {
        return ServiceManager.getService(UserConfig.class);
    }

    @NotNull
    public static UserConfig.DataBean getDataBean() {
        return getInstance().dataBean;
    }

    public static final class DataBean {
        @NotNull
        private String characters = DEFAULT_CHARACTERS;
        private int fontColor = DEFAULT_FONT_COLOR;
        private int bgColor = DEFAULT_BG_COLOR;
        private boolean smartcase = DEFAULT_SMARTCASE;

        @NotNull
        public String getCharacters() {
            return characters;
        }

        public void setCharacters(@NotNull String characters) {
            this.characters = characters;
        }

        public int getFontColor() {
            return fontColor;
        }

        public void setFontColor(int fontColor) {
            this.fontColor = fontColor;
        }

        public int getBgColor() {
            return bgColor;
        }

        public void setBgColor(int bgColor) {
            this.bgColor = bgColor;
        }

        public boolean isSmartcase() {
            return smartcase;
        }

        public void setSmartcase(boolean smartcase) {
            this.smartcase = smartcase;
        }
    }
}
