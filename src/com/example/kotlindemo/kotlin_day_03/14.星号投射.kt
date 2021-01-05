package com.example.kotlindemo.kotlin_day_03

/**
 * kotlin的星号投射：
 * 可以接受任何类型，相当于java中的 ？类型通配符
 */
fun main(args: Array<String>) {

    val list = ArrayList<Int>()
    setList(list)
    val list2 = ArrayList<String>()
    setList(list2)
}

/**
 * ArrayList里可以接受任何类型，怎么办？
 * 只需要在<>中加*号
 */
fun setList(list : ArrayList<*>){
    println(list.size)
}