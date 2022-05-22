package com.example.kotlindemo.kotlin_day_06

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.IllegalStateException
import kotlin.system.measureTimeMillis

/**
 * Flow:
 * 1、flow代表的是异步数据流，和同步数据流sequences类似，同时跟Rxjava类似支持多种操作符对数据流进行转化，并且由于面向扩展设计，flow的操作符实现比Rxjava更简单；
 * 2、flow是冷流，创建flow时并不会执行builder块中的代码，只有在调用flow的末端操作符时才会激活flow(执行代码打开资源)，并且在末端操作符返回前释放掉所有打开的资源；
 * 3、flow是可取消的，它遵循协程的取消，builder的emit方法每次执行前都会调用[Job.ensureActive]方法检查当前协程是否被取消，如果取消就会抛出异常并不再继续执行；
 * 4、flow具有上下文保护，它总是运行在调用collect方法的协程上下文中，在builder块中进行上下文切换会抛出异常，如果需要改变flow上游的运行上下文请使用flowOn操作符；
 * 5、flow是顺序执行的，如果没有使用buffer相关的操作符，默认都在同一协程上依次处理从上游到下游经过中间操作符处理后的元素，这个过程是线性的，所以如果上游和下游都运行着很耗时的任务，那么整个flow的收集时间就会变长；
 *
 * 背压(Back pressure)：
 * 背压的定义是当生产者发送元素的速度过快而消费者来不及处理时消费者会通知生产者减低发送元素速率的能力，传统的Reactive Streams处理背压的方法是基于线程同步数据管道，消费者线程通过阻塞生产者的线程把背压应用
 * 于生产者，根据需要向生产者索要更多数据，flow的所有方法都是suspend方法，它默认支持了背压，所以自定义操作符时无须再考虑背压设计问题，当消费者来不及处理生产者的数据时，suspend方法可以挂起生产者的协程，然后
 * 在稍后消费者有能力准备接收更多数据时恢复生产者协程以继续发送数据，flow通过挂起协程而不是阻塞线程来实现背压，flow透明地管理线程间的背压而不阻塞线程，挂起函数将它从单个线程扩展到异步编程领域
 *
 * 创建flow的方法：
 * [flow]: 创建一个冷的flow，当flow开始收集时，执行builder中的代码，在builder中可以使用emit方法发送数据；
 * [flowOf]: flow方法的变种, 通过有限集合创建一个冷的flow；
 * [asFlow]：把任意类型变成冷的flow，例如[Collection]、[Sequence]
 *
 * 中间操作符(Intermediate operators)：
 * [transform]：收集flow发出的元素，执行相应的转换操作，返回转化后的值；
 * [map]、[filter]、[onEach]等：transform的变种，遍历flow发出的元素，执行相应的操作；
 * [take]、[drop]等：限制flow上游发送的元素数量，到达限制后抛出[CancellationException]异常；
 * [zip]、[combine]等：组合两个flow发射的元素，返回组合后的值；
 * [flatMapConcat]、[flatMapMerge]、[flatMapLatest]等：转换flow发送的元素，返回另外一个flow，然后展开(Flattening, 扁平化)返回的flow<flow>类型；
 * [flowOn]、[buffer]、[conflate]等：改变flow上游运行的上下文.
 *
 * 末端操作符(Terminal operators):
 * [launchIn]：在独立的协程中收集flow发出的元素
 * [collect]、[collectIndexed]、[collectLatest]等：收集flow发出的元素，调用这个方法后flow开始执行，如果收集过程中出现异常，collect会抛出对应异常；
 * [toCollection]、[toList]、[toSet]等：把flow转换成各种集合；
 * [reduce]、[fold]、[first]、[single]等：减少flow收集的元素数量.
 *
 * ps：flow是冷流，可以通过[shareIn]或[stateIn]方法把flow变成[SharedFlow]或[StateFlow], 它们是热流
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
        //cancelableFlowExample()
    }

    runBlocking {
        //changeFlowContentExample()
    }

    runBlocking {
        //bufferingFlowExample()
    }

    runBlocking {
        //intermediateOperatorExample()
    }

    runBlocking {
        //terminalOperatorExample()
    }

    runBlocking {
        //flowExceptionHandleExample()
    }

    runBlocking {
        //flowCompleteHandle()
    }

    println("End")
}

/**
 * flow的构建
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
 * flow的取消
 */
suspend fun cancelableFlowExample() {
    println("cancelableFlowExample")

    /**
     * flow发送2个数据后，delay方法响应withTimeoutOrNull的超时取消
     */
    withTimeoutOrNull(250) {
        flow {
            try {
                for(i in 1..3) {
                    delay(100)
                    println("emit $i")
                    emit(i)
                }
            }finally {
                println("final emit")
            }
        }.collect {
            println("collected $it")
        }
    }

    /**
     * cancellable操作符，emit方法会做ensureActive检查，而asFlow、flowOf这些方法为了性能会取消检查，可以通过cancellable操作符让flow变成可取消
     */
    GlobalScope.launch {
        (1..3).asFlow().cancellable().collect {
            println("collected $it")
            if(it == 2) {
                cancel()
            }
        }
    }

    println("Done")
}

