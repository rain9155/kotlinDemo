package com.example.kotlindemo.kotlin_day_05

/**
 * kotlin的lambda表达式（匿名函数，函数只能使用一次）
 */
fun main(args: Array<String>) {

    val a = 10
    val b = 20

    /** 1、lambda第一种写法 */
    //求和
    //函数的第三个参数传了一个lambda表达式进去
    //lambda表达式中，返回值不需要写，自动推断出当前返回值类型
    val result = cal(a, b, { a, b ->
        a + b
    })
    println(result)

    /** 2、lambda第二种写法 */
    //求和
    //如果lambda表达式是函数的最后一个参数
    //传递lambda表达式时可以直接写在函数的括号外
    val result2 = cal(a, b) { a, b ->
        a + b
    }
    println(result2)

}