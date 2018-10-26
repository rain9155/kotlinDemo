package com.example.hy

/**
 * Kotlin空值处理运算符：
 * a?    可空变量类型
 * a!!   非空断言（关闭空检查）,不建议使用
 * ?.    空安全调用符
 * ?:    Elvis操作符
 */
fun main(args: Array<String>) {
    /** 可空变量类型,变量可以赋值为空 */
    //val str : String = null ,报错，不可直接赋值为空
    val str : String? = null
    println(str)//输出null

    /** 非空断言,告诉编译器，关闭空检查 */
    //val str2 : String = null 报错,
    //val str2 : String = null!!
    //println(str2)

    /** 空安全调用符, 当对象不为空时才调用方法，为空时返回null*/
    val int : Int? = str?.toInt()
    println(int)
    //相当于java的 if（str != null） return str.toInt; else return null; 语句*/

    /** Elvis操作符, 当对象不为空时才调用方法，为空时返回默认值*/
    val int2 : Int = str?.toInt()?:-1
    println(int2)
    //相当于java的 if（str != null） return str.toInt; else return -1; 语句
}