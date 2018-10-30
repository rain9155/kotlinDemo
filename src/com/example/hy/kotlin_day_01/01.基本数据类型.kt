package com.example.hy.kotlin_day_01

/**
 * 8种基本数据类型
 */
fun main(args: Array<String>) {
    var short : Short = 1//2byte
    var int : Int = 2//4byte
    var long  : Long = 3//8byte
    var float : Float = 4f//4byte
    var double : Double = 6.0//8byte

    var char : Char = 'n'//2byte
    var b : Boolean = false//1byte
    var byte : Byte = 10//1byte

    println(int.hashCode())

}