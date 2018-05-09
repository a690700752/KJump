package com.werfad

import org.junit.Test

class Test {
    @Test
    fun test0() {
        val regex = Regex("(?m)^")
        val s = "wwww\naaaa\nbbbb\nccc\n"
        println(regex.findAll(s).map { it.range.start }.toList())
    }
}