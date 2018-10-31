package com.example.hy.kotlin_day_02

/**
 * kotlin的反向区间的区间反转
 */
fun main(args: Array<String>) {
    /** 反向区间定义 */
    //需求：定义100到1的整形区间并输出
    val rang = 100 downTo 1
    rang.forEach {
        println(it)
    }
    println()
    /** 区间反转 */
    //需求1：反转1到100的整形区间并输出
    val rang2 = 1..100
    val rang3 = rang2.reversed()//区间反转
    rang3.forEach {
        println(it)
    }
    println()
    //需求2：反转1到100的整形区间并每隔2输出
    for ( r in rang3 step 2){
        println(r)
    }
}