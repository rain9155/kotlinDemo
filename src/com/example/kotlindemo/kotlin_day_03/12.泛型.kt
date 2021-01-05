package com.example.kotlindemo.kotlin_day_03

/**
 * kotlin的泛型：
 * 1、泛型类：
 * 定义对象的时候使用泛型，类名后<T>定义泛型，类中直接使用T
 * 定义子类的时候不知道使用什么泛型，子类继续使用泛型
 * 定义子类的时候知道使用什么泛型，子类执行泛型
 * 2、泛型函数：
 * 定义函数时，fun后<T> 定义泛型，函数中直接使用
 */
fun main(args: Array<String>) {

    val banana = Banana("香蕉")
    val banBox = FruitBox(banana)
    println(banBox.something)

    praseType(1)
    praseType("记号")
    praseType(1.0f)

}

/**
 * 箱子
 */
open class Box<T>(var something : T)

/**
 * 不知道放什么东西的箱子
 */
class SonBox<T>(something : T): Box<T>(something)

/**
 * 放水果的箱子
 */
class FruitBox(something: Fruit) : Box<Fruit>(something)

/**
 * 水果抽象类
 */
abstract class Fruit{
    abstract var name : String
}

/**
 * 香蕉
 */
class Banana(override var name: String) : Fruit()

/**
 * 判断类型
 */
fun <T> praseType(type : T){
    when(type){
        is Int -> println("int类型")
        is String -> println("String类型")
        else -> println("未知类型")
    }
}