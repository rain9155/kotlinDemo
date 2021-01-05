package com.example.kotlindemo.kotlin_day_01

fun main(args : Array<String>){
    /** Kotlin智能类型转换 */
    //var a : Int = 10,不推荐这种写法
    //推荐这种写法，Kotlin自动推断成Int，Long，Double，Float，Byte除外， String，Char，Boolean
    var a = 10
    var b = 10L
    var c = 10.0
    var d = 10f
    var e : Byte = 10
    var f = "10"
    var g = '1'
    var h = false
    /** Kotlin类型转换 */
    //java只能小转大，不能大转小，类型转换较不安全
    //kotlin只能通过对象的to方法转换，类型安全，如
    var a1 = 10
    var b2 = 20L
    //a1 = b2 报错
    //b2 = a1 报错（java可以）
    a1 = b2.toInt()//这样才可以
    println(a1)
}