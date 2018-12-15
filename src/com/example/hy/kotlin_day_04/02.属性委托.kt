package com.example.hy.kotlin_day_04

import kotlin.reflect.KProperty

/**
 * kotlin的属性委托
 * 把类中的属性委托其他类来实现get和set方法
 * 实现：属性后加关键字by接委托类，委托类实现getValue和setValue方法
 */
fun main(args: Array<String>) {

    //需求：儿子委托妈妈存压岁钱
    val son = Son()
    //存钱
    son.压岁钱 = 100
    //取钱
    println(son.压岁钱)//输出：50

}

/**
 * 儿子
 */
class Son{

    var 压岁钱 : Int by Mother()
}

/**
 * 妈妈
 */
class Mother{

    var 儿子的压岁钱 = 0
    var 妈妈自己的钱 = 0

    //儿子要压岁钱
    operator fun getValue(son: Son, property: KProperty<*>): Int {
        return 儿子的压岁钱
    }

    //儿子存压岁钱
    // i 表示要设置的钱
    operator fun setValue(son: Son, property: KProperty<*>, i: Int) {
        儿子的压岁钱 = i - 50
        妈妈自己的钱 = 50
    }

}