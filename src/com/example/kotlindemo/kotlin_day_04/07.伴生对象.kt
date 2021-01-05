package com.example.kotlindemo.kotlin_day_04

/**
 * kotlin中的伴生对象
 * 定义：
 * 类中加companion object{}，{}中放想要定义成静态的属性
 * 作用：
 * 解决object的缺点(object单例中所有的字段都是静态，过多静态消耗内存)
 * kotlin没有static关键字，伴生对象可以控制属性的静态
 */
fun main(args: Array<String>) {


    val util = Util()
    println(util.name)
    //不用创建对象，就可以直接调用里面的静态属性
    Util.age
    Util.sayHello()

}

class Util{

    //非静态字段
    var name = "rain"

    //伴生对象
    companion object {
        //静态字段
        val age = 18
        //静态方法
        fun sayHello(){
            println("Hello")
        }
    }

}