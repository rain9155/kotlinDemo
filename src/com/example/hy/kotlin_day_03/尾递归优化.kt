package com.example.hy.kotlin_day_03

/**
 * 尾递归优化
 * 尾递归定义：函数在调用完自身后没有做其他操作
 * 尾递归原理：底层把递归转换为迭代
 * 尾递归优化步骤：
 * 1、把递归转换为尾递归
 * 2、使用tailrec修饰符
 */
fun main(args: Array<String>) {
    //需求：求100000的累加
    /** 没有优化前 */
    //val result = sum1(100000)
   // println(result)//报错：StackOverflowError

    /** 优化后 */
    val result2 = sum2(100000)
    println(result2)//输出：705082704
}

/**
 * 递归
 */
fun sum1(n : Int) : Int =
    if(n == 1){
         1
    }else{
         n + sum1(n - 1)
    }


/**
 * 尾递归优化
 */
tailrec fun sum2(n : Int, result : Int = 0) : Int =
    if(n == 1) {
         result + 1
    }else{
         sum2(n - 1, result + n)
    }
