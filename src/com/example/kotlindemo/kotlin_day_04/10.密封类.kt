package com.example.kotlindemo.kotlin_day_04

/**
 * kotlin的密封类
 * 1、特点：
 * 超强的枚举
 * 2、与枚举的区别：
 * 密封类封装的是类型（类型确定），而枚举封装的是数据（数据确定）
 * 3、定义：
 * class前加sealed关键字
 */
fun main(args: Array<String>) {

    //需求：权力的游戏，斯塔克选继承人
    val jonSnow = JonSnow()
    println(hasRight(jonSnow))//私生子没有权利，输出：false

    val aryaStark = NedStark.AryaStark()
    println(hasRight(aryaStark))//亲生的，有权利，输出：true

}

/**
 * 判断是否有权力继承王位
 */
fun hasRight(stark : NedStark) : Boolean{
    return when(stark){
        is NedStark.AryaStark -> true
        is NedStark.BrandonStark -> true
        is NedStark.RobStark -> true
        is NedStark.SansaStark -> true
        else -> false
    }
}

/**
 * 斯塔克
 */
sealed class NedStark{

    /** 斯塔克的亲生子女 */

    class RobStark : NedStark()

    class SansaStark : NedStark()

    class AryaStark : NedStark()

    class BrandonStark : NedStark()

}

/**
 * 斯塔克的私生子
 */
class JonSnow : NedStark()