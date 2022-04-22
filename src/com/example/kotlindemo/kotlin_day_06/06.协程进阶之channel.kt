package com.example.kotlindemo.kotlin_day_06

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Channel:
 * 1、Channel是协程之间通信的一种方式，不同于Deferred方式只能传递一个值，它可以在协程之间传递多个值；
 * 2、Channel实现了SendChannel和ReceiveChannel接口，通过SendChannel的send方法发送数据，通过ReceiveChannel的receive方法接收数据；
 * 3、Channel类似于java中BlockQueue，但是它的方法并不是阻塞的(block)而是挂起的(suspend), 同时它是可以被关闭的(close/cancel);
 * 4、Channel是热流，只要发送端被创建，那么它就会执行块中的代码打开资源并挂起等待接收端使用，所以需要持有接收端引用并在不需要时关闭它以释放资源.
 *
 * Channel构造的capacity参数：
 * 当capacity = 0时(默认capacity = 0)，对应[Channel.RENDEZVOUS], 发送端/接收端每调用一次send/receive，接收端/发送端就要调用一次receive/send，否则会挂起；
 * 当capacity > 0时，对应[Channel.BUFFERED]或[Channel.UNLIMITED], 发送端/接收端在挂起前可以连续地调用多次send/receive，即当channel满了之后才会挂起；
 * 当capacity = -1时, 对应[Channel.CONFLATED], channel会合并发送端连续调用的send操作，接收端只会receive到最近一次发送的数据.
 *
 * ps:
 * 1、当Channel被close/cancel后，后续的send或receive操作都会失败，并且会抛出异常；
 * 2、由于Channel是热流使用不当会造成资源浪费、内存泄露，所以要适当地配合结构化编程使用它或者使用冷流flow代替它.
 *
 * 参考文档:
 * - [Channels](https://kotlinlang.org/docs/channels.html)
 * - [kotlinx.coroutines.channels](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/index.html)
 * - [Cold flows, hot channels](https://elizarov.medium.com/cold-flows-hot-channels-d74769805f9)
 */
fun main(){
    println("Start")

    /** 在两个协程之间模拟数据的发送/接收 */
    runBlocking {
        val channel = Channel<Int>()

        launch {
            for(num in 1..5){
                println("send $num")
                channel.send(num)
            }
        }

        repeat(5){
            val num = channel.receive()
            println("receive $num")
        }
    }

    println("-------------------------------------------")

    /** channel发送端的关闭和接收端的迭代 */
    runBlocking {
        val channel = Channel<Int>()

        launch {
            for(num in 1..5){
                println("send $num")
                channel.send(num)
            }
            //数据发送完关闭发送通道
            channel.close()
        }

        //使用迭代来接收数据
        for(num in channel){
            println("receive $num")
        }
    }

    println("-------------------------------------------")

    /** 通过produce方法创建channel的发送端， produce方法返回channel的接收端，在接收端迭代 */
    runBlocking {
        val nums = produceNums()
        val filetedNums = filter(nums)
        filetedNums.consumeEach {
            println("receive $it")
        }
    }

    println("End")
}

fun CoroutineScope.produceNums() = produce<Int> {
    for(num in 1..5){
        println("send $num")
        send(num)
    }
}

fun CoroutineScope.filter(nums: ReceiveChannel<Int>) = produce<Int> {
    for(num in nums){
        if(num % 2 == 0){
            send(num)
        }
    }
}