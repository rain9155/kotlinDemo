package com.example.kotlindemo.kotlin_day_06

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.*
import kotlinx.coroutines.channels.*
import java.util.concurrent.*
import java.util.concurrent.atomic.*
import kotlin.system.measureTimeMillis

/**
 * 协程的并发问题：由于协程可以使用上下文中的[Dispatchers]把自己分发到不同的线程上执行，所以如果在不同线程的运行协程中访问共享可变状态就会出现并发问题
 *
 * 协程的并发处理(保证共享可变状态的一致性)：
 * 1、使用线程安全的数据结构包装共享可变状态，例如[java.util.concurrent.atomic]包下的原子操作类、[ConcurrentHashMap]、[CopyOnWriteArrayList]等；
 * 2、使用[newSingleThreadContext]创建单一线程的协程上下文，把对于共享可变状态的访问通过[withContext]切换到单一线程的协程上下文下执行；
 * 3、使用互斥锁，例如[synchronized]、ReentrantLock等，但是在协程中可以使用[Mutex]，它的lock/unlock方法都是suspend的对协程更加友好；
 * 4、使用Actor Model模式，把对于共享可变状态的维护放在[actor]中，然后所有协程通过消息传递来访问actor，从而访问共享可变状态.
 *
 * ps:
 * 1、线程同步约束(thread confinement)的代码范围粒度不同，并发处理所需的时间也不一样，一般细粒度范围(fine-grained)比粗粒度范围(coarse-grained)的处理耗时时间更长；
 * 2、协程的Mutex与线程的synchronized、ReentrantLock的区别在于它的相应方法都是suspend，所以Mutex并不会阻塞整个线程；
 * 3、Actor模式是处理并发问题的一个模型，在Actor模式中一切都为actor，actor之间只能通过消息传递访问，协程中的actor是通过Channel实现；
 *
 * 参考文档：
 * - [Shared mutable state and concurrency](https://kotlinlang.org/docs/shared-mutable-state-and-concurrency.html)
 * - [The actor model in 10 minutes](https://www.brianstorti.com/the-actor-model/)
 */
fun main() {
    println("Start")

    runBlocking {
        unsafeConcurrentModify()
    }

    runBlocking {
        concurrentModifyWithThreadsafeStructures()
    }

    runBlocking {
        concurrentModifyWithSingleThreadContext()
    }

    runBlocking {
        concurrentModifyWithMutex()
    }

    runBlocking {
        concurrentModifyWithActor()
    }

    println("End")
}

const val MODIFY_COUNT = 10000

/**
 * 多线程修改sharedState，出现并发问题
 */
suspend fun unsafeConcurrentModify() = withContext(Dispatchers.Default) {
    println("unsafeConcurrentModify")

    var sharedState = 0
    val time = measureTimeMillis {
        coroutineScope {
            repeat(MODIFY_COUNT) {
                launch {
                    sharedState++
                }
            }
        }
    }
    println("completed $MODIFY_COUNT modify in $time ms, sharedState = $sharedState")

    println("Done")
}

/**
 * 多线程修改sharedState，使用线程安全的数据结构包装sharedState
 */
suspend fun concurrentModifyWithThreadsafeStructures() = withContext(Dispatchers.Default) {
    println("concurrentModifyWithThreadsafeStructures")

    val sharedState = AtomicInteger(0)
    val time = measureTimeMillis {
        coroutineScope {
            repeat(MODIFY_COUNT) {
                launch {
                    sharedState.incrementAndGet()
                }
            }
        }
    }
    println("completed $MODIFY_COUNT modify in $time ms, sharedState = ${sharedState.get()}")

    println("Done")
}

/**
 * 多线程修改sharedState，把对sharedState修改切换到单一线程上下文中
 */
suspend fun concurrentModifyWithSingleThreadContext() = withContext(Dispatchers.Default) {
    println("concurrentModifyWithSingleThreadContext")

    var sharedState = 0
    val time = measureTimeMillis {
        coroutineScope {
            val singleThreadContext = newSingleThreadContext("SingleThreadContext")
            repeat(MODIFY_COUNT) {
                launch {
                    //也可以把withContext(singleThreadContext)移动到外层，让所有协程在单一线程上下文中启动
                    withContext(singleThreadContext) {
                        sharedState++
                    }
                }
            }
        }
    }
    println("completed $MODIFY_COUNT modify in $time ms, sharedState = $sharedState")

    println("Done")
}

/**
 * 多线程修改sharedState，把对sharedState修改放在Mutex的互斥范围中
 */
suspend fun concurrentModifyWithMutex() = withContext(Dispatchers.Default) {
    println("concurrentModifyWithMutex")

    var sharedState = 0
    val time = measureTimeMillis {
        coroutineScope {
            val mutex = Mutex()
            repeat(MODIFY_COUNT) {
                launch {
                    //可以使用mutex.withLock方法代替try{ mutex.lock() } finally{ mutex.unlock() }
                    try {
                        mutex.lock()
                        sharedState++
                    }finally {
                        mutex.unlock()
                    }
                }
            }
        }
    }
    println("completed $MODIFY_COUNT modify in $time ms, sharedState = $sharedState")

    println("Done")
}

abstract class Msg
object IncMsg : Msg()
class GetMsg(val response: CompletableDeferred<Int>) : Msg()

/**
 * 把对sharedState的维护放在Actor中，通过消息来访问它
 */
fun CoroutineScope.stateActor() = actor<Msg> {
    var sharedState = 0
    for (msg in channel) {
        when(msg) {
            is IncMsg -> {
                sharedState++
            }
            is GetMsg -> {
                msg.response.complete(sharedState)
            }
        }
    }
}

/**
 * 多线程通过actor消息修改sharedState，通过消息获取sharedState的值
 */
suspend fun concurrentModifyWithActor() = withContext(Dispatchers.Default) {
    println("concurrentModifyWithActor")
    val actor = stateActor()
    val time = measureTimeMillis {
        coroutineScope {
            repeat(MODIFY_COUNT) {
                launch {
                    actor.send(IncMsg)
                }
            }
        }
    }
    val deferredState = CompletableDeferred<Int>()
    actor.send(GetMsg(deferredState))
    println("completed $MODIFY_COUNT modify in $time ms, sharedState = ${deferredState.await()}")

    println("Done")
}




