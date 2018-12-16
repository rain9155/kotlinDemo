package com.example.hy.kotlin_day_04

/**
 * kotlin的惰性加载和延时加载
 * 1、惰性加载（by lazy）
 * 有确定值，使用的时候再加载
 * 特点：
 * 1) 字段必须是val不可变
 * 2）by lazy 可以放到成员变量中也可以单独存在
 * 3）by lazy{} 中的返回值就是最后一行
 * 4）by lazy 线程安全
 * 2、延时加载（lateinit）
 * 不确定值，使用时再赋值
 * 特点：
 * 1）lateinit 可以放到成员变量中也可以单独存在
 * 2) 字段必须通过var修饰
 * 3）使用时再赋值，不赋值会报错
 * 4)lateinit 修饰的变量/属性不能是 原始数据类型
 */

val name : String by lazy {
    println("name初始化")
    "rain"
}

lateinit var age : String

fun main(args: Array<String>) {

    //有确定值，使用时再加载，只初始化一次，再次使用时不用初始化
    println(name)//输出：name初始化 rain
    println(name)//输出：rain

    //无确定值，使用时再赋值
    val people = People()
    // people.age //第一次使用不赋值报错，lateinit property age has not been initialized
    people.age = "10"
    println(people.age)


}

class People{

    lateinit var age : String

    val name : String by lazy {
        println("name初始化")
        "李四"
    }

}