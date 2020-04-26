package com.fengwenyi.study_java.chapter2;

/**
 * @author Erwin Feng
 * @since 2019/10/18 00:57
 */
public class Son extends Father {

    private String name;

    public Son() {
        System.out.println("Son 无参构造方法");
    }

    public Son(String name) {
        this.name = name;
        System.out.println("Son 有参构造方法，参数：" + this.name);
    }

    static {
        System.out.println("Son 静态代码块");
    }

    {
        System.out.println("Son 非静态代码块");
    }

    public void speak() {
        System.out.println("Son 方法");
    }

    public static void main(String[] args) {
        Son son = new Son();
        son.speak();

        /*
        Father 静态代码块
        Son 静态代码块
        Father 非静态代码块
        Father 无参构造方法
        Son 非静态代码块
        Son 无参构造方法
        Son 方法
         */

        Father father = new Father();
        father.speak();

        /*
        Father 静态代码块
        Son 静态代码块
        Father 非静态代码块
        Father 无参构造方法
        Son 非静态代码块
        Son 无参构造方法
        Son 方法
        Father 非静态代码块
        Father 无参构造方法
        Father 方法
         */


    }

}
