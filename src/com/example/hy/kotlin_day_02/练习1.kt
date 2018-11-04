package com.example.hy.kotlin_day_02

/**
 * 判断用户名输入是否合法
 */
fun main(args: Array<String>) {
    //需求：判断用户名输入是否合法
    //1、用户名只能有数字，小写字母，大写字母和下划线组成
    //2、必须包含三位以上数字
    //3、必须包含三位以上字母
    //4、长度不能小于6位
    val name = "123bfB_"
    println(islegal(name))
}

/**
 * 判断用户名输入是否合法
 */
fun islegal(name: String): Boolean{

    if(name.length < 6) return false

    var numConut = 0
    var lowCharCount = 0
    var upCharCount = 0

    name.forEach {
        when(it){
            in '0'..'9' -> numConut++
            in 'a'..'z' -> lowCharCount++
            in 'A'..'Z' -> upCharCount++
            '_' -> {}
            else -> return false
        }
    }

    if(numConut < 3 || (lowCharCount + upCharCount) < 3) return false

    return true
}
