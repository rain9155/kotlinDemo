package com.example.hy.kotlin_day_03

/**
 * kotlin的嵌套类和内部类
 * 1、嵌套类
 * 直接在一个类中嵌套另外一个类，相当于java中加了static的类，
 * 独立于外部类，不能直接使用外部类的字段和方法
 * 2、内部类
 * 加inner关键字，相当于java中没有加static的类，
 * 可以直接使用外部类的字段和方法,也可以通过（this@外部类）来引用外部类的字段和方法
 */
fun main(args: Array<String>) {

    val class1 = Class.NestedClass()
    class1.doSomething()

    val class2 = Class2().InnerClass()
    class2.doShomething()

}


class Class{

    var name = "rain"

    fun work(){
        println("外部类")
        println(name)
        println(this.name)
    }

    /**
     * 嵌套类,相当于java中加了static的类，独立于外部类，不能直接使用外部类的字段和方法
     */
    class NestedClass{

        var name = "jianyu"

        fun doSomething(){
            println(name)
            println(this.name)
        }

    }

}

class Class2{

    var name = "rain"

    fun work(){
        println("外部类")
        println(name)
        println(this.name)
    }


    /**
     * 内部类，相当于java中没有加static的类，可以直接使用外部类的字段和方法,也可以通过（this@外部类）来引用外部类的字段和方法
     */
    inner class InnerClass{

        var name = "jianyu"

        fun doShomething(){
            work()
            println(this@Class2.name)
            this@Class2.work()
            println(name)
            println(this.name)

        }
    }

}