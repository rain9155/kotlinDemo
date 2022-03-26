package com.example.kotlindemo.kotlin_day_06

import kotlinx.coroutines.*
import java.lang.NullPointerException

/**
 * supervisorScope作用域：supervisorScope与coroutineScope区别是它不会产生异常传播
 */
fun main(){
    println("Start")

    runBlocking {
        //supervisorTask1等效于supervisorTask2
        println("supervisorTask1 result = " + supervisorTask1())
        println("supervisorTask2 result = " + supervisorTask2())
    }

    println("End")
}

/**
 * supervisorScope：
 * 1、当supervisorScope作用域中所有协程完成任务后，父协程才会结束返回；
 * 2、当supervisorScope作用域发生异常或被取消，其作用域内的所有协程也会被取消；
 * 2、当supervisorScope作用域中某个协程失败抛出异常，并不影响其他协程的运行，同时这个异常会被子协程的异常处理器处理.
 */
suspend fun supervisorTask1() = supervisorScope {
    joinAll(childJob1(), childJob2())
    return@supervisorScope "supervisorJob1 completed"
}

/**
 * supervisorTask1等效于supervisorTask2，因为supervisorScope作用域底层也是使用SupervisorJob指定协程作用域的上下文
 */
suspend fun supervisorTask2(): String{
    //使用SupervisorJob构造CoroutineScope
    val coroutineScope = CoroutineScope(SupervisorJob())
    return with(coroutineScope){
        joinAll(childJob1(), childJob2())
        return@with "supervisorJob2 completed"
    }
}

private fun CoroutineScope.childJob1(): Job{
    val childCoroutineExceptionHandler = CoroutineExceptionHandler {coroutineContext, throwable ->
        println("child coroutineExceptionHandler catch exception, msg = ${throwable.message}")
    }
    return launch(childCoroutineExceptionHandler) {
            try {
                throw NullPointerException("exception thrown from childJob1")
            } finally {
                println("childJob1 was cancelled by exception")
            }
        }
}

private fun CoroutineScope.childJob2(): Job {
    return launch {
        try {
            repeat(3) {
                delay(1000)
                println("childJob2 is stilling running")
            }
        } finally {
            println("childJob2 is finished")
        }
    }
}



