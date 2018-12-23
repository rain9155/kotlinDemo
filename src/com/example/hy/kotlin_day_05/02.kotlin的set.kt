package com.example.hy.kotlin_day_05

/**
 * kotlin的set集合
 */
fun main(args: Array<String>) {

    /** 1、通过setOf<>(...) 创建, 只读集合，只能查不能增删改*/
    val set1 = setOf<String>("1", "2", "3")
    set1.forEach {
        println(it)
    }

    /** 2、通过mutableSetOf<>(...)创建， 能查、增、删不能改 */
    val  set2 = mutableSetOf<String>("1", "2", "3")
    set2.add("4")
    set2.remove("1")
    set2.forEach {
        println(it)
    }

    /** 3、直接创建, 能查、删不能增、改 */
    val set3 = HashSet<String>()
    set3.add("1")
    set3.add("2")
    set3.add("5")
    set3.forEach {
        println(it)
    }
}