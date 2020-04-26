package com.fengwenyi.study_java.chapter2;

/**
 * @author Erwin Feng
 * @since 2019/10/18 00:51
 */
public class Father {

    private String name;

    public Father() {
        System.out.println("Father 无参构造方法");
    }

    public Father(String name) {
        this.name = name;
        System.out.println("Father 有参构造方法，参数值为：" + this.name);
    }

    static {
        System.out.println("Father 静态代码块");
    }

    {
        System.out.println("Father 非静态代码块");
    }

    public void speak() {
        System.out.println("Father 方法");
    }

    public static void main(String[] args) {
        Father father = new Father();
        father.speak();

        /*
        Father 静态代码块
        Father 非静态代码块
        Father 无参构造方法
        Father 方法
         */

    }

}
