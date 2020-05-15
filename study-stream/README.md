# Java 8 Stream API 学习总结

Java 8 API添加了一个新的抽象称为流Stream，可以让你以一种声明的方式处理数据。Stream API可以极大提高Java程序员的生产力，让程序员写出高效率、干净、简洁的代码。这种风格将要处理的元素集合看作一种流， 流在管道中传输， 并且可以在管道的节点上进行处理， 比如筛选， 排序，聚合等。元素流在管道中经过中间操作（intermediate operation）的处理，最后由最终操作(terminal operation)得到前面处理的结果。



这一次为什么要系统性的总结一下 `Java 8 Stream API` 呢？说得简单点，我们先不论性能，我们就是为了 `装x` ，而且要让这个 `x` 装得再优秀一些，仅此而已！



![Stream Tests](https://images.fengwenyi.com/1261398909869170689)



## Stream基础知识

### 流程

`创建流`  →  `流的中间操作`  →  `流的最终操作`

### 创建流

我们需要把哪些元素放入流中，常见的api有：

```
// 使用List创建流
list.stream()

// 使用一个或多个元素创建流
Stream.of(T value)
Stream.of(T... values)

// 使用数组创建流
Arrays.stream(T[] array)

// 创建一个空流
Stream.empty()

// 两个流合并
Stream.concat(Stream<? extends T> a, Stream<? extends T> b)

// 无序无限流
Stream.generate(Supplier<T> s)

// 通过迭代产生无限流
Stream.iterate(final T seed, final UnaryOperator<T> f)
```



### 流的中间操作

```
// 元素过滤
filter
limit
skip
distinct

// 映射
map
flatmap

// 排序
```



### 流的最终操作

通过流对元素的最终操作，我们想得到一个什么样的结果



## 构造测试数据

### 员工实体类

```java
/**
 * 员工实体类
 * @author Erwin Feng
 * @since 2020/4/27 2:10
 */
public class Employee {

    /** 员工ID */
    private Integer id;

    /** 员工姓名 */
    private String name;

    /** 员工薪资 */
    private Double salary;
    
    /** 构造方法、getter and setter、toString */
}
```

### 测试数据列表

```json
[
    {
        "id":1,
        "name":"Jacob",
        "salary":1000
    },
    {
        "id":2,
        "name":"Sophia",
        "salary":2000
    },
    {
        "id":3,
        "name":"Rose",
        "salary":3000
    },
    {
        "id":4,
        "name":"Lily",
        "salary":4000
    },
    {
        "id":5,
        "name":"Daisy",
        "salary":5000
    },
    {
        "id":6,
        "name":"Jane",
        "salary":5000
    },
    {
        "id":7,
        "name":"Jasmine",
        "salary":6000
    },
    {
        "id":8,
        "name":"Jack",
        "salary":6000
    },
    {
        "id":9,
        "name":"Poppy",
        "salary":7000
    }
]
```



## Stream API Test

### filter 过滤

需求：查找薪酬为5000的员工列表

```java
List<Employee> employees = list.stream().filter(employee -> employee.getSalary() == 5000)
        .peek(System.out::println)
        .collect(Collectors.toList());
Assert.assertEquals(2, employees.size());
```



### map 映射

需求：将薪酬大于5000的员工放到Leader对象中

```java
List<Leader> leaders = list.stream().filter(employee -> employee.getSalary() > 5000).map(employee -> {
    Leader leader = new Leader();
    leader.setName(employee.getName());
    leader.setSalary(employee.getSalary());
    return leader;
}).peek(System.out::println).collect(Collectors.toList());
Assert.assertEquals(3, leaders.size());
```



### flatMap 水平映射

需求：将多维的列表转化为单维的列表

> 说明：
>
> 我们将薪酬在1000-3000的分为一个列表，4000-5000分为一个列表，6000-7000分为一个列表。
>
> 将这三个列表组合在一起形成一个多维列表。

```java
List<Employee> employees = multidimensionalList.stream().flatMap(Collection::stream).collect(Collectors.toList());
Assert.assertEquals(9, employees.size());
```



### sorted 排序

需求：根据薪酬排序

```java
// 薪酬从小到大排序
List<Employee> employees = list.stream().sorted(Comparator.comparing(Employee::getSalary)).peek(System.out::println).collect(Collectors.toList());

// 薪酬从大到小排序
List<Employee> employees2 = list.stream().sorted(Comparator.comparing(Employee::getSalary).reversed()).peek(System.out::println).collect(Collectors.toList());
```



### min 最小值

```java
double minValue = list.stream().mapToDouble(Employee::getSalary).min().orElse(0);
Assert.assertEquals(1000, minValue, 0.0);

Employee employee = list.stream().min(Comparator.comparing(Employee::getSalary)).orElse(null);
assert employee != null;
Assert.assertEquals(employee.getSalary(), minValue, 0.0);
```



### max 最大值

```java
double maxValue = list.stream().mapToDouble(Employee::getSalary).max().orElse(0);
Assert.assertEquals(7000, maxValue, 0.0);
```



### average 平均值

```java
double sum = list.stream().mapToDouble(Employee::getSalary).sum();
double averageValue = list.stream().mapToDouble(Employee::getSalary).average().orElse(0);
Assert.assertEquals(sum / list.size(), averageValue, 0.0);
```



### match 匹配

```java
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
```



### distinct 去重

默认的 `distinct()` 不接收参数，是根据 `Object#equals(Object)` 去重。根据API介绍，这是一个有中间状态的操作。

```java
List<Employee> employees = list.stream().distinct().collect(Collectors.toList());
Assert.assertEquals(9, employees.size());
```



如果我们要根据对象中的某个属性去重的，可以使用 `StreamEx`

```java
// 使用StreamEx去重
List<Employee> employees2 = StreamEx.of(list).distinct(Employee::getSalary).collect(Collectors.toList());
Assert.assertEquals(7, employees2.size());
```



当然也可以使用JDK Stream API

```java
private static <T>Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
    Map<Object, Boolean> result = new ConcurrentHashMap<>();
    return t -> result.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
}

List<Employee> employees3 = list.stream().filter(distinctByKey(Employee::getSalary)).collect(Collectors.toList());
Assert.assertEquals(7, employees3.size());
```



### reduce 裁减

需求：计算薪酬总和

```java
// 先将员工列表转换为薪酬列表
// 再计算薪酬总和
double salarySum = list.stream().map(Employee::getSalary).reduce(Double::sum).orElse(0.0);
double sum = list.stream().mapToDouble(Employee::getSalary).sum();
Assert.assertEquals(salarySum, sum, 0.0);
```



另外，我们也可以设定一个累加函数的标识值

```java
double salarySum5 = list.stream().map(Employee::getSalary).reduce(1.00, Double::sum);
Assert.assertEquals(salarySum5, sum + 1, 0.0);
```



### collector 流的终止结果

```java
// joining 拼接字符串
String employeeNames = list.stream().map(Employee::getName).collect(Collectors.joining(", "));
System.out.println(employeeNames); // Jacob, Sophia, Rose, Lily, Daisy, Jane, Jasmine, Jack, Poppy

// 返回一个List
List<String> employeeNameList = list.stream().map(Employee::getName).collect(Collectors.toList());
System.out.println(employeeNameList);

// 返回一个Set
Set<String> employeeNameSet = list.stream().map(Employee::getName).collect(Collectors.toSet());
System.out.println(employeeNameSet);

// 返回一个Vector
Vector<String> employeeNameVector = list.stream().map(Employee::getName).collect(Collectors.toCollection(Vector::new));
System.out.println(employeeNameVector);

// 返回一个Map
Map<Integer, String> employeesMap = list.stream().collect(Collectors.toMap(Employee::getId, Employee::getName));
System.out.println(employeesMap);
```



### count 统计

需求：薪酬为5000的员工数



不使用流

```java
int count2 = 0;
for (Employee employee : list) {
    if (employee.getSalary() == 5000) {
        count2++;
    }
}
System.out.println(count2);
```



使用流

```java
long count3 = list.stream().filter(employee -> employee.getSalary() == 5000).count();
Assert.assertEquals(count3, count2);
```



### summarizingDouble 统计分析

```java
DoubleSummaryStatistics employeeSalaryStatistics = list.stream().collect(Collectors.summarizingDouble(Employee::getSalary));
System.out.println("employee salary statistics:" + employeeSalaryStatistics);

DoubleSummaryStatistics employeeSalaryStatistics2 = list.stream().mapToDouble(Employee::getSalary).summaryStatistics();
System.out.println("employee salary statistics2:" + employeeSalaryStatistics2);
```



> {count=9, sum=39000.000000, min=1000.000000, average=4333.333333, max=7000.000000}



### partitioningBy 分区

分成满足条件（true）和不满足条件（false）两个区



需求：找出薪酬大于5000的员工

```java
Map<Boolean, List<Employee>> map = list.stream().collect(Collectors.partitioningBy(employee -> employee.getSalary() > 5000));
System.out.println("true:" + map.get(Boolean.TRUE));
System.out.println("false:" + map.get(Boolean.FALSE));
```



> true:[Employee{id=7, name='Jasmine', salary=6000.0}, Employee{id=8, name='Jack', salary=6000.0}, Employee{id=9, name='Poppy', salary=7000.0}]



> false:[Employee{id=1, name='Jacob', salary=1000.0}, Employee{id=2, name='Sophia', salary=2000.0}, Employee{id=3, name='Rose', salary=3000.0}, Employee{id=4, name='Lily', salary=4000.0}, Employee{id=5, name='Daisy', salary=5000.0}, Employee{id=6, name='Jane', salary=5000.0}]



### groupingBy 分组

需求：根据员工薪酬分组

```java
Map<Double, List<Employee>> map = list.stream().collect(Collectors.groupingBy(Employee::getSalary));
System.out.println(map);
```



再举一个例子：薪酬 一> 总和（薪酬*员工数）

```java
Map<Double, Double> map3 = list.stream().collect(Collectors.groupingBy(Employee::getSalary, Collectors.summingDouble(Employee::getSalary)));
System.out.println(map3);
```



### parallel 平行计算

简单的说，就是启动多个线程计算

```java
private static void cal(Employee employee) {
    try {
        long sleepTime = employee.getSalary().longValue();
        TimeUnit.MILLISECONDS.sleep(sleepTime);
        logger.info("employee name: {}", employee.getName());
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}

list.stream().parallel().forEach(StreamTest::cal);
```



> ```
> 2020-05-15 01:47:14.231 [ForkJoinPool.commonPool-worker-4] INFO  com.fengwenyi.study_stream.StreamTest - employee name: Jacob
> 2020-05-15 01:47:15.226 [ForkJoinPool.commonPool-worker-2] INFO  com.fengwenyi.study_stream.StreamTest - employee name: Sophia
> 2020-05-15 01:47:16.226 [ForkJoinPool.commonPool-worker-1] INFO  com.fengwenyi.study_stream.StreamTest - employee name: Rose
> 2020-05-15 01:47:17.226 [ForkJoinPool.commonPool-worker-3] INFO  com.fengwenyi.study_stream.StreamTest - employee name: Lily
> 2020-05-15 01:47:18.225 [main] INFO  com.fengwenyi.study_stream.StreamTest - employee name: Jane
> 2020-05-15 01:47:18.228 [ForkJoinPool.commonPool-worker-7] INFO  com.fengwenyi.study_stream.StreamTest - employee name: Daisy
> 2020-05-15 01:47:19.226 [ForkJoinPool.commonPool-worker-5] INFO  com.fengwenyi.study_stream.StreamTest - employee name: Jack
> 2020-05-15 01:47:19.228 [ForkJoinPool.commonPool-worker-6] INFO  com.fengwenyi.study_stream.StreamTest - employee name: Jasmine
> 2020-05-15 01:47:21.234 [ForkJoinPool.commonPool-worker-4] INFO  com.fengwenyi.study_stream.StreamTest - employee name: Poppy
> ```



### file 文件操作

```java
try (PrintWriter printWriter = new PrintWriter(Files.newBufferedWriter(Paths.get(tempFilePath)))) { // 使用 try 自动关闭流
    list.forEach(printWriter::println);
    list.forEach(employee -> printWriter.println(employee.getName())); // 将员工的姓名写到文件中
}

// 从文件中读取员工的姓名
List<String> s = Files.lines(Paths.get(tempFilePath)).peek(System.out::println).collect(Collectors.toList());
```





## 测试代码

[Study Java 8 Stream API](https://github.com/fengwenyi/study-java/tree/master/study-stream)



![StreamTest Method List](https://images.fengwenyi.com/1261399054102896642)



## 学习链接

- [noodlespan > Stream系列](https://space.bilibili.com/372774607/channel/detail?cid=98302)
- [Java 8 中的 Streams API 详解](https://www.ibm.com/developerworks/cn/java/j-lo-java8streamapi/)
- [Java8新特性-Stream API 常用完整版](https://blog.csdn.net/hxhaaj/article/details/80725857)
- [Stream In Java](https://www.geeksforgeeks.org/stream-in-java/)