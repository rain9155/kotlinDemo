package com.example.hy.kotlin_day_06

import kotlinx.coroutines.*

/**
 * 协程的异常处理：
 * 1、当父协程内某个子协程抛出异常时，父协程也会抛出异常, 在抛出异常之前，父协程会取消所有子协程的运行；
 * 2、在启动协程时可以通过[CoroutineExceptionHandler]指定协程的异常处理器，它会处理协程内部未捕获的异常.
 */
fun main(){
    println("Start")

    /**
     * 当使用launch和async启动根协程而不是作为其他协程的子协程时，
     * 在launch中抛出的未捕获异常，会被默认的异常处理器处理，
     * 在async中抛出的未捕获异常，需要用户调用await方法时才抛出
     */
    runBlocking {
        val job = GlobalScope.launch {
            println("throwing exception from launch")
            throw IndexOutOfBoundsException()
        }
        job.join()
        println("job failed")

        val deferred = GlobalScope.async {
            println("throwing exception from async")
            throw NullPointerException()
        }
        try {
            deferred.await()
        }catch (e: NullPointerException){
            println("catch exception thrown from async")
        }
    }

    println("-------------------------------------------")

    /**
     * 可以通过CoroutineExceptionHandler自定义异常处理器，作为根协程启动的上下文, 异常处理器只对launch启动的根协程有效，
     * 而对async启动的根协程无效，因为async默认会捕获所有未捕获异常并把它放在Deferred中，等到用户调用await方法才抛出
     */
    runBlocking {
        //自定义ExceptionHandle
        val handler = CoroutineExceptionHandler{ coroutineContext, throwable ->
            println("my coroutineExceptionHandler catch exception, msg = ${throwable.message}")
        }

        //handler有效
        val job = GlobalScope.launch(handler){
            throw IndexOutOfBoundsException("exception thrown from launch")
        }

        //handler无效
        val deferred = GlobalScope.async(handler){
            throw NullPointerException("exception thrown from async")
        }

        joinAll(job, deferred)
    }

    println("-------------------------------------------")

    /**
     * 子协程抛出的未捕获异常会委托父协程的异常处理器处理，子协程设置的异常处理器永远不会生效（SupervisorJob除外）
     */
    runBlocking {
        //根协程的Handler
        val parentHandler = CoroutineExceptionHandler{coroutineContext, throwable ->
            println("parent coroutineExceptionHandler catch exception, msg = ${throwable.message}")
        }
        //启动根协程
        val job = GlobalScope.launch(parentHandler){

            //子协程的Handler
            val childHandler = CoroutineExceptionHandler{coroutineContext, throwable ->
                println("child coroutineExceptionHandler catch exception, msg = ${throwable.message}")
            }
            //启动子协程
            val childJob = launch(childHandler){
                throw IndexOutOfBoundsException("exception thrown from child launch")
            }
            childJob.join()

        }
        job.join()
    }

    println("-------------------------------------------")

    /**
     * 取消协程时会抛出一个CancellationException，它会被所有异常处理器省略，但可以捕获它，
     * 同时当子协程抛出CancellationException时，并不会终止当前父协程的运行
     */
    runBlocking {
        val job = GlobalScope.launch {

            val jobChild = launch {
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
            jobChild.cancelAndJoin()

            println("parent is still running")
        }
        job.join()
    }

    println("-------------------------------------------")

    /**
     * 当父协程的子协程同时抛出多个异常时，异常处理器只会捕获第一个协程抛出的异常，后续协程抛出的异常被保存在第一个异常的suppressed数组中
     */
    runBlocking {
        val handler = CoroutineExceptionHandler{coroutineContext, throwable ->
            println("my coroutineExceptionHandler catch exception, msg = ${throwable.message}, suppressed = ${throwable.suppressed.contentToString()}")
        }
        val job = GlobalScope.launch(handler){
            launch {
                try {
                    delay(2000)
                }finally {
                    //第二个抛出的异常
                    throw IndexOutOfBoundsException("exception thrown from first child launch")
                }
            }.start()

            launch {
                delay(1000)
                //第一个抛出的异常
                throw NullPointerException("exception thrown from second child launch")
            }.start()
        }
        job.join()
    }

    println("End")
}