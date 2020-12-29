package com.joesemper.githubclient.mvp.model

class Model {
    private val counters: MutableList<Int> = mutableListOf(0,0,0)

    fun  getCurrent(index:Int):Int {
        return counters[index]
    }

    fun set(index: Int, value: Int) {
        counters[index] = value
    }

    fun next(index: Int): Int {
        return ++counters[index]
    }

}