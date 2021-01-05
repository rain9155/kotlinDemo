package com.example.kotlindemo.kotlin_day_02

/**
 * 标签处返回（break@tag + tag@），用于跳出多重循环
 */
fun main(args: Array<String>) {

    //需求：
    //1、输出num和str所有的组合
    //2、遇到2b时，停止输出
    val str1 = "123"
    val str2 = "abc"
    tag@ for(n in str1){
        for(a in str2){
            //if(n == '2' && a == 'b') break 不可行，只能跳出一个循环
            if(n == '2' && a == 'b') break@tag //可行，跳出了两个循环，tag名字任取
            println("$n $a")
        }
    }

}