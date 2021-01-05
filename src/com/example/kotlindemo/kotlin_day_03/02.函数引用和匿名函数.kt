package com.example.kotlindemo.kotlin_day_03

/**
 * kotlin的函数引用（类似于C语言中的函数指针）和匿名函数（没有名字的函数）
 */
fun main(args: Array<String>) {

    val a = 10
    val b = 20

    /** 函数引用 */
    val reference = ::decrease// ::加一个函数等于定义了一个函数引用
    //调用方式1：
    println(reference(a, b))
    //调用方式2：
    println(reference.invoke(a, b))

    println(reference?.invoke(a, b))//函数不为空时才调用

    /** 匿名函数 */
    val anonymity : (Int, Int) -> Int = {a, b -> a + b}//定义了一个两个形参类型为Int，返回值为Int的匿名函数
    //调用：
    println(anonymity(a, b))
}


/**
 * 计算两个数的差
 */
fun decrease(a : Int, b : Int) = a - b