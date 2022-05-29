package com.example.kotlindemo.kotlin_day_06

import kotlinx.coroutines.*
import java.util.IllegalFormatException
import java.util.concurrent.CopyOnWriteArraySet
import kotlin.coroutines.*
import kotlin.coroutines.intrinsics.*

fun main() {
    val simpleScope = SimpleCoroutineScope(Dispatchers.Default)
    val simpleJob = simpleScope.launch(CoroutineName("main"), CoroutineStart.DEFAULT) {
        val user = login()
        val userData = fetchData(user)
        displayUI(userData)
    }
    simpleJob.invokeOnCompletion(object : SimpleJob.CompletionHandler {
        override fun invoke(cause: Throwable?) {
            println("invokeOnCompletion: cause = $cause")
        }
    })
    Thread.sleep(1000)
}

private suspend fun SimpleCoroutineScope.login(): String {
    return async(CoroutineName("login")) {
        delay(200)
        return@async "user"
    }.await()
}

private suspend fun SimpleCoroutineScope.fetchData(user: String): String {
    return async(CoroutineName("fetch")) {
        delay(200)
        return@async "$user data"
    }.await()
}

private fun displayUI(data: String) {
    println("displayUI: $data")
}


/**
 * 简化版的[CoroutineScope]，提供协程运行作用域，它与[CoroutineScope]的区别是没有[CoroutineScope.cancel]、[CoroutineScope.ensureActive]等这些扩展方法
 */
private interface SimpleCoroutineScope {

    val coroutineContext: CoroutineContext
}

/**
 * [SimpleCoroutineScope]的实现
 */
private class SimpleCoroutineScopeImpl(override val coroutineContext: CoroutineContext) : SimpleCoroutineScope

/**
 * 构造[SimpleCoroutineScope]实例
 */
private fun SimpleCoroutineScope(context: CoroutineContext) = SimpleCoroutineScopeImpl(if(context[SimpleJob] != null) context else context + SimpleJob())

/**
 * 简化版的[Job]，用于管理协程的生命周期，它与[Job]的区别是它没有取消操作、异常传播、异常处理等功能，只有简单的状态流转：
 *
 *      start/await
 * NEW -------------> ACTIVE (isActive = true)
 *   \                /
 *    \  fail/finish /
 *     \            /
 *       COMPLETING
 *           |
 *           | wait children
 *           v
 *       COMPLETE (isComplete = true)
 */
private interface SimpleJob : CoroutineContext.Element {

    companion object Key : CoroutineContext.Key<SimpleJob>

    /**
     * 协程是否已启动
     */
    fun isActive(): Boolean

    /**
     * 协程是否已完成
     */
    fun isComplete(): Boolean

    /**
     * 启动协程
     */
    fun start()

    /**
     * 等待协程的结果返回
     */
    suspend fun <T> await(): T

    /**
     * 注册协程完成回调[completionHandler]，返回的[DisposableHandle]可以用来反注册回调
     */
    fun invokeOnCompletion(completionHandler: CompletionHandler, invokeImmediately: Boolean = true): DisposableHandle

    /**
     * 建立起与[childJob]子协程的父子关系
     */
    fun attachChild(childJob: SimpleJob)

    /**
     * 协程完成通知回调
     */
    interface CompletionHandler {

        /**
         * cause == null -> 成功结束
         * cause == other -> 异常结束
         */
        fun invoke(cause: Throwable?)
    }

    /**
     * 反注册句柄
     */
    interface DisposableHandle {

        /**
         * 调用[dispose]方法反注册
         */
        fun dispose()
    }
}

/**
 * [SimpleJob]的实现
 */
private open class SimpleJobImpl(active: Boolean) : SimpleJob {

    enum class State {
        NEW, ACTIVE, COMPLETING, COMPLETED
    }

    override val key: CoroutineContext.Key<*> get() = SimpleJob

    @Volatile
    private var state = if(active) State.ACTIVE else State.NEW
    @Volatile
    private var result: Any? = null
    private val children = CopyOnWriteArraySet<SimpleJob>()
    private val completionHandlers = CopyOnWriteArraySet<SimpleJob.CompletionHandler>()

    override fun isActive(): Boolean {
        return state == State.ACTIVE
    }

    override fun isComplete(): Boolean {
        return state == State.COMPLETED
    }

    override fun start() {
        if(state == State.NEW) {
            state = State.ACTIVE
            onStart()
        }
    }

    override suspend fun <T> await(): T {
        if(state == State.COMPLETED) {
            if(result is Throwable) {
                throw result as Throwable
            }else {
                return result as T
            }
        }
        if(state == State.NEW) {
            start()
        }
        return suspendCoroutineUninterceptedOrReturn {
            invokeOnCompletion(object : SimpleJob.CompletionHandler {
                override fun invoke(cause: Throwable?) {
                     if(cause != null) {
                         it.resumeWithException(cause)
                     }else {
                         it.resume(result as T)
                     }
                }
            })
            COROUTINE_SUSPENDED
        }
    }

    override fun invokeOnCompletion(completionHandler: SimpleJob.CompletionHandler, invokeImmediately: Boolean): SimpleJob.DisposableHandle {
        if(invokeImmediately && state == State.COMPLETED) {
            completionHandler.invoke(result as? Throwable)
        }
        completionHandlers.add(completionHandler)
        return CompletionHandlerDisposeHandle(completionHandler)
    }

    override fun attachChild(childJob: SimpleJob) {
        children.add(childJob)
    }

