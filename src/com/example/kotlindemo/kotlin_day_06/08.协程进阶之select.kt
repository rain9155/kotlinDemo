package com.example.kotlindemo.kotlin_day_06

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.selects.*
import kotlinx.coroutines.sync.Mutex

/**
 * select:
 * 1、协程的select类似与java NIO和Unix IO多路复用中的select概念相似，协程的select可以同时等待多个可选择(selectable)的挂起函数的结果，然后选择第一个挂起函数返回的结果；
 * 2、可选择的挂起函数的返回结果回调通过[select]的[SelectBuilder]中的clause注册，当调用select方法注册clause时，select方法会挂起当前协程直到某个clause被选择或失败.
 *
 * Suspend function和对应的Select clause：
 * | Suspending function            | Select clause                    ｜
 * | -------------------------------| ----------------------------------
 * | [Job.join]                     | [Job.onJoin]                     |
 * | [Deferred.await]               | [Deferred.onAwait]               |
 * | [SendChannel.send]             | [SendChannel.onSend]             |
 * | [ReceiveChannel.receive]       | [ReceiveChannel.onReceive]       |
 * | [ReceiveChannel.receiveOrNull] | [ReceiveChannel.onReceiveOrNull] |
 * | [Mutex.lock]                   | [Mutex.onLock]                   |
 * | [delay]                        | [SelectBuilder.onTimeout]        |
 *
 * ps：对于select的能力使用flow的相关操作符也可以实现
 *
 * 参考文档：
 * - [Select expression](https://kotlinlang.org/docs/select-expression.html)
 * - [聊聊IO多路复用之select、poll、epoll详解](https://www.jianshu.com/p/dfd940e7fca2)
 */
fun main() {
    println("Start")

    runBlocking {
        selectFromChannelsExample()
    }

    runBlocking {
        selectFromDeferredsExample()
    }

    println("End")
}

/**
 * select多个Channel发送的结果
 */
suspend fun selectFromChannelsExample() {
    println("selectFromChannelsExample")

    coroutineScope {
        val produceA = produce<String> {
            delay(100)
            send("A")
        }
        val produceB = produce<String> {
            delay(300)
            send("B")
        }
        val selectedValue = select<String> {
            produceA.onReceive {
                "received $it"
            }
            produceB.onReceive {
                "received $it"
            }
        }
        println(selectedValue)
        //关闭发送端
        coroutineContext.cancelChildren()
    }

    println("Done")
}

/**
 * select多个async返回的Deferred结果
 */
suspend fun selectFromDeferredsExample() {
    println("selectFromDeferredsExample")

    coroutineScope {
        val deferredA = async {
            delay(100)
            return@async "A"
        }
        val deferredB = async {
            delay(200)
            return@async "B"
        }
        val selectedValue = select<String> {
            deferredA.onAwait {
                "awaited $it"
            }
            deferredB.onAwait {
                "awaited $it"
            }
        }
        println(selectedValue)
    }

    println("Done")
}



