package com.example.kotlindemo.kotlin_day_05

/**
 * 函数中嵌套lambda表达式
 */
fun main(args: Array<String>) {

    /** 嵌套函数 */
//    fun sayHello() {
//        println("Hello")
//    }
//    sayHello()

    /** 嵌套无参lambda */
//    {
//        println("Hello")
//    }()//定义并调用
//或
//    {
//        println("Hello")
//    }.invoke()//定义并调用

    /** 嵌套有参lambda, 实现求和 */
//    val result = { a : Int, b : Int ->
//        a + b
//    }(10, 20)//定义并调用
//    println(result)
//或
    val result = { a : Int, b : Int ->
        a + b
    }//定义
    println(result.invoke(10, 20))//调用，输出：30
}