    protected fun initParentJob(parentJob: SimpleJob?) {
        parentJob?.start()
        parentJob?.attachChild(this)
    }

    protected fun tryMakeCompleted(value: Any?): Boolean {
        result = value ?: result
        val complete = children.find { !it.isComplete() } == null
        if(complete) {
            if(state == State.COMPLETED) {
                return true
            }
            state = State.COMPLETED
            val cause = if(result is Throwable) { result as Throwable } else { null }
            notifyCompleteHandlers(cause)
        }else {// 等待所有child完成
            if(state == State.COMPLETING) {
                return false
            }
            state = State.COMPLETING
            children.forEach {
                it.invokeOnCompletion(object : SimpleJob.CompletionHandler {
                    override fun invoke(cause: Throwable?) {
                        tryMakeCompleted(cause)
                    }
                }, invokeImmediately = true)
            }
        }
        return complete
    }

    private fun notifyCompleteHandlers(cause: Throwable?) {
        completionHandlers.forEach {
            it.invoke(cause)
        }
    }

    /**
     * 协程调用start/await方法从[State.NEW]转移到[State.ACTIVE]
     */
    protected open fun onStart() {}

    /**
     * 调用[dispose]方法解除注册的[completionHandler]
     */
    inner class CompletionHandlerDisposeHandle(private val completionHandler: SimpleJob.CompletionHandler) : SimpleJob.DisposableHandle {

        override fun dispose() {
            completionHandlers.remove(completionHandler)
        }
    }
}

/**
 * 构造[SimpleJob]实例
 */
private fun SimpleJob() = SimpleJobImpl(active = true)

/**
 * 简化版的协程，调用start方法启动协程
 * @param parentContext 协程的父Context，用于建立父子关系
 * @param active 为true时让协程处于active状态，否则处于new状态，处于new状态需要调用start/await方法才会启动协程
 */
private open class SimpleCoroutine<T>(private val parentContext: CoroutineContext, active: Boolean = true) : SimpleJobImpl(active), SimpleCoroutineScope, Continuation<T> {

    override val context: CoroutineContext = parentContext + this

    override val coroutineContext: CoroutineContext get() = context

    /**
     * 协程完成通知，这里处理结果，进行生命周期状态流转
     */
    override fun resumeWith(result: Result<T>) {
        println("resumeWith: result = $result, coroutineName = ${coroutineContext[CoroutineName]}")
        if(result.isSuccess) {//成功恢复
            tryMakeCompleted(result.getOrNull())
        }else {//错误恢复
            tryMakeCompleted(result.exceptionOrNull())
        }
    }

    /**
     * for [CoroutineStart.LAZY]
     */
    private var lazyContinuation: Continuation<Unit>? = null

    /**
     * for [CoroutineStart.LAZY]
     */
    override fun onStart() {
        lazyContinuation?.intercepted()?.resumeWith(Result.success(Unit))
    }

    /**
     * 建立协程的父子关系，使用[kotlin.coroutines.intrinsics]原语为[block]块创建协程的初始化[Continuation], 并根据[start]模式启动它
     */
    fun start(start: CoroutineStart, block: suspend SimpleCoroutineScope.() -> T) {
        if(coroutineContext[CoroutineExceptionHandler] != null) {
            throw IllegalAccessException("unsupport CoroutineExceptionHandler")
        }
        initParentJob(parentContext[SimpleJob])
        when(start) {
            /**
             * 立即启动协程，并把启动的协程运行在指定的Dispatcher上
             */
            CoroutineStart.DEFAULT -> {
                block.createCoroutineUnintercepted(this, this).intercepted().resumeWith(Result.success(Unit))
            }
            /**
             * 在当前线程立即启动协程, 但恢复时会把协程运行在指定的Dispatcher上，效果和指定[Dispatchers.Unconfined]类似
             */
            CoroutineStart.UNDISPATCHED -> {
                val result = try {
                    block.startCoroutineUninterceptedOrReturn(this, this)
                }catch (e: Throwable) {
                    e
                }
                if(result is Throwable) {
                    this.resumeWithException(result)
                }else if(result !== COROUTINE_SUSPENDED) {
                    this.resume(result as T)
                }else {
                    //COROUTINE_SUSPENDED, do noting
                }
            }
            /**
             * 不立即启动协程，当调用start/await方法时才启动协程，并把启动的协程运行在指定的Dispatcher上
             */
            CoroutineStart.LAZY -> {
                lazyContinuation = block.createCoroutineUnintercepted(this, this)
            }
            else -> {
                throw IllegalAccessException("unsupport $start")
            }
        }
    }
}

/**
 * 启动协程，没有结果返回
 */
private fun SimpleCoroutineScope.launch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend SimpleCoroutineScope.() -> Unit
): SimpleJob {
    val newContext = coroutineContext + context
    val coroutine = if(start == CoroutineStart.LAZY) {
        SimpleCoroutine<Unit>(newContext, active = false)
    }else {
        SimpleCoroutine<Unit>(newContext, active = true)
    }
    coroutine.start(start, block)
    return coroutine
}

/**
 * 启动协程，可以调用返回的SimpleJob的await方法等待结果
 */
private fun <T> SimpleCoroutineScope.async(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend SimpleCoroutineScope.() -> T
): SimpleJob {
    val newContext = coroutineContext + context
    val coroutine = if(start == CoroutineStart.LAZY) {
        SimpleCoroutine<T>(newContext, active = false)
    }else {
        SimpleCoroutine<T>(newContext, active = true)
    }
    coroutine.start(start, block)
    return coroutine
}