/**
 * 切换flow运行上下文，在子线程处理元素，在主线程收集元素
 */
suspend fun changeFlowContentExample() {
    println("changeFlowContentExample")

    /**
     * 错误用法，不要在flow中切换上下文
     */
    flow {
        try {
            //IllegalStateException:  Flow invariant is violated
            withContext(Dispatchers.Default) {
                (1..3).forEach {
                    println("emit $it")
                    emit(it)
                }
            }
        }catch (e: IllegalStateException) {
            println(e.message)
        }
    }.collect {
        println("collected $it")
    }

    /**
     * 正确用法，使用flowOn切换flow上游运行上下文
     */
    flow {
        (1..3).forEach {
            println("emit $it, ${Thread.currentThread().name}")
            emit(it)
        }
    }.flowOn(Dispatchers.Default).map {
        println("map $it, ${Thread.currentThread().name}")
        return@map it * 2
    }.flowOn(Dispatchers.IO).collect {
        println("collected $it, ${Thread.currentThread().name}")
    }

    println("Done")
}

/**
 * flow的并发处理，通过buffer、conflate相关操作符通过Channel在独立协程发射和收集元素
 */
suspend fun bufferingFlowExample() {
    println("changeFlowContentExample")

    /**
     * flow默认顺序处理，总耗时1200ms
     */
    val time1 = measureTimeMillis {
        costTask().collect {
            delay(300)
            println("collected $it")
        }
    }
    println("collect total time1 $time1 ms")

    /**
     * 使用buffer操作符，并发处理每个元素，flow上游和下游并发处理，总耗时1000ms
     */
    val time2 = measureTimeMillis {
        costTask().buffer().collect {
            delay(300)
            println("collected $it")
        }
    }
    println("collect total time2 $time2 ms")

    /**
     * 使用conflate操作符，并发处理最近的元素，flow上游和下游并发处理，下游只处理上游最近发送的元素，总耗时700ms
     */
    val time3 = measureTimeMillis {
        costTask().conflate().collect {
            delay(300)
            println("collected $it")
        }
    }
    println("collect total time3 $time3 ms")

    /**
     * 使用collectLatest末端操作符，收集端只处理上游发送的最新数据，每次上游发送数据时，收集端都会取消然后重新执行，总耗时600ms
     */
    val time4 = measureTimeMillis {
        costTask().collectLatest{
            delay(300)
            println("collected $it")
        }
    }
    println("collect total time4 $time4 ms")

    println("Done")
}

/**
 * 常用中间操作符使用
 */
suspend fun intermediateOperatorExample() {
    println("intermediateOperatorExample")

    /**
     * map操作符，对flow的每个数据进行转换
     */
    (1..3).asFlow().map {
        println("map $it")
        return@map (it * 2).toString()
    }.collect {
        println("collected $it")
    }

    /**
     * transform操作符，是一个FlowCollector，可以emit多次转换后的数据，一个很基本的中间操作符，用来自定义操作符，实现类似map、filter的功能
     */
    (1..3).asFlow().transform {
        emit("transform $it")
        emit((it * 2).toString())
    }.collect {
        println("collected $it")
    }

    /**
     * take操作符，是一个FlowCollector，可以限制flow发送的数据, 当达到限制时会取消flow的执行
     */
    flow {
        try {
            (1..3).forEach {
                println("emit $it")
                emit(it)
            }
        }finally {
            println("final emit")
        }
    }.take(2).collect {
        println("collected $it")
    }

    val nums = (1..2).asFlow().onEach { delay(100) }
    val strs = flowOf("one", "two", "three").onEach { delay(300) }

    /**
     * zip操作符，组合两个flow发射的元素，例如nums中1、2和strs中one、two组合
     */
    var startTime = System.currentTimeMillis()
    nums.zip(strs) { a, b ->
        println("zip $a $b")
        return@zip "$a -> $b"
    }.collect {
        println("collected $it, total time ${System.currentTimeMillis() - startTime} ms")
    }

    /**
     * combine操作符，组合两个flow最近发射的元素，例如由于nums比strs发送速率慢，所以nums最近发送的2会和strs最近发送的one、two、three组合
     */
    startTime = System.currentTimeMillis()
    nums.combine(strs) { a, b ->
        println("combine $a $b")
        return@combine "$a -> $b"
    }.collect {
        println("collected $it, total time ${System.currentTimeMillis() - startTime} ms")
    }

    /**
     * flatMapConcat操作符，转换flow发送的每个元素，返回另外一个flow，然后连接并展开返回的flow<flow>数据
     */
    startTime = System.currentTimeMillis()
    nums.flatMapConcat {
        println("flatMapConcat $it")
        costTask(it.toString())
    }.collect {
        println("collected $it, total time ${System.currentTimeMillis() - startTime} ms")
    }

    println("Done")
}

