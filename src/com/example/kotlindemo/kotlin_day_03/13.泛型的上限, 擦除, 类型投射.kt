package com.example.kotlindemo.kotlin_day_03

/**
 * 泛型的上限、擦除、类型投射：
 * 1、泛型的上限
 * <T ：对象>限制T的单个上限类型，通过where关键字可以设置多个上限，类似与java中的&关键字，是一种与关系
 * 2、泛型擦除
 * 表示泛型在编译期间类型会被擦除，在java中必须要通过反射在运行期间获取泛型类型，
 * 在kotlin中解决泛型擦除的方案是,泛型前加reified，如果是方法，方法前还要加inline
 * 3、泛型的类型投射
 * <out T>：接受当前类型或它的子类，相当于java中的 ？extend 对象，生产者(Producer), 又称协变
 * <in T>：接受当前类型或它的父类，相当于java中的 ？super 对象，消费者(Consumer),又称逆变
 */
fun main(args: Array<String>) {

    /** 泛型上限 */

    //val other = FruitBox2<Other>(Banana("香蕉")) 报错
    val fruitBox2 = FruitBox2<Banana2>(Banana2("香蕉"))
    println(fruitBox2.something)

    /** 泛型擦除 */

    val box1 = Box<String>("")
    val box2 = Box<Int>(10)
    val clz1 = box1.javaClass
    val clz2 = box2.javaClass
    println(clz1 == clz2)//输出true，class类型相同，因为<String>和<Int>被擦除了

    //解决泛型擦除，通过添加reified关键字，可以直接获取泛型类型
    praseType2("")//输出java.lang.String
    praseType2(10)//输出java.lang.Integer

    /** 泛型的类型投射 */

    val list = ArrayList<Banana>()
    setFruitListOut(list)
    val list2 = ArrayList<Any>()
    setFruitListIn(list2)

}

/**
 * 水果箱子，限制传入的类型只能为Fruit的子类，并且实现了Other接口
 */
class FruitBox2<T>(some : T) : Box<T>(some) where T : Fruit, T : Other

/**
 * Other接口
 */
interface Other

/**
 * 继承自Fruit，并且实现了Other的香蕉
 */
class Banana2(override var name: String) : Fruit(), Other

/**
 * 获取type的类型
 */
inline fun <reified T> praseType2(type : T){
  val name = T::class.java.name
    println(name)
}

/**
 * 设置水果集合，只能接收类型是Fruit或它的子类的集合
 */
fun setFruitListOut(list : ArrayList<out Fruit>){
    println(list.size)
}

/**
 * 设置水果集合，只能接收类型是Fruit或它的父类的集合
 */
fun setFruitListIn(list : ArrayList<in Fruit>){
    println(list.size)
}