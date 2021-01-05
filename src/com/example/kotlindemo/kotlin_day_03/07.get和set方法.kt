package com.example.kotlindemo.kotlin_day_03

/**
 * 对象字段的get和set方法：
 * 1、kotlin对象的字段默认都是私有的，
 * 定义字段时会默认生成get和set方法，
 * 访问字段时都是通过默认生成的get和set方法访问字段
 * 2、修改kotlin对象的字段默认生成的get和set方法时，
 * 不能通过直接访问字段，如（this.name）,这样会递归调用它自己，造成内存溢出
 * 正确方法是通过默认定义好的field字段访问
 */
fun main(args: Array<String>) {

    val person = Person()

    //需求1：访问Person字段
    println(person.age)
    println(person.name)

    //需求2：修改Person字段的访问性，使name只能从外部访问而不能从外部修改
    //tips:使set方法私有
    println(person.name)
    //person.name = "jianyu" 报错，不能修改

    //需求3：修改Person字段的set和get方法，使之带有一定条件的访问
    person.age = 30
    println(person.age)
}

class Person{

    var name = "rain"
    private set//使set方法私有

    var age = 18
    set(value) {
        //this.age = value 不能这样访问，会StackOverFlowError
        if(value < 0)
            field = 0
        else
            field = value
    }
    get(){
        //return  this.age 不能这样访问，会StackOverFlowError
        if(field < 0)
            return -1
     return field
    }
}