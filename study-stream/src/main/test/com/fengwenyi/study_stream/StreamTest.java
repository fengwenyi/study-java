package com.fengwenyi.study_stream;

import com.google.gson.Gson;
import one.util.streamex.StreamEx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 通过Employee的操作例子来学习java 8 stream api
 * @author Erwin Feng
 * @since 2020/5/13
 */
public class StreamTest extends Base {

    private static final Logger logger = LogManager.getLogger(StreamTest.class.getName());

    @Test
    public void data() {
        String jsonStr = new Gson().toJson(list);
        System.out.println(jsonStr);
    }

    @Test
    public void create() {
        // Stream.generate();
        // Stream.iterate()
    }

    @Test
    public void filter() {
        // 需求：我们要查找薪酬为5000的员工列表
        List<Employee> employees = list.stream().filter(employee -> employee.getSalary() == 5000)
                .peek(System.out::println)
                .collect(Collectors.toList());
        Assert.assertEquals(2, employees.size());

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
        Assert.assertEquals(3, leaders.size());
    }

    @Test
    public void flatMap() {
        // 需求：将多维的列表转化为单维的列表
        List<Employee> employees = multidimensionalList.stream().flatMap(Collection::stream).collect(Collectors.toList());
        Assert.assertEquals(9, employees.size());
        // flatMap 接收的一个流
        List<Employee> employees2 = multidimensionalList.stream().flatMap(list -> list.stream()).peek(System.out::println).collect(Collectors.toList());
        Assert.assertEquals(employees, employees2);
    }

