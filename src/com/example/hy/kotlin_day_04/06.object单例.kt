package com.example.hy.kotlin_day_04

/**
 * kotlin实现单例
 * object定义：
 * class关键字变为object关键字
 * object特点:
 * kotlin中加object实现的单例，所有的字段都是相当于java中的static字段，所有的方法都是相当于java中的final方法
 * object适用条件：
 * 类中字段不多的时候
 */
fun main(args: Array<String>) {

    //val utils = Utils() 报错，单例不能直接创建
    //单例直接调用里面的属性方法，不用创建对象
    println(Utils.name)
    println(Utils.age)
    Utils.sayHello()

}

/**
 * 加object变成单例
 */
object Utils{

    var name = "rain"
    val age = 18

    fun sayHello(){
        println("hello")
    }
}
