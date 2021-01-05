package com.example.hy.kotlin_day_03

/**
 * kotlin的运算符重载：
 * kotlin中所有的运算符都对应一个方法，运算符相当于方法的简写
 * 运算符重载步骤：
 * 1、找到运算符对应的函数
 * 2、函数前加上operator关键字
 */
fun main(args: Array<String>) {
    //需求：实现两个girl对象的相加
    var girl1 = Girl()
    var girl2 = Girl()
    //两个对象相加，重载运算符把年龄和名字相加
    val girl = girl1 + girl2
    println(girl.name)//输出：rainrain
    println(girl.age)//输出：36


}

/**
 * girl对象
 */
class Girl{

    operator fun plus(girl2: Girl) : Girl{
        this.age += girl2.age
        this.name += girl2.name
        return this
    }

    var age : Int = 18
    var name :String = "rain"

    //常见运算符及对应方法
    // !a      a.not()
    // a++     a.inc() + see below
    // a--     a.dec() + see below
    //...
    // a + b   a.plus(b)
    // a - b   a.minus(b)
    // a * b   a.times(b)
    // a / b   a.div(b)
    // a % b   a.rem(b)
    // a ..b   a.rangeTo(b)
    // a += b  a.plusAssign(b)
    // a == b  a?.equals(b)?:(b === null)
    // a > b   a.compareTo(b) > 0
    //...
    // a in b  b.contains(a)
    // a[i]    a.get(i)
    // a[i]=b  a.set(i, b)
    // a[i,j]  a.get(i, j)
    //a[i,j]=b a.set(i, j, b)
    //...
    // a()     a.invoke()
    // a(i)    a.invoke(i)
    //...
}