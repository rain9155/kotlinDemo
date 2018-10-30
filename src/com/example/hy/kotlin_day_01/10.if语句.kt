package com.example.hy.kotlin_day_01

/**
 * 有返回值的if语句
 */
fun main(args: Array<String>) {

    //需求：求a，b中最大值

    //java的if语句没有返回值
    println(max(10, 20))

    //kotlin的if语句有返回值
    println(max2(10, 20))
    println(max3(10, 20))

}

/**
 * java做法
 */
fun max(a : Int, b : Int) : Int{
    if(a > b){
        return a;
    }else{
        return b;
    }
}

/**
 * kotlin做法1
 */
fun max2(a : Int, b : Int) : Int{
    return if(a > b) return a else return b
}

/**
 * kotlin做法2：有返回值的if语句
 */
fun max3(a : Int, b : Int) : Int{
    return if(a > b)  a else b
}