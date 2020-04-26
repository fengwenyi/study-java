package com.fengwenyi.study_java.chapter3;

import sun.tools.jconsole.Plotter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * 计算到期时间
 * <p>
 *     比如，你签订合同的时间是：2019-12-04，合同期限是三年，那么合同到期时间就是2022-12-03
 * </p>
 * @author Erwin Feng[xfsy_2015@163.com]
 * @since 2019/12/4 17:39
 */
public class CalculationDueTime {

    public static void main(String[] args) {

        LocalDate now = LocalDate.now();

        LocalDateTime localDateTime = LocalDateTime.now();

        // 加
        //localDateTime.plus(, ChronoUnit.);

        long effective = 0;

        // 多少年以后
        localDateTime.plusYears(effective);

        // 多少月以后
        localDateTime.plusMonths(effective);
        // 多少周以后
        localDateTime.plusWeeks(effective);
        // 多少天以后
        localDateTime.plusDays(effective);
        // 多少小时以后
        localDateTime.plusHours(effective);
        // 多少分钟以后
        localDateTime.plusMinutes(effective);
        // 多少秒以后
        localDateTime.plusSeconds(effective);

        // 减
        // 多少年以前
        localDateTime.minusYears(effective);
        // 多少月以前
        localDateTime.minusMonths(effective);
        // 多少周以前
        localDateTime.minusWeeks(effective);
        // 多少天以前
        localDateTime.minusDays(effective);
        // 多少小时以前
        localDateTime.minusHours(effective);
        // 多少分钟以前
        localDateTime.minusMinutes(effective);
        // 多少秒以前
        localDateTime.minusSeconds(effective);

        // 算当前就-1

    }



}
