package com.example.kotlindemo.kotlin_day_06

import kotlinx.coroutines.*

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
 * 启动协程时的4个关键参数：
 * 1、CoroutineScope：协程作用域，它用来追踪通过它的launch或async方法启动的协程，需要时，可以通过它的cancel方法取消所有协程的运行,
 *                   这三个方法都是CoroutineScope的扩展方法，同时创建CoroutineScope时，需要指定一个CoroutineContext；
 *
 * 2、CoroutineContext：协程上下文，它等于[Job] + [Dispatchers] + [CoroutineName] + [CoroutineExceptionHandler]，含义如下：
 *                     Job: 协程的唯一标识，用来控制协程的生命周期(new、active、completing、completed、cancelling、cancelled)
 *                     Dispatchers：指定协程运行的线程(IO、Default、Main、Unconfined)
 *                     CoroutineName：指定协程的名称，默认为coroutine
 *                     CoroutineExceptionHandler：指定协程的异常处理器，用来处理未捕获的异常
 *
 * 3、CoroutineStart：协程的启动模式，可以通过[CoroutineStart]指定，默认是立即启动
 *
 * 4、suspend CoroutineScope.() -> Unit：协程运行的代码块
 *
 * ps：
 * 1、协程的上下文CoroutineContext可以通过 + 来组合，+号右边的覆盖左边的；
 * 2、当新建一个协程时，新协程的CoroutineContext = 父协程的CoroutineContext + 参数的CoroutineContext
 * 3、每新建一个协程，总会创建一个新的Job
 *
 * 参考文档：
 * - [Coroutines basics](https://kotlinlang.org/docs/coroutines-basics.html)
 * - [Coroutine context and dispatchers](https://kotlinlang.org/docs/coroutine-context-and-dispatchers.html)
 * - [kotlinx.coroutines](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/index.html)
 * - [Coroutine Context and Scope](https://elizarov.medium.com/coroutine-context-and-scope-c8b255d59055)
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
    val coroutineScope: CoroutineScope = CoroutineScope(Job() + Dispatchers.IO + CoroutineName("myCoroutine"))
    val job: Job = coroutineScope.launch(start = CoroutineStart.LAZY){
        delay(1000)
        println("Hello World, thread = " + getThreadName())
    }
    job.start()

    Thread.sleep(2000)//进程保活

    println("End, thread = " + getThreadName())
}

fun getThreadName(): String = Thread.currentThread().name