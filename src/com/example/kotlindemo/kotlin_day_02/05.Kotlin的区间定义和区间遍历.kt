package com.example.kotlindemo.kotlin_day_02

/**
 * kotlin的区间定义和区间遍历
 */
fun main(args: Array<String>) {
    /** 区间定义 */
    //需求1：定义1到100整数区间
    //方式1：
    val rang = 1..100
    //方式2：
    val rang2 = IntRange(1, 100)
    //方式3：
    val rang3 = 1.rangeTo(100)

    //需求2：定义1到100长整形区间
    val rangL = 1L..100L
    val rangL2 = LongRange(1, 100)
    val rangL3 = 1L.rangeTo(100L)

    //需求3：定义‘a’到‘z’字符区间
    val rangS = 'a'..'z'
    val rangS2 = CharRange('a', 'z')
    val rangS3 = 'a'.rangeTo('z')

    /** 区间遍历 */
    //需求1：用for循环
    for(s in rangS){
        println(s)
    }
    println()
    //需求2：用foreach循环
    rangS.forEach {
        println(it)
    }
    println()
    rangS.forEachIndexed{ index, c ->
        println("index: $index, s: $c")
    }
}