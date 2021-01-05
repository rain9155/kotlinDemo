package com.example.kotlindemo.kotlin_day_04

/**
 * kotlin的扩展函数
 * 在不改变已有的类的情况下，为类添加新的函数
 * 扩展函数主要是替代java中的util类（工具类）
 * 定义：
 * 扩展可空类型的对象（对象可能为空），如String， fun String？.接自定义扩展函数
 * 扩展非空类型的对象（（对象一定不为空）, 如Father， fun Father.接自定义扩展函数
 * 特点：
 * 1、扩展函数可以访问当前对象里面的字段和方法
 * 2、在父类扩展的函数，子类也可以调用
 */
fun main(args: Array<String>) {

    //需求1：扩展String类，判断stirng类型是否为空
    val str : String? = null
    println(str.mIsEmpty())

    //需求2: 子类使用父类的扩展函数sayHello（）
    val girl = Girl()
    girl.sayHello()

}

/**
 * 扩展可空类型的对象
 */
fun String?.mIsEmpty() : Boolean{
    return this == null || this.length == 0
}

/**
 * 扩展非空类型的对象
 */

fun Father.sayHello(){
    println("你好")
}

/**
 * 女儿
 */
class Girl : Father()

/**
 * 父亲
 */
open class Father