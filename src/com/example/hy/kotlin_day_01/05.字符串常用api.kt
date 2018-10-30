package com.example.hy.kotlin_day_01

/**
 * Kotlin常用字符串api
 */
fun main(args: Array<String>) {
    /****************字符串比较********************/
    val str = "abc"
    val str2 = String(charArrayOf('a', 'b', 'c'))
    println(str.equals(str2))//true，比较的是值
    println(str == str2)//true, 比较的也是值，java中比较的是地址，为false
    println(str === str2)//flase, 比较的是地址
    /********************字符串切割split，多条件切割****************************/
    val str3 = "C:/Users/HY/AndroidStudio3.1/config/colors"
    val list = str3.split(":", "/")//可指定多个参数，java只能指定一个
    println(list)//输出[C, , Users, HY, AndroidStudio3.1, config, colors]
    /****************************字符串截取*******************************************/
    //需求1：获取前6个字符
    val str4 = str3.substring(0..5)//0..5表示0到5
    val str5 = str3.substring(0, 6)//java的做法
    //输出：C:/Use
    println(str4)
    println(str5)
    //需求2：把第一个c字符之前的字符截取
    val str6 = str3.substringBefore('c')
    println(str6)//输出：C:/Users/HY/AndroidStudio3.1/
    //需求3：把最后一个c之前的字符截取
    val  str7 = str3.substringBeforeLast('c')
    println(str7)//输出：C:/Users/HY/AndroidStudio3.1/config/
    //需求4：把第一个c字符之后的字符截取
    val str8 = str3.substringAfter('c')
    println(str8)//输出：onfig/colors
    //需求5：把最后一个c字符之后的字符截取
    val str9 = str3.substringAfterLast('c')
    println(str9)//输出:olors

}
