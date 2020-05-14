package com.example.hy.kotlin_day_06

import kotlinx.coroutines.*
import kotlin.coroutines.EmptyCoroutineContext

/**
 * kotlin协程：
 * 1、协程它是一片包含特定逻辑的代码块，这个代码块可以被调度到不同线程上执行；
 * 2、协程中的代码逻辑是顺序执行的，前一部分执行完，后一部分才能执行；
 * 3、协程它是编译器的实现，不依赖与操作系统的实现，它的底层通过Interceptor来关联线程或共享线程池实现；
 * 4、协程中的某部分需要执行耗时操作时，当前协程可以被挂起等待，在等待期间，承载协程的线程可以被释放，从而完成其他逻辑操作；
 * 5、协程被suspend分成多个Continuation，每一个Continuation就是协程中的一段执行流，每一段执行流可以被不同的线程执行.
 *
 * 协程的3种启动方式：
 * 1、直接通过runBlocking方法启动，这个方法会阻塞当前线程直到协程完成，主要用于单元开发（不推荐）
 * 2、通过GlobalScope的launch/async方法启动，这个方法启动的协程的生命周期和当前应用程序同步（不推荐）
 * 3、自行创建CoroutineScope(协程范围)，指定它的上下文CoroutineContext，调用它launch/async方法启动（推荐）
 *
 * ps：
 * 1、通过launch方法会返回一个Job对象，调用Job对象的start、cancel方法可以启动、取消协程的运行，调用它的join方法(suspend标记)可以等待协程的完成；
 * 2、通过async方法会返回一个Deferred<T>对象，它是Job的子类，调用它的await方法(suspend标记)可以返回协程的结果T.
 *
 * 启动协程时的3个关键参数：
 * 1、CoroutineContext：协程上下文，可以通过[Dispatchers]指定协程运行的线程
 * 2、CoroutineStart：协程的启动模式，可以通过[CoroutineStart]指定，默认是立即启动
 * 3、suspend CoroutineScope.() -> Unit：协程运行的代码块
 */
fun main(args: Array<String>){
    println("Start, thread = " + getThreadName())

    /** 1、通过runBlocking方法启动 */
    runBlocking{
        println("Hello World, thread = " + getThreadName())
    }

    /** 2、通过GlobalScope的launch方法启动 */
    GlobalScope.launch{
        delay(1000)
        println("World, thread = " + getThreadName())
    }
    println("Hello, thread = " + getThreadName())

    /** 3、 自行创建CoroutineScope，调用launch方法启动 */
    val coroutineScope: CoroutineScope = CoroutineScope(EmptyCoroutineContext)
    val job: Job = coroutineScope.launch(start = CoroutineStart.LAZY){
        delay(1000)
        println("Hello World, thread = " + getThreadName())
    }
    job.start()

    Thread.sleep(2000)//进程保活

    println("End, thread = " + getThreadName())
}

fun getThreadName(): String = Thread.currentThread().name