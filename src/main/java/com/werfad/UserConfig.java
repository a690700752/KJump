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
    private static final String DEFAULT_CHARACTERS = "hklyuiopnm,qwertzxcvbasdgjf;";
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
        private int backgroundColor = DEFAULT_BG_COLOR;
        private int hit1Color = DEFAULT_FONT_COLOR;
        private int hit2Color0 = DEFAULT_FONT_COLOR;
        private int hit2Color1 = DEFAULT_FONT_COLOR;
        private boolean smartcase = DEFAULT_SMARTCASE;

        @NotNull
        public String getCharacters() {
            return characters;
        }

        public void setCharacters(@NotNull String characters) {
            this.characters = characters;
        }

        public int getBackgroundColor() {
            return backgroundColor;
        }

        public void setBackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        public int getHit1Color() {
            return hit1Color;
        }

        public void setHit1Color(int hit1Color) {
            this.hit1Color = hit1Color;
        }

        public int getHit2Color0() {
            return hit2Color0;
        }

        public int getHit2Color1() {
            return hit2Color1;
        }

        public void setHit2Color0(int hit2Color0) {
            this.hit2Color0 = hit2Color0;
        }

        public void setHit2Color1(int hit2Color1) {
            this.hit2Color1 = hit2Color1;
        }

        public boolean isSmartcase() {
            return smartcase;
        }

        public void setSmartcase(boolean smartcase) {
            this.smartcase = smartcase;
        }
    }
}
