package com.example.kotlindemo

import kotlinx.coroutines.*

fun main(){
    println("Hello world!")
    val scope = CoroutineScope(Dispatchers.Default)
    scope.launch {
        async {
            delay(200)
            println("async1")
        }.await()

        async {
            delay(100)
            println("async2")
        }.await()
    }.invokeOnCompletion {
        println("invokeOnCompletion: $it")
    }

    Thread.sleep(1000)
}


