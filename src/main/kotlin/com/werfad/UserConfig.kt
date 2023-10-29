package com.werfad

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import com.werfad.UserConfig.DataBean

@State(name = "KJump", storages = [Storage("KJump.xml")])
class UserConfig : PersistentStateComponent<DataBean> {
    private val dataBean = DataBean()
    override fun getState(): DataBean {
        return dataBean
    }

    override fun loadState(dataBean1: DataBean) {
        XmlSerializerUtil.copyBean(dataBean1, dataBean)
    }

    class DataBean {
        var characters = DEFAULT_CHARACTERS
        var backgroundColor = DEFAULT_BG_COLOR
        var hit1Color = DEFAULT_FONT_COLOR
        var hit2Color0 = DEFAULT_FONT_COLOR
        var hit2Color1 = DEFAULT_FONT_COLOR
    }

    companion object {
        private const val DEFAULT_CHARACTERS = "hklyuiopnm,qwertzxcvbasdgjf;"
        const val DEFAULT_FONT_COLOR = -0x1
        const val DEFAULT_BG_COLOR = -0xff8534

        private val instance: UserConfig
            get() = ApplicationManager.getApplication().getService((UserConfig::class.java))

        fun getDataBean(): DataBean {
            return instance.dataBean
        }
    }
}