package com.example.hy.kotlin_day_03

/**
 * 泛型的上限、擦除、类型投射
 * 1、泛型的上限
 * <T ：对象>限制存放的类型
 * 2、泛型擦除
 * 表示泛型在编译期间类型会被擦除
 * 在java中必须要通过反射在运行期间获取泛型类型
 * 在kotlin中解决泛型擦除的方案是,
 * 1）泛型前加reified
 * 2）方法前加inline
 * 3、泛型的类型投射
 * out：接受当前类型或它的子类，相当于java中的 ？extens 对象
 * in：接受当前类型或它的父类，相当于java中的 ？super 对象
 */
fun main(args: Array<String>) {

    /** 泛型上限 */

    //val other = FruitBox2<Other>(Banana("香蕉")) 报错
    val fruitBox2 = FruitBox2<Banana>(Banana("香蕉"))

    /** 泛型擦除 */

    val box1 = Box<String>("")
    val box2 = Box<Int>(10)
    val clz1 = box1.javaClass
    val clz2 = box2.javaClass
    println(clz1 == clz2)//输出true，class类型相同，因为<String>和<Int>被擦除了

    //获取类型
    praseType2("")//输出java.lang.String
    praseType2(10)//输出java.lang.Integer

    /** 泛型投射 */

    val list = ArrayList<Banana>()
    setFruitList(list)
    val list2 = ArrayList<Thing>()
    setFruitList2(list2)

}



/**
 * 水果箱子，限制传入的类型型只能为Fruit的子类
 */
class FruitBox2<T : Fruit>(some : T) : Box<T>(some)

/**
 * 其他类型
 */
class Other

/**
 * 获取type的类型
 */
inline fun <reified T> praseType2(type : T){
  val name = T::class.java.name
    println(name)
}

/**
 * 设置水果集合，只能接收Fruit或它的子类的集合
 */
fun setFruitList(list : ArrayList<out Fruit>){
    println(list.size)
}

/**
 * 设置水果集合，只能接收Fruit或它的父类的集合
 */
fun setFruitList2(list : ArrayList<in Fruit>){
    println(list.size)
}