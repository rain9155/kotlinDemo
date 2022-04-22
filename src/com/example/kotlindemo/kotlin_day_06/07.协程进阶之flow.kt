package com.example.kotlindemo.kotlin_day_06

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

/**
 * Flow:
 * 1、flow代表的是异步数据流，和同步数据流sequences类似，同时跟Rxjava类似支持多种操作符对数据流进行转化，并且由于面向扩展设计，flow的操作符实现比Rxjava更简单；
 * 2、flow是冷流，创建flow时并不会执行builder块中的代码，只有在调用flow的末端操作符时才会激活flow(执行代码打开资源)，并且在末端操作符返回前释放掉所有打开的资源；
 * 3、flow是可取消的，它遵循协程的取消，当flow被挂起在可取消的suspend方法中时例如delay，如果suspend方法被取消那么flow也会被取消不会继续执行；
 * 4、
 *
 * 背压(Back pressure)：
 *
 * 中间操作符(Intermediate operators)：
 *
 * 末端操作符(Terminal operators):
 *
 * ps：
 *
 *
 * 参考文档：
 * - [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html)
 * - [kotlinx.coroutines.flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/index.html)
 * - [kotlin.sequences](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.sequences/)
 * - [Simple design of Kotlin Flow](https://elizarov.medium.com/simple-design-of-kotlin-flow-4725e7398c4c)
 * - [Execution context of Kotlin Flows](https://elizarov.medium.com/execution-context-of-kotlin-flows-b8c151c9309b)
 */
fun main() {
    println("Start")

    runBlocking {
        buildFlowExample()
    }

    runBlocking {
        cancelableFlowExample()
    }

    println("End")
}

/**
 * 构建flow，可以通过flow方法，也可以通过asFlow、flowof等工具方法把列表转化为flow，相应方法都在[kotlinx.coroutines.flow.Builders]中
 */
suspend fun buildFlowExample() {
    println("buildFlowExample")

    /**
     * 使用flow方法构建
     */
    flow {
        for(i in 1..3) {
            emit(i)
        }
    }.collect {
        println("collected $it")
    }

    /**
     * 使用asFlow方法构建
     */
    (1..3).asFlow().collect {
        println("collected $it")
    }

    /**
     * 使用flowof方法构建
     */
    flowOf(1, 2, 3).collect {
        println("collected $it")
    }

    println("Done")
}

/**
 * 取消flow，flow发送2个数据后，delay方法响应withTimeoutOrNull的超时取消
 */
suspend fun cancelableFlowExample() {
    println("cancelableFlowExample")

    withTimeoutOrNull(250) {
        flow {
            for(i in 1..3) {
                delay(100)
                emit(i)
            }
        }.collect {
            println("collected $it")
        }
    }

    println("Done")
}