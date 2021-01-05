package com.example.kotlindemo.kotlin_day_04

/**
 * kotlin的数据类
 * 1、特点：
 * 只保存数据，没有其他任何逻辑操作，对应java的bean类
 * 主要解决java中定义bean类时产生大量重复代码
 * 2、定义：
 * class前加data关键字
 */
fun main(args: Array<String>) {

    //用法1：
    val news = News("rain", "内容", "2018/12/21")
    println(news.title)
    println(news.content)
    println(news.time)

    //用法2：
    println(news.component1())
    println(news.component2())
    println(news.component3())

    //用法3:
    val (title, content, time) = News("rain", "内容", "2018/12/21")
    println(title)
    println(content)
    println(time)

    //以上全部输出：
    //rain
    //内容
    //2018/12/21

}

/**
 * 加data相当于在类中自动生成了：
 * 1、构造函数，并在构造函数里给字段赋值
 * 2、字段的get，set方法
 * 3、字段对应的component方法
 * 4、类的copy（）方法
 * 5、toString（）方法
 * 6、hashCode()方法
 * 7、equals()方法
 */
data class News(var title : String, var content : String, var time : String)