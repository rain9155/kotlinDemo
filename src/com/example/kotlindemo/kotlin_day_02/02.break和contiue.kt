package com.example.kotlindemo.kotlin_day_02

/**
 * kotlin中的break和continue
 * ps：kotlin中break和continue只能用在for循环中
 */
fun main(args: Array<String>) {
    /** break*/
    //需求：打印到c时跳出循环
    val str = "abcd"
    for (c in str){
        if( c == 'c') break
        println(c)
    }
    println()
    /** continue */
    //需求：打印到f时跳过循环
    val str2 = "a;jdfljffj"
    for(a in str2){
        if(a == 'f') continue
        println(a)
    }
}