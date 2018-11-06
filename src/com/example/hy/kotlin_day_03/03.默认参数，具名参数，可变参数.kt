package com.example.hy.kotlin_day_03

/**
 * kotlin中的默认参数，具名参数和可变参数
 */
fun main(args: Array<String>) {
    /** 默认参数，定义函数变量时可以指定默认值 */
    //需求：发送网络请求，默认请求方式为GET
    println(sendRequest("www.baidu.com"))//输出：请求地址：www.baidu.com, 请求方法：GET
    println(sendRequest("www.baidu.com", "POST"))//输出：请求地址：www.baidu.com, 请求方法：POST

    /** 具名参数，调用函数时可以指定参数（没有顺序） */
    //需求：调用发送网络请求的函数
    println(sendRequest("www.baidu.com", method = "pull"))//输出:请求地址：www.baidu.com, 请求方法：pull
    //sendRequest(method = "post", ""), 报错
    println(sendRequest(method = "post", path = "www.baidu.com"))//输出：请求地址：www.baidu.com, 请求方法：post

    /** 可变参数，底层是数组 */
    //需求；创建一个求和函数，无论传多少个数都能求和
    println(add(1, 2, 3, 4, 5, 6))//输出：21
}

/**
 * 发送网络请求，默认请求方式为GET
 */
fun sendRequest(path : String, method : String = "GET") : String = "请求地址：$path, 请求方法：$method"

/**
 * 求和函数
 */
fun add(vararg a : Int) : Int{
    var result = 0;
    a.forEach {
        result += it
    }
    return result
}