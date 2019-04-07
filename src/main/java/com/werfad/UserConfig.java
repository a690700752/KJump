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
    private static final String DEFAULT_CHARACTERS = "abcdefghijklmnopqrstuvwxyz;";
    static final int DEFAULT_FONT_COLOR = 0xFFFFFFFF;
    static final int DEFAULT_BG_COLOR = 0xFF007ACC;
    private static final boolean DEFAULT_SMARTCASE = true;

    private final UserConfig.DataBean dataBean = new UserConfig.DataBean();

    @NotNull
    @Override
    public UserConfig.DataBean getState() {
        return this.dataBean;
    }

    @Override
    public void loadState(@NotNull UserConfig.DataBean dataBean1) {
        XmlSerializerUtil.copyBean(dataBean1, this.dataBean);
    }

    @NotNull
    private static UserConfig getInstance() {
        return ServiceManager.getService(UserConfig.class);
    }

    @NotNull
    public static UserConfig.DataBean getDataBean() {
        return getInstance().dataBean;
    }

    public static final class DataBean {
        @NotNull
        private String characters = DEFAULT_CHARACTERS;
        private int fontColor2 = DEFAULT_FONT_COLOR;
        private int bgColor2 = DEFAULT_BG_COLOR;
        private boolean smartcase = DEFAULT_SMARTCASE;

        @NotNull
        public String getCharacters() {
            return characters;
        }

        public void setCharacters(@NotNull String characters) {
            this.characters = characters;
        }

        public int getFontColor2() {
            return fontColor2;
        }

        public void setFontColor2(int fontColor2) {
            this.fontColor2 = fontColor2;
        }

        public int getBgColor2() {
            return bgColor2;
        }

        public void setBgColor2(int bgColor2) {
            this.bgColor2 = bgColor2;
        }

        public boolean isSmartcase() {
            return smartcase;
        }

        public void setSmartcase(boolean smartcase) {
            this.smartcase = smartcase;
        }
    }
}
