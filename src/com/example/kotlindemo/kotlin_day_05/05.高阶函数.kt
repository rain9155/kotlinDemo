package com.example.kotlindemo.kotlin_day_05

/**
 * kotlin的高阶函数（函数能引用多次）
 * kotlin的函数里面可以传递函数参数就被称为高阶函数
 */
fun main(args: Array<String>) {

    val a = 10
    val b = 20
    //::后接函数名 - 表示一个函数的引用
    //求和
    val result = cal(a, b, ::sum)
    println(result)//30
    //求差
    val result2 = cal(a, b, ::sub)
    println(result2)///-10
}

/**
 * 高阶函数
 * @param a 第一个参数
 * @param b 第二个参数
 * @param unit 一个函数引用
 * @return 利用传递进来的函数，计算出结果并返回
 */
fun cal(a : Int, b : Int, unit : (Int, Int)  -> Int) : Int{
    //return unit(a, b) 或
    return unit.invoke(a, b)
}

/**
 * 求和函数
 */
fun sum(a : Int, b : Int) : Int{
    return a + b
}

/**
 * 求差函数
 */
fun sub(a : Int, b : Int) : Int{
    return a - b;
}