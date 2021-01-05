package com.example.kotlindemo.kotlin_day_03

/**
 * kotlin中的多态和智能类型转换
 * 1、多态：
 * 同种功能，不同类型表现形态
 * 2、智能类型转换：
 * 使用 is（相当于java中的instanceOf） 判断后，
 * 可以不需要再用 as（相当于java中的（）强转） 强转为具体类型，直接调用其中的方法
 */
fun main(args: Array<String>) {

    /** 多态实现 */
    val asus : Computer = ASUS()
    if(asus is ASUS){
        val newAsus = asus as ASUS
        newAsus.work()
    }
    /** 智能类型转换 */
    val apple : Computer = Apple()
    if(apple is Apple){
        apple.work()
    }
}

/**
 * 电脑
 */
abstract class Computer

/**
 * ASUS电脑
 */
class ASUS : Computer(){

     fun work() {
        println("华硕电脑工作")
    }

}

/**
 * 苹果电脑
 */
class Apple : Computer(){

     fun work() {
        println("苹果电脑工作")
    }
}