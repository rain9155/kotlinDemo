package com.example.kotlindemo.kotlin_day_06

import kotlinx.coroutines.*

/**
 * coroutineScope：使用async配合coroutineScope可以很容易地实现结构化编程
 */
fun main(){
    println("Start")

    runBlocking {
        //求和
        println("result = " + sumTask())
    }

    println("End")
}

/**
 * 结构化并发编程:
 * 1、当coroutineScope作用域中所有协程完成任务后，父协程才会结束返回；
 * 2、当coroutineScope作用域发生异常或被取消，其作用域内的所有协程也会被取消；
 * 3、当coroutineScope作用域中某个协程失败抛出异常，父协程会取消作用域内的所有协程，
 *    当所有协程取消后，父协程也会抛出异常，并且此异常会被父协程的异常处理器处理
 */
suspend fun sumTask(): Int = coroutineScope {
    val result1 = async { taskAsync1() }
    val result2 = async { taskAsync2() }
    return@coroutineScope result1.await() + result2.await()
}

suspend fun taskAsync1(): Int{
    delay(1000)
    return 100
}

suspend fun taskAsync2(): Int{
    delay(1000)
    return 200
}