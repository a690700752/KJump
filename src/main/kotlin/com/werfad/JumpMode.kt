package com.werfad

sealed class JumpMode(private val value: Int) {
    object Char1: JumpMode(0)
    object Char2: JumpMode(1)
    object Word0: JumpMode(2)
    object Word1: JumpMode(3)
    object Line: JumpMode(4)
    object Word1Declaration: JumpMode(5)

    override fun toString() = value.toString()
}