/**
 * 常用末端操作符使用
 */
suspend fun terminalOperatorExample() {
    println("terminalOperatorExample")

    /**
     * collect操作符，最基本的末端操作符，收集flow发出的元素
     */
    (1..3).asFlow().collect {
        println("collected $it")
    }

    /**
     * toCollection操作符，把flow输出到任意集合中，如list、set等
     */
    val list = ArrayList<Int>()
    flow {
        (1..3).forEach {
            emit(it)
        }
    }.toCollection(list)
    println("collected collection $list")

    /**
     * first操作符，返回flow发出的第一次元素，然后取消flow的执行
     */
    val first = (1..3).asFlow().first()
    println("collected first $first")

    /**
     * single操作符，返回flow发出的第一次元素，如果flow发出超过一个就抛出IllegalArgumentException异常
     */
    try {
        val single = (1..3).asFlow().single()
        println("collected single $single")
    }catch (e: IllegalArgumentException) {
        println(e.message)
    }

    /**
     * reduce操作符，从flow发出的第一个元素开始累积，返回累积后的值
     */
    val reduce = (1..3).asFlow().reduce { a, b ->
        return@reduce a + b
    }
    println("collected reduce $reduce")

    /**
     * fold操作符，从传入的初始值initial开始累积flow发出的元素，返回累积后的值
     */
    val fold = (1..3).asFlow().fold(1) { a, b ->
        return@fold a + b
    }
    println("collected fold $fold")

    /**
     * launchIn操作符，在独立协程中启动flow的收集
     */
    (1..3).asFlow().onEach {
        println("collected $it")
    }.launchIn(CoroutineScope(Dispatchers.Default))

    println("Done")
}

/**
 * flow的异常处理
 */
suspend fun flowExceptionHandleExample() {
    println("flowExceptionHandleExample")

    /**
     * 通过try catch语句捕获flow中的所有异常，包括builder块、中间操作符、末端操作符中的异常
     */
    try {
        exceptionTask().collect {
            println("collected $it")
        }
    }catch (e: Exception) {
        println(e)
    }

    /**
     * 使用catch操作符捕获flow上游抛出的所有异常，它是一个FlowCollector，所以catch后可以把错误信息发送到flow下游
     */
    exceptionTask().catch {
        println("catch $it")
        emit(-1)
    }.collect{
        println("collected $it")
    }

    /**
     * 以声明式的方式捕捉所有异常，由于catch操作符只能捕获它上游的异常，所以如果collect中抛出异常catch是不会捕获到，可以使用以下declarative方式，
     * collect方法中的逻辑放在onEach方法中，collect方法的调用只作为触发flow收集，这样就可以把flow中发生的异常都统一在catch块中处理
     */
    exceptionTask().onEach {
        check(it == 0)
        println("collected $it")
    }.catch {
        println("catch $it")
    }.collect()

    println("Done")
}

/**
 * flow的完成处理，当flow出现异常或flow正常结束时处理一些统一逻辑
 */
suspend fun flowCompleteHandleExample() {
    println("flowCompleteHandleExample")

    /**
     * 通过try catch finally语句
     */
    try {
        exceptionTask().collect {
            println("collected $it")
        }
    }catch (e: Exception) {
        println(e)
    }finally {
        println("finally")
    }

    /**
     * 使用onCompletion操作符，在收集完成或异常结束时都会回调该方法并接收一个可空的throwable，当throwable为空时表示正常结束，
     * 当throwable不为空时，表示异常结束，当异常结束时它不会catch住异常，会继续抛出异常给下游
     */
    normalTask().onCompletion {
        println("finally with $it")
    }.collect {
        println("collected $it")
    }

    /**
     * onCompletion配合catch、onEach操作符以声明式地方式(declarative)实现try catch finally的功能
     */
    exceptionTask().onCompletion {
        println("finally with $it")
    }.onEach {
        println("collected $it")
    }.catch {
        println("catch $it")
    }.collect()

    println("Done")
}

suspend fun costTask(param: String? = null) = flow {
    for(i in 1..3) {
        delay(100)
        val value = if(param.isNullOrEmpty()) {
            i
        }else {
            "$i $param"
        }
        println("emit $value")
        emit(value)
    }
}

suspend fun normalTask() = flow {
    for(i in 1..3) {
        println("emit $i")
        emit(i)
    }
}

suspend fun exceptionTask() = flow {
    for(i in 1..3) {
        println("emit $i")
        emit(i)
        check(i <= 1) {
            "Crashed on $i"
        }
    }
}