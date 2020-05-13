package com.fengwenyi.study_stream;

import one.util.streamex.StreamEx;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 通过Employee的操作例子来学习java 8 stream api
 * @author Erwin Feng
 * @since 2020/5/13
 */
public class StreamTest extends Base {

    @Test
    public void filter() {
        // 需求：我们要查找薪酬为5000的员工
        List<Employee> employees = list.stream().filter(employee -> employee.getSalary() == 5000)
                .peek(System.out::println)
                .collect(Collectors.toList());
        Assert.assertEquals(1, employees.size());

        List<Employee> employees2 = list.stream().filter(employee -> employee.getSalary() == 5000 && employee.getName().equals("Jack"))
                .peek(System.out::println)
                .collect(Collectors.toList());
        Assert.assertEquals(0, employees2.size());
    }

    @Test
    public void map() {
        // 需求：将薪酬大于5000的员工放到Leader对象中
        List<Leader> leaders = list.stream().filter(employee -> employee.getSalary() > 5000).map(employee -> {
            Leader leader = new Leader();
            leader.setName(employee.getName());
            leader.setSalary(employee.getSalary());
            return leader;
        }).peek(System.out::println).collect(Collectors.toList());
        Assert.assertEquals(2, leaders.size());
    }

    @Test
    public void flatMap() {
        // 需求：将多维的列表转化为单维的列表
        List<Employee> employees = multidimensionalList.stream().flatMap(Collection::stream).collect(Collectors.toList());
        Assert.assertEquals(7, employees.size());
        // flatMap 接收的一个流
        List<Employee> employees2 = multidimensionalList.stream().flatMap(list -> list.stream()).peek(System.out::println).collect(Collectors.toList());
        Assert.assertEquals(employees, employees2);
    }

    @Test
    public void sorted() {
        // 需求：根据薪酬排序

        // 薪酬从小到大排序
        list.stream().sorted(Comparator.comparing(Employee::getSalary)).peek(System.out::println).collect(Collectors.toList());

        System.out.println();

        // 薪酬从大到小排序
        list.stream().sorted(Comparator.comparing(Employee::getSalary).reversed()).peek(System.out::println).collect(Collectors.toList());
    }

    @Test
    public void min() {
        double minValue = list.stream().mapToDouble(Employee::getSalary).min().orElse(0);
        Assert.assertEquals(1000, minValue, 0.0);

        Employee employee = list.stream().min(Comparator.comparing(Employee::getSalary)).orElse(null);
        assert employee != null;
        Assert.assertEquals(employee.getSalary(), minValue, 0.0);
    }

    @Test
    public void max() {
        double maxValue = list.stream().mapToDouble(Employee::getSalary).max().orElse(0);
        Assert.assertEquals(7000, maxValue, 0.0);
    }

    @Test
    public void average() {
        double sum = list.stream().mapToDouble(Employee::getSalary).sum();
        double averageValue = list.stream().mapToDouble(Employee::getSalary).average().orElse(0);
        Assert.assertEquals(sum / list.size(), averageValue, 0.0);
    }

    @Test
    public void match() {
        // allMatch 集合中的元素都要满足条件才会返回true
        // 薪酬都是大于等于1000的
        boolean isAllMatch = list.stream().allMatch(employee -> employee.getSalary() >= 1000);
        Assert.assertTrue(isAllMatch);

        // anyMatch 集合中只要有一个元素满足条件就会返回true
        // 只要有一个薪酬大于等于7000
        boolean isAnyMatch = list.stream().anyMatch(employee -> employee.getSalary() >= 7000);
        Assert.assertTrue(isAnyMatch);

        // noneMatch 集合中没有元素满足条件才会返回true
        // 没有薪酬小于1000的
        boolean isNoneMatch = list.stream().noneMatch(employee -> employee.getSalary() < 1000);
        Assert.assertTrue(isNoneMatch);
    }

    // 去重
    @Test
    public void distinct() {
        // 去重，根据对象的hash值去重
        List<Employee> employees = list.stream().distinct().collect(Collectors.toList());
        Assert.assertEquals(9, employees.size());

        List<Integer> nums = Arrays.asList(0, 1, 2, 3, 4, 1, 2, 3);
        List<Integer> integers = nums.stream().distinct().collect(Collectors.toList());
        Assert.assertEquals(5, integers.size());

        // 根据对象的属性值去重
//        Map<Double, Employee> map = list.stream().collect(Collectors.toMap(Employee::getSalary, Function.identity()));
        // https://segmentfault.com/a/1190000015775709
        // 报错 java.lang.IllegalStateException: Duplicate key Employee{id=5, name='Daisy', salary=5000.0}
//        Assert.assertEquals(7, map.size());


        // 使用StreamEx去重
        List<Employee> employees2 = StreamEx.of(list).distinct(Employee::getSalary).collect(Collectors.toList());
        Assert.assertEquals(7, employees2.size());


        // 自己写
        List<Employee> employees3 = list.stream().filter(distinctByKey(Employee::getSalary)).collect(Collectors.toList());
        Assert.assertEquals(7, employees3.size());
    }

    private static <T>Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> result = new ConcurrentHashMap<>();
        return t -> result.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
