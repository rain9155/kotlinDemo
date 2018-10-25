package com.example.hy

/**
 * 元组数据：
 * 给多个变量同时赋值
 */
fun main(args: Array<String>) {
    /** 二元元组，Pair */
    //案例1：存一个人的性命和年龄
    val pair : Pair<String, Int> = Pair("rain", 20)
    println("名字：" + pair.first)
    println("年龄： " + pair.second)
    //也可以这样写(常用写法)
    val pair2 : Pair<String, Int> = "rain" to 20
    println("名字：" + pair2.first)
    println("年龄： " + pair2.second)
    /** 三元元组，Triple */
    //案例2：存一个人的性命和年龄和电话
    val triple : Triple<String, Int, Long> = Triple("rain", 20, 13660139155)
    println("名字: " + triple.first)
    println("年龄：" + triple.second)
    println("电话： " + triple.third)
}