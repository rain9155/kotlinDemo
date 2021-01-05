package com.example.kotlindemo.kotlin_day_01

/**
 * 1、输入输出函数
 * 2、自定义函数
 * 3、顶层函数和嵌套函数
 */
fun main(args: Array<String>) {
    /** 1、输入输出函数 */
    //输出函数，println()
    val int = 10
    println(int)
    //输入函数，readLine()
    val a : String? = readLine()
    val b : String? = readLine()
    println(a + b)
    /** 2、自定义函数 */
    sayHello()
    sayHello2("Hello!")
    println(sayHello3("Hello!"))
    println(sayHello4())
    /** 3、顶层函数和嵌套函数 */
    //顶层函数 - 和对象同等地位，可以独立于对象的存在，java中不可以,如本文件中所有定义的函数
    //嵌套函数 - 在函数中嵌套函数，java中不可以，如下：
    fun sayHello(){
        println("Hello")
    }
    sayHello()
}

//无参无返回值函数
fun sayHello(){
    println("Hello!")
}

//有参无返回值函数
fun sayHello2(name : String){
    println(name)
}

//有参有返回值函数
fun sayHello3(name : String) : String{
    return name
}

//无参有返回值函数
fun sayHello4() : String{
    return "Hello！"
}