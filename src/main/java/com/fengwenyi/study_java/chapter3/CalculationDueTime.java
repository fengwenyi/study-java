package com.fengwenyi.study_java.chapter3;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

        int effectiveYear = 3;
        int effectiveMonth = 3;
        int effectiveDay = 1;

        LocalDate dueDateByYear = getDueDateByYear(now, effectiveYear);
        System.out.println("现在是：" + now +", 有效时间 " + effectiveYear + " 年，到期时间为：" + dueDateByYear);

        LocalDate dueDateByMonth = getDueDateByMonth(now, effectiveMonth);
        System.out.println("现在是：" + now +", 有效时间 " + effectiveMonth + " 个月，到期时间为：" + dueDateByMonth);

        LocalDate dueDateByDay = getDueDateByDay(now, effectiveDay);
        System.out.println("现在是：" + now +", 有效时间 " + effectiveDay + " 天，到期时间为：" + dueDateByDay);

        LocalDateTime nowDateTime = LocalDateTime.now();

        LocalDateTime dueDateTimeByDay = getDueDateTimeByDay(nowDateTime, effectiveDay);
        System.out.println("现在是：" + nowDateTime +", 有效时间 " + effectiveDay + " 天，到期时间为：" + dueDateTimeByDay);
    }

    // 获取到期日期
    private static LocalDate getDueDateByYear(LocalDate startDate, int effectiveYear) {
         return startDate.plusYears(effectiveYear).minusDays(1);
    }

    // 获取到期日期
    private static LocalDate getDueDateByMonth(LocalDate startDate, int effectiveMonth) {
        return startDate.plusMonths(effectiveMonth).minusDays(1);
    }

    private static LocalDate getDueDateByDay(LocalDate startDate, int effectiveDay) {
        return startDate.plusDays(effectiveDay);
    }

    private static LocalDateTime getDueDateTimeByDay(LocalDateTime startDateTime, int effectiveDay) {
        return startDateTime.plusDays(effectiveDay);
    }

}
