package com.werfad

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(name = "KJump",
        storages = arrayOf(Storage("KJump.xml")))
class UserConfig : PersistentStateComponent<UserConfig.DataBean> {
    private val dataBean = DataBean()

    fun setCharacters(s: String) {
        dataBean.characters = s
    }

    fun getCharacters(): String {
        return dataBean.characters
    }

    fun setFontColor(c: Int) {
        dataBean.fontColor = c
    }

    fun getFontColor(): Int {
        return dataBean.fontColor
    }

    fun setBgColor(c: Int) {
        dataBean.bgColor = c
    }

    fun getBgColor(): Int {
        return dataBean.bgColor
    }

    override fun getState(): DataBean {
        return dataBean
    }

    override fun loadState(dataBean1: DataBean) {
        XmlSerializerUtil.copyBean(dataBean1, dataBean)
    }

    data class DataBean(var characters: String = DEFAULT_CHARACTERS,
                        var fontColor: Int = DEFAULT_FONT_COLOR,
                        var bgColor: Int = DEFAULT_BG_COLOR)

    companion object {
        const val DEFAULT_CHARACTERS = "abcdefghijklmnopqrstuvwxyz;"
        const val DEFAULT_FONT_COLOR = 0xFFFFFF
        const val DEFAULT_BG_COLOR = 0x007ACC

        fun getInstance(): UserConfig {
            return ServiceManager.getService(UserConfig::class.java)
        }
    }
}

