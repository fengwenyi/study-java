package com.fengwenyi.study_java_base;

/**
 * 还没搞懂
 * @author Erwin Feng
 * @since 2020/4/28 3:48 下午
 */
public class StudyEquals {

    public static void main(String[] args) {
        String s1 = new String("abc");
        String s2 = new String("abc");
        System.out.println("s1 equals s2 : " + s1.equals(s2));
        String s3 = "abc";
        String s4 = "abc";
        System.out.println("s1 equals s2 : " + s3.equals(s4));

        User user1 = new User();
        user1.id = 1;
        user1.name = "abc";
        User user2 = new User();
        user2.id = 1;
        user2.name = "abc";
        System.out.println(user1.equals(user2));
        System.out.println(user1 == user2);

        User user = new User();
        User user3 = user;
        User user4 = user;
        System.out.println(user3.equals(user4));
        System.out.println(user3 == user4);
    }

    static class User {
        int id;
        String name;
    }

}
