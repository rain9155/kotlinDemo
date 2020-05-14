package com.example.hy.kotlin_day_06

import kotlinx.coroutines.*
import java.lang.Exception
import javax.xml.bind.JAXBElement
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * suspend关键字：
 * 1、suspend关键字是用来修饰方法的，标识这个方法是一个耗时方法，suspend方法只能在协程或另一个suspend方法中调用；
 * 2、当协程遇到suspend方法时，它会把自己挂起(释放当前线程的执行权)，当suspend方法"异步返回"后，协程又会被恢复(再次被原来线程或新的线程调度)；
 * 3、在suspend方法内部要自己手动启动新的协程，并把这个协程分派到另一个线程上执行，完成线程切换, 在完成耗时操作后，要自己手动切换回当前协程，
 *    这个挂起/恢复操作可以通过调用Kotlin协程框架自带的suspend函数，如delay、withContext、async/await等方法完成.
 *
 * ps:在suspend方法调用链的终点，最终是通过suspendCoroutine等方法挂起当前协程，然后在suspendCoroutine方法的block代码块中完成耗时操作，
 *    完成操作后，在block块内部通过调用Continuation的resume/resumeWithException方法恢复外部被挂起的协程
 */
fun main(){
    println("Start")

    //开启协程，异步获取数据
    val job = GlobalScope.launch {
        println("start load data, thread = ${getThreadName()}")

        var data = loadDataFromCache()
        if(data.isEmpty()){
            data = loadDataFromLocal()
        }
        if(data.isEmpty()){
            data = loadDataFromNetwork()
        }

        println(data)
    }

    runBlocking {
        job.join()
    }

    println("End")
}

/**
 * 模拟内存加载
 */
suspend fun loadDataFromCache(): String = GlobalScope.async {
    println("loading data from cache, thread = ${getThreadName()}")
    delay(1000)
    return@async ""
}.await()

/**
 * 模拟本地加载
 */
suspend fun loadDataFromLocal(): String = withContext(Dispatchers.IO){
    println("loading data from local, thread = ${getThreadName()}")
    delay(1000)
    return@withContext ""
}

/**
 * 模拟网络加载
 */
suspend fun loadDataFromNetwork(): String = suspendCoroutine {
    //从远端异步获取数据
    println("loading data from network, thread = ${getThreadName()}")
    //调用resume方法恢复被挂起的协程
    it.resume("data from network")
    //调用resumeWithException方法可以抛出一个异常，外部可以捕获，通过捕获异常恢复协程
    //it.resumeWithException(Exception())
}


