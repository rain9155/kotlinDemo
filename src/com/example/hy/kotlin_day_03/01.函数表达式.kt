package com.example.hy.kotlin_day_03

/**
 * kotlin的函数表达式：
 * 当函数体只用一行时，可以省略return，{}，直接用 = 连接
 */
fun main(args: Array<String>) {
    //需求：用函数计算两个数的和
    val a = 10
    val b = 20
    println(add(a, b))
}

fun add2(a : Int, b : Int) : Int{
    return a + b
}

//函数表达式
fun add(a : Int, b : Int) = a + b