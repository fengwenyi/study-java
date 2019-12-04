package com.fengwenyi.study_java.chapter4;

import java.math.BigDecimal;

/**
 * 学习BigDecimal
 * @author Erwin Feng[xfsy_2015@163.com]
 * @since 2019/12/4 14:22
 */
public class StudyBigDecimal {

    public static void main(String[] args) {
        compare(new BigDecimal(1), new BigDecimal(2));
        compare(new BigDecimal(0.1), new BigDecimal(0.2));
        compare(new BigDecimal(-0.1), new BigDecimal(-0.2));

        compare(new BigDecimal(2), new BigDecimal(2));
        compare(new BigDecimal(0.2), new BigDecimal(0.2));
        compare(new BigDecimal(-0.2), new BigDecimal(-0.2));
    }

    // 加
    // 减
    // 乘 // 小数保留精度问题
    // 除

    // 比较大小
    private static void compare(BigDecimal a, BigDecimal b) {
        int c = a.compareTo(b);
        // 如果 c = 1, 则 a > b
        // 如果 c = 0, 则 a = b
        // 如果 c = -1, 则 a < b
        switch (c) {
            case 1: {
                System.out.println("a = " + a + ", b = " + b + ", a > b");
                break;
            }
            case 0: {
                System.out.println("a = " + a + ", b = " + b + ", a = b");
                break;
            }
            case -1: {
                System.out.println("a = " + a + ", b = " + b + ", a < b");
                break;
            }
        }
    }

}
