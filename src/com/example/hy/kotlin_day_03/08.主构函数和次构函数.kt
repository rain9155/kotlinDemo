package com.example.hy.kotlin_day_03

/**
 * 主构函数和次构函数
 * 1、kotlin的主构函数(在类名后接括号，括号里放要初始化的参数)：
 *  主构函数使用init, 参数初始化必须放在init方法块中
 *  主构函数使用var或val，可以省略init方法块，自动生成参数初始化步骤
 * 2、kotlin的次构函数(constructor（参数列表）):
 * constructor（参数列表）：this（参数列表）表示调用主构函数初始化部分参数
 * 次构函数不能使用使用var或val自动生成参数初始化步骤
 * 次构函数的参数初始化，必须放在自己的方法块里进行
 * 3、主构函数和次构函数的调用顺序：
 * 先主构后次构
 */
fun main(args: Array<String>) {
    //需求1：用name和age初始化一个Persion对象，主构函数使用init
    val person = Person1("rain", 18)
    println(person.age)
    println(person.name)
    //需求2：用name和age初始化一个Persion对象，主构函数使用var或val
    val person2 = Person2("rain2", 19)
    println(person2.name)
    println(person2.age)
    //需求3：用name，age和phone初始化一个Persion对象，使用次构函数
    val person3 = Person3("rain3", 20, 1847796089)
    println(person3.phone)
    println(person3.age)
    println(person3.name)
    //主构函数和次构函数的调用顺序
    val person4 = Person4("raian4", 21, 123456)
}

/**
 * Persion对象
 */
class Person1(name: String, age : Int){

    var name : String
    var age : Int
    //主构函数使用init，参数初始化放在init方法块中
    init {
        this.name = name
        this.age = age
    }
}

/**
 * Persion对象
 */
//主构函数使用var或val，可以省略init方法块，自动生成参数初始化步骤
class Person2(var name: String, val age : Int)

/**
 * Persion对象
 */
class Person3(var name: String, val age : Int){

    var phone : Int = -1
    //次构函数
    constructor(name : String, age : Int, phone : Int) : this(name, age){
        this.phone = phone
    }

}

/**
 * Persion对象
 */
class Person4(name: String, age : Int){

    var name : String
    var age : Int

    init {
        this.name = name
        this.age = age
        println("主构函数init执行")
    }

    var phone : Int = -1
    //次构函数
    constructor(name : String, age : Int, phone : Int) : this(name, age){
        this.phone = phone
        println("次构函数执行")
    }

}


