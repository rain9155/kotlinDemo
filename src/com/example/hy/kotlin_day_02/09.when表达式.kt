package com.example.hy.kotlin_day_02

/**
 * kotlin的有返回值的when表达式，类似java中的case语句
 */
fun main(args: Array<String>) {

    /** when表达式 */
    //需求：通过一个人的年龄来判断它此刻的人生阶段
    //6岁：开始上小学
    //12岁：开始上初中
    //15岁：开始上高中
    //18岁：开始上大学
    //22岁以后：步入社会
    val age = 15
    println(todo(age))//输出：开始上高中

    /** when表达式加强 */
    //需求：完善上面的逻辑
    val age2 = 1
    println(todo2(age2))//输出：还没上小学
}

/**
 * 通过一个人的年龄来判断它此刻的人生阶段
 */
fun todo(age : Int) : String =
    when(age){

        6 -> {
            "开始上小学"
        }

        12 -> {
            "开始上初中"
        }

        15 -> {
            "开始上高中"
        }

        18 -> {
            "开始上大学"
        }

        22 -> {
            "步入社会"
        }

        else -> {
            "步入社会"
        }
    }


fun todo2(age : Int) : String =

        when(age){

            in 1..5 -> "还没上小学"

            6 -> "开始上小学"

            in 7..11 -> "正在上小学"

            12 -> "开始上初中"

            13, 14 -> "正在上初中"

            15 -> "开始上高中"

            16, 17 -> "正在上高中"

            18 -> "开始上大学"

            in 19..21 -> "正在上大学"

            else -> "步入社会"
        }

