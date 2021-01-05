package com.example.kotlindemo.kotlin_day_02

/**
 * kotlin中的while循环和do while循环
 */
fun main(args: Array<String>) {

    //需求：打印1~100数字
    val num = 100
    var i = 1
    /** while */
    while (i <= 100){
        println(i)
        i++
    }
    /** do while */
    i = 0
    do {
        i++
        println(i)
    }while (i < 100)

}