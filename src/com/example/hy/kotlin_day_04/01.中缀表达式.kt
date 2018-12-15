package com.example.hy.kotlin_day_04

/**
 * kotlin的中缀表达式（使代码更加易读）
 * 方法前加infix关键字
 * 中缀表达式使用条件：
 * 1、必须是成员函数（类中的函数）或扩展函数
 * 2、函数必须只有一个参数
 * 3、参数不能是可变参数（vararg）或默认参数
 */
fun main(args: Array<String>) {

    //例如定义一个函数，张三对李四sayHello
    val 张三 = Human()
    //这是一般的做法
    张三.sayHelloTo("李四")//输出：张三sayHelloTo李四
    //使用中缀表达式后
    张三 sayHelloto "李四"//输出：张三sayHelloTo李四

    //应用，如kotlin中的二元组Pair<A, T>
    //可以这样表示
    val pair = Pair<String, Int>("张三", 10)
    //也可以这样表示
    val pairs = "张三" to 10 //底层：public infix fun <A, B> A.to(that: B): Pair<A, B>
}

class Human{
    fun sayHelloTo(name : String){
        println("张三sayHelloTo$name")
    }

    infix fun sayHelloto(name: String){
        println("张三sayHelloTo$name")

    }
}