package com.example.hy.kotlin_day_03

import java.io.BufferedReader
import java.io.File
import java.io.FileReader

fun main(args: Array<String>) {
    /** 运行时异常 */
    val a = 10
    val b = 0
    try {
        a / b
    }catch (e : ArithmeticException){
        println("捕获到运行时异常")
    }
    /** 编译时异常 */
    //kotlin不会检查编译时异常
    //kotlin认为大部分编译时异常的检查都是没有必要的
    val file = File("a.txt")
    val bufferReader = BufferedReader(FileReader(file))
}