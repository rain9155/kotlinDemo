package com.example.kotlindemo.kotlin_day_01

/**
 * kotlin的原样输出字符串
 * 怎样写就怎样输出
 */
fun main(args: Array<String>) {
    /** 普通字符串 */
    val str = "广东省广州市番禺区"
    println(str)
    //如果要换行
    val str2 = "广东省\n广州市\n番禺区"
    println(str2)
    println()
    /** kotlin的原样输出字符串 */
    val str3 = """广东省广州市番禺区"""
    println(str3)
    val str4 = """
        广东省
        广州市
        番禺区
    """.trimIndent()
    println(str4)
    println()
    /** 普通字符串删除空格 */
    val str5 = "   广东省广州市番禺区   "
    val str6 = str5.trim()//删除空格
    println(str6)
    println()
    /** 用kotlin的原样字符串删除空格 */
    val str7 = """
        广东省
        广州市
        番禺区
        """
    println(str7)
    val str8 = """
        广东省
        广州市
        番禺区
    """.trimIndent()
    println(str8)//.trimIndent()删除空格
    val str9 = """
        |广东省
        |广州市
        |番禺区
    """.trimMargin("|")
    println(str9)//或者用.trimMargin("|")，参数可以为任意字符
}