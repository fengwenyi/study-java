package com.fengwenyi.study_java.chapter1;

import com.fengwenyi.study_java.chapter1.bean.UserBean;

import java.util.Optional;

/**
 * 学习Java 8 Optional
 * @author Erwin Feng
 * @since 2019-08-08 10:01
 */
public class StudyOptional {

    public static void main(String[] args) {
        /* 创建 Optional */
//        create();

        /* 获取值 */
//        getValueByMap();

        /* 异常 */
//        getValueException();

        /* orElse 与 orElseGet */
        orElse_orElseGet();
    }

    // 创建
    private static void create() {
        // create null
        Optional optional = Optional.empty();
        optional.isPresent();
        // 接收一个非空的值
        String str = "Hello World";
        Optional<String> notNullOpt = Optional.of(str);
        // 允许接收一个null的值
        String strNull = null;
        Optional<String> nullableOpt = Optional.ofNullable(strNull);
    }

    // 使用map取值
    private static void getValueByMap() {

        UserBean userBean = null;
        String username;

        // 不用optional的代码
        if (userBean != null)
            username = userBean.getUsername();

        // 用optional的代码
        Optional<UserBean> userBeanOpt = Optional.ofNullable(userBean);
        Optional<String> usernameOpt = userBeanOpt.map(UserBean::getUsername);
        username = usernameOpt.orElse(null);
    }

    // 取值异常
    private static void getValueException() {
        UserBean userBean = null;
        String username;
        Optional<UserBean> userBeanOpt = Optional.ofNullable(userBean);
        Optional<String> usernameOpt = userBeanOpt.map(UserBean::getUsername);
        username = usernameOpt.get();
        /*
        Exception in thread "main" java.util.NoSuchElementException: No value present
            at java.util.Optional.get(Optional.java:135)
        */
    }

    // orElse
    private static void getValueOrElse() {
        String str = "Hello World";
        Optional<String> strOpt = Optional.of(str);
        String orElseResult = strOpt.orElse(get("a"));
        String orElseGet = strOpt.orElseGet(() -> get("b"));
        String orElseThrow = strOpt.orElseThrow(
                () -> new IllegalArgumentException("Argument 'str' cannot be null or blank."));
    }

    /* orElse 与 orElseGet */
    /*
    相同点：
    orElse 与 orElseGet 当Optional的值是空时，会将 （orElse 与 orElseGet）赋给Optional

    不同的
    orElse：都会执行
    orElseGet：只有Optional的值是空时，才会执行
     */

    /*
    orElse()：如果有值则将其返回，否则返回指定的其它值。
    orElseGet()：如果有值则将其返回，否则调用函数并将其返回调用结果。
     */
    private static void orElse_orElseGet() {
        String s2 = "Hello";
        Optional<String> s2Opt = Optional.ofNullable(s2);
        String orElseStr = s2Opt.orElse(get("orElse"));
        String orElseGetStr = s2Opt.orElseGet(() -> get("orElseGet"));
        System.out.println("orElse value : " + orElseStr);
        System.out.println("orElseGet value : " + orElseGetStr);

        /*
        当 s2 = null  打印如下：
        orElse执行了方法
        orElseGet执行了方法
        orElse value : orElse
        orElseGet value : orElseGet
         */

        /*
        当 s2 = "Hello"时，打印如下：
        orElse执行了方法
        orElse value : Hello
        orElseGet value : Hello
         */
    }

    private static String get(String name) {
        System.out.println(name + "执行了方法");
        return name;
    }

    // 过滤器
    private static void filter() {
        Optional<String> optional = Optional.of("wangyi@163.com");
        optional = optional.filter(str -> str.contains("164"));
    }

}
