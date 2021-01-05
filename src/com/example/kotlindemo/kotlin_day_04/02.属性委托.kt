package com.example.kotlindemo.kotlin_day_04

import kotlin.properties.ReadWriteProperty
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
    son.压岁钱 = 50
    //取钱
    println(son.压岁钱)//输出：150

}

/**
 * 儿子
 */
class Son{

    var 压岁钱 : Int by Mother()
}

/**
 * 妈妈(ReadWriteProperty接口的实现并不是必须的，只要被委托的类含含有相同签名的get/setValue方法就行)
 */
class Mother : ReadWriteProperty<Son, Int>{

    var 儿子的压岁钱 = 0

    //儿子要压岁钱
    override operator fun getValue(thisRef: Son, property: KProperty<*>): Int {
        return 儿子的压岁钱
    }

    //儿子存压岁钱, value表示要存的钱
    override operator fun setValue(thisRef: Son, property: KProperty<*>, value: Int) {
        儿子的压岁钱 += value
    }

}