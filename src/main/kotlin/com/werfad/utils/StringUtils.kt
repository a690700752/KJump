package com.werfad.utils

fun String.findAll(c: Char, ignoreCase: Boolean = false): List<Int> {
    val res = ArrayList<Int>()
    var index = indexOf(c, ignoreCase = ignoreCase)
    while (index >= 0) {
        res.add(index)
        index = indexOf(c, index + 1, ignoreCase)
    }
    return res
}

fun String.findAll(find: String, ignoreCase: Boolean = false): List<Int> {
    val res = ArrayList<Int>()
    var index = indexOf(find, ignoreCase = ignoreCase)
    while (index >= 0) {
        res.add(index)
        index = indexOf(find, index + 1, ignoreCase)
    }
    return res
}
