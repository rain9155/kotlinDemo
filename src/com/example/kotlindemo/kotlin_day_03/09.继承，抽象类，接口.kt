package com.example.kotlindemo.kotlin_day_03

/**
 * kotlin的继承，抽象类，接口
 * 1、继承：
 * 继承普通类、抽象类或实现接口，都是使用 ：
 * 2、普通类：
 * kotlin的普通类默认不能被继承（final），要加open关键字才能被继承
 * 普通类中的字段、方法默认不能被重写（final），要加open关键字才能被重写（override）
 * 3、抽象类：（表示事物的本质）
 * 抽象类前加abstract关键字，若里面有抽象字段或方法，子类要实现它们或自己再次声明为abstract
 * 4、接口：（表示事物的行为）
 * 接口前加interface关键字
 * 接口中的字段不可以默认实现，要由实现类实现
 * 接口中的方法可以在接口中默认实现，若在接口中默认实现，则实现类无需重写方法，否则要重写
 */
fun main(args: Array<String>) {

    val human = Human()
    println(human.name)
    human.work()

    val xiaoming = XiaoMing()
    println(xiaoming.name)
    xiaoming.work()

    val dog = Dog("小狗")
    println(dog.name)
    dog.work()

    val jianyu = JianYu()
    println(jianyu.name)
    println(jianyu.number)
    jianyu.work()
    jianyu.walk()
    jianyu.dirve()

}

/**
 * 普通类
 */
open class Human{

    open var name = "人类"

    open fun work(){
        println("吃饭")
    }
}

/**
 * 继承Human普通类
 */
class XiaoMing : Human(){

    override var name = "XiaoMing"

    override fun work() {
        println("吃饭，睡觉")
    }
}

/**
 * 抽象类
 */
abstract class Animal{

    abstract var name : String

    abstract fun work()
}

/**
 * 继承抽象类
 */
class Dog(override var name: String) : Animal() {

    override fun work() {
        println("吃饭，睡觉")
    }

}

/**
 * 接口1, 表示走路行为
 */
interface Walk{

    fun walk()

}

/**
 * 接口2，表示驾车行为,接口中的方法可以默认实现
 */
interface Dirve{

    var number : String//驾驶证号
    fun dirve(){
        println("我会开车啦！")
    }

}

/**
 * 实现接口
 */
class JianYu : Human(), Walk, Dirve{

    override var number = "123456"

    override fun walk() {
        println("我学会走路")
    }

    override fun dirve() {
        super.dirve()
    }

    override var name: String = "jianyu"

}

