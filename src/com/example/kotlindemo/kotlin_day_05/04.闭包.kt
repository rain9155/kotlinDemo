package com.example.kotlindemo.kotlin_day_05

/**
 * kotlin的闭包（函数式编程）
 * 1、定义：
 * 相当于lambda表达式，函数既可以作为方法返回值，又可以作为函数参数
 */
fun main(args: Array<String>) {

    //调用三次test（），a输出都是0
    test()// 0
    test()// 0
    test()// 0

    //使用闭包，调用三次test2（），每次输出值不一样
    val result = test2()
    result.invoke()// 0
    result()// 1
    result()// 2

}

/**
 * 传统函数不会保存变量状态
 */
fun test(){
    var a = 0
    println(a)
    a++
}

/**
 * 闭包，返回一个函数
 */
fun test2() : () -> Unit{
    var a = 0
    return {
        println(a)
        a++
    }
}