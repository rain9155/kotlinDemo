package com.example.kotlindemo.kotlin_day_04

/**
 * 用伴生对象和by lazy实现和java一样的对象
 */
fun main(args: Array<String>) {

    Single.instance.name

}

/**
 * 实现单例
 * 1、私有构造函数
 * 2、静态保存单例对象
 */
class Single private constructor(){//私有构造函数

    var name = "rain"
    var age = 18

    companion object {
        val instance by lazy { Single() }//静态保存单例对象
    }

}