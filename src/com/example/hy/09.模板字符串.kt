package com.example.hy

/**
 * 比java拼接字符串更好用的模板字符串（${}）
 */
fun main(args: Array<String>) {
    //需求；字符串中有变量
    println(createPlace("北京", 3))
    println(createPlace2("广州", 199))

}

/**
 * java拼接字符串做法
 */
fun createPlace(place : String, num : Int) : String{
    return "今天我去了" + place + ", 很好玩，看到" + num + "个小鸟！"
}

/**
 * Kotlin的模板字符串
 */
fun createPlace2(place: String, num : Int) : String{
    return "今天我去了$place,看到了${num}个小鸟！"
}