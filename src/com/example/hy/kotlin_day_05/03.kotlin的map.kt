package com.example.hy.kotlin_day_05

/**
 * kotlin的map集合
 */
fun main(args: Array<String>) {

    /** map的创建 */

    /** 1、通过mapOf<K, T>（..to..) 创建, 只读 */
    val map1 = mapOf<String, String>("中国" to "china", "英国" to "England")

    /** 2、通过mutableMapOf<K, T>(.. to ..) */
    val map2 = mutableMapOf<String, String>("中国" to "china", "英国" to "England")

    /** 3、直接创建 */
    val map3 = HashMap<String, String>()
    map3.put("中国", "china")
    map3.put("英国", "England")

    /** map的遍历 */

    /** 1、遍历所有的key */
    val keyset = map3.keys
    keyset.forEach {
        println(it)
    }

    /** 2、遍历所有的value */
    val values = map3.values
    values.forEach {
        println(it)
    }

    /** 3、遍历所有的key和value */
    map3.forEach { t, u ->
        println("key = $t, value = $u")
    }

    for ((key, value) in map3){
        println("key = $key, value = $value")
    }
}