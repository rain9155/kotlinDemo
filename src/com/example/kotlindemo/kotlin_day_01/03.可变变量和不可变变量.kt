package com.example.kotlindemo.kotlin_day_01

/**
 * kotlin的var和val
 * kotlin中能使用val就尽量使用val
 */
fun main(args: Array<String>) {
    //var，可变变量
    var a = 10
    a = 12//不报错
    //val，不可变变量（编译时常量，与java的final一样），但反射（运行时）可以改变其值
    val b = 10
    //b = 12//报错
}