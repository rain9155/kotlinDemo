package com.example.kotlindemo.kotlin_day_04

/**
 * kotlin的类委托
 * 1、类委托
 * 把类的某些方法通过代理模式委托给其他类实现
 * 类委托实现方式一：类后加关键字by接委托类
 * 类委托实现方式二：主构函数加by接接口实现类
 * 2、类委托加强
 * 要委托的类委托其他类的同时，可以加入自己的逻辑实现
 */
fun main(args: Array<String>) {

    //需求：大头爸爸委托小头儿子洗碗
    val father = BigHeadFather()
    father.wash()//输出：小头儿子洗碗

    //需求2：大头爸爸委托小头儿子和大头儿子洗碗
    val father2 = BigHeadFather2(BigHeadSon())
    val father3 = BigHeadFather2(SmallHeadSon())
    father2.wash()//输出：大头儿子洗碗
    father3.wash()//输出：小头儿子洗碗

    //需求3：大头爸爸在委托儿子洗碗前，奖励儿子一块，洗完后，表扬儿子一下
    val father4 = BigHeadFather3(BigHeadSon())
    father4.wash()
    //输出：
    // 奖励一块钱
    //大头儿子洗碗
    //真棒！以后继续

}

/**
 * 表示洗碗能力
 */
interface WashPower{
    fun wash()
}

/**
 * 小头儿子
 */
class SmallHeadSon : WashPower{
    override fun wash() {
        println("小头儿子洗碗")
    }
}

/**
 * 大头儿子
 */
class BigHeadSon : WashPower{
    override fun wash() {
        println("大头儿子洗碗")
    }
}

/**
 * 大头爸爸，通过by委托小头儿子洗碗（类委托实现方式一）
 */
class BigHeadFather : WashPower by SmallHeadSon()

/**
 * 大头爸爸2，通过by委托儿子洗碗（类委托实现方式二）
 */
class BigHeadFather2(var washPower: WashPower) : WashPower by washPower

/**
 * 大头爸爸3, 在委托儿子洗碗前，奖励儿子一块，洗完后，表扬儿子一下 (类委托加强)
 */
class BigHeadFather3(var washPower: WashPower): WashPower by washPower{

    override fun wash() {
        println("奖励一块钱")
        washPower.wash()//儿子洗碗
        println("真棒！以后继续")
    }

}

