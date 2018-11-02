package com.example.hy.kotlin_day_02

/**
 * kotlin数组的遍历，修改和查找
 */
fun main(args: Array<String>) {

    /** 数组的遍历 */
    val array = arrayOf("rain", "小明", "张三", "李四")
    //for循环遍历
    for(a in array){
        println(a)
    }
    //foreach遍历
    array.forEach {
        println(it)
    }
    println()
    /** 数组的修改 */
    //需求1：修改上面数组第一个元素的值为"李华"
    array[0] = "李华"
    println(array[0])
    //需求2：修改上面数组第二个元素的值为"张强"
    array.set(1, "张强")
    println(array.get(1))
    println()
    /** 数组角标的查找 */
    //需求1：查找第一个“张三”的角标
    val index = array.indexOf("张三")
    println(index)
    //需求2：查找最后一个“张三”的角标
    val index2 = array.lastIndexOf("张三")
    println(index2)
    //需求3：查找第一个姓“张”的角标
    val index3 = array.indexOfFirst {
        it.startsWith("张")
    }
    println(index3)
    //需求4：查找最后一个姓“张”的角标
    val index4 = array.indexOfLast {
        it.startsWith("张")
    }
    println(index4)
}