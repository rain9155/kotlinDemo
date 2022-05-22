package com.example.kotlindemo

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor

fun main(){
    val handler = CoroutineExceptionHandler{coroutineContext, throwable ->
        println("my coroutineExceptionHandler catch exception, msg = ${throwable.message}")
    }
    val parentJob = GlobalScope.launch(handler){
        val childJob = launch {
            try {
                delay(Long.MAX_VALUE)
            }catch (e: CancellationException){
                println("catch cancellationException thrown from child launch")
                println("rethrow cancellationException")
                throw CancellationException()
            }finally {
                println("child was canceled")
            }
        }
        //取消子协程
        childJob.cancelAndJoin()
        println("parent is still running")
    }
    parentJob.start()

    //进程保活
    Thread.sleep(1000)
}


