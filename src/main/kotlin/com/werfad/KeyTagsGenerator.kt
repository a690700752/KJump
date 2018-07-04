package com.werfad

import java.util.*

object KeyTagsGenerator {

    fun createTagsTree(targetCount: Int, keys: String = UserConfig.getDataBean().characters): List<String> {
        val res = ArrayList<String>()
        _createTagsTree(targetCount, keys, "", res)
        return res
    }

    private fun _createTagsTree(targetCount: Int, keys: String, prefix: String, res: MutableList<String>) {
        val keysLen = keys.length
        val keysCountMap = HashMap<Char, Int>()

        val chars = keys.toCharArray()
        for (c in chars) {
            keysCountMap.put(c, 0)
        }

        /* Calculate counts each branch stored. */
        chars.reverse()
        var targetsLeft = targetCount
        var level = 0

        while (targetsLeft > 0) {
            val childsLen = if (level == 0) 1 else keysLen - 1

            for (key in chars) {
                keysCountMap.put(key, keysCountMap[key]!! + childsLen)

                targetsLeft -= childsLen
                if (targetsLeft <= 0) {
                    keysCountMap.put(key, keysCountMap[key]!! + targetsLeft)
                    break
                }
            }
            level++
        }

        /* Create tree ( represent by String array ). */
        chars.reverse()
        for (key in chars) {
            val count = keysCountMap[key]
            if (count!! > 1) {
                _createTagsTree(count, keys, prefix + key, res)
            } else if (count == 1) {
                res.add(prefix + key)
            } else {
                continue
            }
        }
    }
}
