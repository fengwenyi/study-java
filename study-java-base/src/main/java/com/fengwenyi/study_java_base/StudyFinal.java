package com.fengwenyi.study_java_base;

/**
 * final关键字
 * @author Erwin Feng
 * @since 2020/4/28 4:12 下午
 */
public class StudyFinal {

    // 类
    // String;
    // public final class String
    //    implements java.io.Serializable, Comparable<String>, CharSequence

    // System;
    // public final class System {

    // Cannot inherit from final

    // 方法

    // 成员变量
    // 被final修饰的成员变量，不可不赋值，赋值有两种方式

}

final class Parent {
    // 该方法不能被继承
}

class Parent2 {
    private void hi() {
        // 该方法不能被重写
    }
}

class User {
    // 赋值方法1
    final int id = 1;

    // 赋值方法2
    final String name;
    public User() {
        this.name = "abc";
    }

}




