package com.werfad.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class KeyTagsGeneratorTest {

    @Test
    fun testCreateKeyTagsTree() {
        val res = KeyTagsGenerator.createTagsTree(756, "abcdefghijklmnopqrstuvwxyz")

        assertEquals(res.size.toLong(), 756)
        println(res.size)
        println(res)
    }
}