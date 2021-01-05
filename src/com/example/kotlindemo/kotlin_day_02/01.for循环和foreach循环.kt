package com.example.kotlindemo.kotlin_day_02

/**
 * kotlin中的for和foreach循环
 */
fun main(args: Array<String>) {

    /** for循环 */
    val str1 = "abcd"
    //不带下标的遍历
    for(s in str1){
        println(s)
    }
    println()
    //带下标的遍历
    for((index, s) in str1.withIndex()){
        println("index：${index}, s: ${s}")
    }
    println()
    /** foreach循环 */
    val str2 = "123456"
    //不带下标的遍历
    str2.forEach {
        println(it)
    }
    //带下标的遍历
    str2.forEachIndexed { index, c ->
        println("index: ${index}, c: ${c}")
    }

}