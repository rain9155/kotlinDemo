package com.example.kotlindemo.kotlin_day_04

/**
 * kotlin的枚举
 * 1、定义
 * enum class{}
 * 2、枚举加强
 * 枚举可以添加主构函数
 */
fun main(args: Array<String>) {

    //需求1：定义一个枚举表示星期一到星期日, 并输出星期一
    println(WEEK.MON)

    //需求2：遍历上面定义的枚举
    val result = WEEK.values()
    result.forEach {
        println(it)
    }

    //需求3：定义三原色枚举: 红、蓝、绿,并打印红色
    println(COLOR.RED)
    println(COLOR.RED.b)
    println(COLOR.RED.g)
    println(COLOR.RED.r)

}

/**
 * 用枚举表示周一到周日
 */
enum class WEEK{
    MON, TUES, MID, THURS, FIRD, SATUR, SUN
}

/**
 * 三原色枚举
 * 红：r 255 g 0 b 0
 * 蓝：r 0 g 0 b 255
 * 绿：r 0 g 255 b 0
 */
enum class COLOR(var r : Int, var g : Int, var b : Int){
    RED(255, 0, 0),
    BLUE(0, 0, 255),
    GREEN(0, 255, 0)
}