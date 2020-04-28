package com.fengwenyi.study_java_base;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Erwin Feng
 * @since 2020/4/28 4:29 下午
 */
public class StudyMath {

    public static void main(String[] args) {
        System.out.println(Math.round(1.1));
        System.out.println(Math.round(1.4));  // 1
        System.out.println(Math.round(1.5));      // 2
        System.out.println(Math.round(1.6));          // 2
        System.out.println(Math.round(-1.6));             // -2
        System.out.println(Math.round(-1.5));                  // -1
        System.out.println(Math.round(-1.4));                       // -1

    }

    @Test
    public void round() {

        // round
        // 解释：
        /*
         以 long round(double a)为例，
         1、取最接近的值
         2、如果两个距离相等，去较大的值
         */

        // long round(double a)

        // int round(float a)

        Assert.assertTrue(1 == Math.round(1.4));   // 1 , 2     =>  1
        Assert.assertTrue(2 == Math.round(1.5));   // 1,2       =>  2>1  => 2
        Assert.assertTrue(2 == Math.round(1.6));   // 1,2       =>  2
        Assert.assertTrue(-2 == Math.round(-1.6)); // -1,-2     => -2
        Assert.assertTrue(-1 == Math.round(-1.5f)); // -1, -2   => -1
    }

    /*
    以参数为double 类型为例

    首先要注意的是它的返回值类型是long，如果 Math.round(11.5f)，那它的返回值类型就是int，这一点可以参考API
    其次 Returns the closest long to the argument, with ties rounding to positive infinity
    它返回的是一个最接近参数的long 值（例如：Math.round(11.6) = 12；Math.round(-11.6) = -12；Math.round(-0.1) = 0；Math.round(0.1) = 0），那如果出现向上向下距离一样的数值，比如题目中的11.5，该如何处理呢，别着急，看它的后半句话，with ties rounding to positive infinity（同时向正无穷方向取舍或者翻译成取较大的值，英语水平较差，只能翻译成这样了；
    例子:   Math.round(11.5) ，首先与 11.5最接近的有两个整数 11 和 12，取较大的那结果就是12；
               Math.round(-11.5)，首先与 -11.5最接近的有两个整数 -11 和 -12，取较大的那结果就是-11；
               Math.round(0.5)，首先与 0.5最接近的有两个整数 0 和 1，取较大的那结果就是1；
               Math.round(-0.5)，首先与 -0.5最接近的有两个整数 -1 和 0，取较大的那结果就是0；）
    然后它有三个特例：
    1.如果参数为 NaN（无穷与非数值），那么结果为 0。
    2.如果参数为负无穷大或任何小于等于 Long.MIN_VALUE 的值，那么结果等于Long.MIN_VALUE 的值。
    3.如果参数为正无穷大或任何大于等于 Long.MAX_VALUE 的值，那么结果等于Long.MAX_VALUE 的值。
     */

}
