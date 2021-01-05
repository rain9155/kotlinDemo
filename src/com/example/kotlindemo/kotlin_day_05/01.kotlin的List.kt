package com.example.kotlindemo.kotlin_day_05

/**
 * kotlin的List集合
 */
fun main(args: Array<String>) {

    /** 1、通过listOf<>(...)创建,只读集合，只能查不能增删改 */
    val list1 = listOf<String>("rain", "jianyu", "hairong")
    list1.forEach {
        println(it)
    }
    //list1[0] = "jian" 不能修改元素
    //list1.add() 不能添加元素

    /** 2、通过mutableListOf<String>(...)创建, 可以增删改查 */
    val list2 = mutableListOf<String>("rain")
    for (s in list2) {
        println(s)
    }
    list2.add(0, "aa")
    list2.set(0, "aaa")
    list2[0] = "jfkd"
    list2.removeAt(list2.size - 1)
    list2.forEach {
        println(it)
    }

    /** 4、通过arrayListOf<>(...)创建，可以增删改查 */
    val list3 = arrayListOf<String>("rain1", "rain2", "rain3")
    list3.forEach {
        println(it)
    }

    /** 5、直接创建，可以增删改查 */
    val list4 = ArrayList<String>()
    list4.add("jianyuyu")
    list4.add("jdfkdjf")
    list4.forEach {
        println(it)
    }
}