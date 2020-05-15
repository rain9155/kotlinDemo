package com.example.hy.kotlin_day_06

import kotlinx.coroutines.*
import kotlin.coroutines.suspendCoroutine

/**
 * async/await方法：
 * async是CoroutineScope中的扩展方法，await是async方法返回值Deferred<T>中的suspend方法, 通过async方法可以启动一个协程，
 * 通过返回值的await方法可以返回协程的结果，使用async配合coroutineScope可以很容易地实现结构化编程
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
 * 1、当coroutineScope作用域中某个协程失败抛出异常，当前父协程也会抛出异常，从而结束作用域内的所有协程；
 * 2、当coroutineScope作用域中所有协程完成任务后，父协程才会结束返回
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