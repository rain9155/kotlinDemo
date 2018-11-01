package com.example.hy.kotlin_day_02

/**
 * kotlin数组的创建：
 * 8种基本数据类型都能通过arrayOf创建或自己类型的Array创建
 * ps：String例外，只能通过arrayOf创建，不能通过自己类型的Array创建
 * ps: Any相当于java中的Object对象（可以代表任何类型）
 */
fun main(args: Array<String>) {
    /** 通过arrayOf创建数组并赋值 */
    //需求1：定义一个数组保存张三，李四，小明的名字
    val array = arrayOf("张三", "李四", "小明")
    //需求2：定义一个数组保存10， 20， 30三个年龄
    val array2 = arrayOf(10, 20, 30)
    //需求3：定义一个数组保存‘a’，‘b’，‘c’三个字符
    val array3 = arrayOf('a', 'b', 'c')
    //需求4：定义一个数组保存‘a’，10，李四三个数据
    val array6 = arrayOf('a', 10, "李四") //本质上这个数组每个元素都为Any类型 ps：Any相当于java中的Object对象（可以代表任何类型）
    //其他类型以此类推...

    /** 通过自己类型的Array创建但不赋值 */
    //需求1：定义一个容量为3的整形数组
    val array4 = IntArray(3)
    //需求2：定义一个容量为3的整形数组，所有数据都初始化为3
    val array5 = IntArray(3){
        3
    }
    //其他类型以此类推...
    //CharArray()
    //LongArray()
    //ShortArray()
    //FloatArray()
    //DoubleArray()
    //BooleanArray()
    //StringArray() kotlin中不存在这种类型，是属于java中的，所以在kotlin中只能通过arrayOf创建String数组
}