    @Test
    public void sorted() {
        // 需求：根据薪酬排序

        // 薪酬从小到大排序
        List<Employee> employees = list.stream().sorted(Comparator.comparing(Employee::getSalary)).peek(System.out::println).collect(Collectors.toList());

        System.out.println();

        // 薪酬从大到小排序
        List<Employee> employees2 = list.stream().sorted(Comparator.comparing(Employee::getSalary).reversed()).peek(System.out::println).collect(Collectors.toList());
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
        // 有没有薪酬大于等于7000
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
        // 去重，根据 Object#equals(Object) 去重
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

    @Test
    public void reduce() {
        // reduce 是裁剪，减少的意思

        // 需求：计算薪酬总和
        // 先将员工列表转换为薪酬列表
        // 再计算薪酬总和
        double salarySum = list.stream().map(Employee::getSalary).reduce(Double::sum).orElse(0.0);
        double sum = list.stream().mapToDouble(Employee::getSalary).sum();
        Assert.assertEquals(salarySum, sum, 0.0);

        double salarySum2 = list.stream().map(Employee::getSalary).reduce((a, b) -> {
            System.out.println("a=" + a + ", b=" + b);
            return a + b;
        }).orElse(0.0);
        Assert.assertEquals(salarySum2, sum, 0.0);

        double salarySum3 = list.stream().map(Employee::getSalary).reduce(AddUtils::add).orElse(0.0);
        Assert.assertEquals(salarySum3, sum, 0.0);

        double salarySum4 = list.stream().map(Employee::getSalary).reduce(0.00, Double::sum);
        Assert.assertEquals(salarySum4, sum, 0.0);

        double salarySum5 = list.stream().map(Employee::getSalary).reduce(1.00, Double::sum);
        Assert.assertEquals(salarySum5, sum + 1, 0.0);
    }

    static class AddUtils {
        public static Double add(Double a, Double b) {
            return a + b;
        }
    }

    @Test
    public void collector() {
        String employeeNames = list.stream().map(Employee::getName).collect(Collectors.joining(", "));
        System.out.println(employeeNames); // Jacob, Sophia, Rose, Lily, Daisy, Jane, Jasmine, Jack, Poppy

        List<String> employeeNameList = list.stream().map(Employee::getName).collect(Collectors.toList());
        System.out.println(employeeNameList);

        Set<String> employeeNameSet = list.stream().map(Employee::getName).collect(Collectors.toSet());
        System.out.println(employeeNameSet);

        Vector<String> employeeNameVector = list.stream().map(Employee::getName).collect(Collectors.toCollection(Vector::new));
        System.out.println(employeeNameVector);

        Map<Integer, String> employeesMap = list.stream().collect(Collectors.toMap(Employee::getId, Employee::getName));
        System.out.println(employeesMap);
    }

    @Test
    public void count() {
        // 计算流中有多少元素
        long count = list.stream().count();
        Assert.assertEquals(9, count);

        // 需求：薪酬为5000的员工数
        int count2 = 0;
        for (Employee employee : list) {
            if (employee.getSalary() == 5000) {
                count2++;
            }
        }
        System.out.println(count2);

        long count3 = list.stream().filter(employee -> employee.getSalary() == 5000).count();
        Assert.assertEquals(count3, count2);
    }

    @Test
    public void summarizingDouble() {
        // 统计分析
        DoubleSummaryStatistics employeeSalaryStatistics = list.stream().collect(Collectors.summarizingDouble(Employee::getSalary));
        System.out.println("employee salary statistics:" + employeeSalaryStatistics);
        // employee salary statistics:DoubleSummaryStatistics{count=9, sum=39000.000000, min=1000.000000, average=4333.333333, max=7000.000000}

        DoubleSummaryStatistics employeeSalaryStatistics2 = list.stream().mapToDouble(Employee::getSalary).summaryStatistics();
        System.out.println("employee salary statistics2:" + employeeSalaryStatistics2);

    }

    @Test
    public void partitioningBy() {
        // 分成满足条件（true）和不满足条件（false）两个区
        // 需求：找出薪酬大于5000的员工
        Map<Boolean, List<Employee>> map = list.stream().collect(Collectors.partitioningBy(employee -> employee.getSalary() > 5000));
        System.out.println("true:" + map.get(Boolean.TRUE));
        System.out.println("false:" + map.get(Boolean.FALSE));
        // true:[Employee{id=7, name='Jasmine', salary=6000.0}, Employee{id=8, name='Jack', salary=6000.0}, Employee{id=9, name='Poppy', salary=7000.0}]
    }

    @Test
    public void groupingBy() {
        // 分组
        // 需求：根据员工薪酬分组
        Map<Double, List<Employee>> map = list.stream().collect(Collectors.groupingBy(Employee::getSalary));
        System.out.println(map);

        Map<Double, List<Employee>> map4 = list.stream().collect(Collectors.groupingBy(Employee::getSalary, Collectors.toList()));
        System.out.println(map4);

        // 薪酬-员工数
        Map<Double, Long> map2 = list.stream().collect(Collectors.groupingBy(Employee::getSalary, Collectors.counting()));
        System.out.println(map2);

        // 薪酬-总和
        Map<Double, Double> map3 = list.stream().collect(Collectors.groupingBy(Employee::getSalary, Collectors.summingDouble(Employee::getSalary)));
        System.out.println(map3);
    }

    @Test
    public void parallel() {
        // 平行计算
        list.stream().parallel().forEach(StreamTest::cal);
        /*
        2020-05-15 01:47:14.231 [ForkJoinPool.commonPool-worker-4] INFO  com.fengwenyi.study_stream.StreamTest - employee name: Jacob
        2020-05-15 01:47:15.226 [ForkJoinPool.commonPool-worker-2] INFO  com.fengwenyi.study_stream.StreamTest - employee name: Sophia
        2020-05-15 01:47:16.226 [ForkJoinPool.commonPool-worker-1] INFO  com.fengwenyi.study_stream.StreamTest - employee name: Rose
        2020-05-15 01:47:17.226 [ForkJoinPool.commonPool-worker-3] INFO  com.fengwenyi.study_stream.StreamTest - employee name: Lily
        2020-05-15 01:47:18.225 [main] INFO  com.fengwenyi.study_stream.StreamTest - employee name: Jane
        2020-05-15 01:47:18.228 [ForkJoinPool.commonPool-worker-7] INFO  com.fengwenyi.study_stream.StreamTest - employee name: Daisy
        2020-05-15 01:47:19.226 [ForkJoinPool.commonPool-worker-5] INFO  com.fengwenyi.study_stream.StreamTest - employee name: Jack
        2020-05-15 01:47:19.228 [ForkJoinPool.commonPool-worker-6] INFO  com.fengwenyi.study_stream.StreamTest - employee name: Jasmine
        2020-05-15 01:47:21.234 [ForkJoinPool.commonPool-worker-4] INFO  com.fengwenyi.study_stream.StreamTest - employee name: Poppy
         */
    }

    private static void cal(Employee employee) {
        try {
            long sleepTime = employee.getSalary().longValue();
            TimeUnit.MILLISECONDS.sleep(sleepTime);
            logger.info("employee name: {}", employee.getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void file() throws IOException {
        String tempFilePath = "E:\\temp\\text.txt";

        /*PrintWriter printWriter = new PrintWriter(Files.newBufferedWriter(Paths.get(tempFilePath)));
        list.forEach(printWriter::println);
        printWriter.close();*/

        try (PrintWriter printWriter = new PrintWriter(Files.newBufferedWriter(Paths.get(tempFilePath)))) { // 使用 try 自动关闭流
            list.forEach(printWriter::println);
            list.forEach(employee -> printWriter.println(employee.getName())); // 将员工的姓名写到文件中
        }

        // 从文件中读取员工的姓名
        List<String> s = Files.lines(Paths.get(tempFilePath)).peek(System.out::println).collect(Collectors.toList());
    }

}
