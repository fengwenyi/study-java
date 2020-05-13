package com.fengwenyi.study_stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Erwin Feng
 * @since 2020/4/27 2:09
 */
public class Base {

    protected static List<Employee> list = Arrays.asList(
            new Employee(1, "Jacob", 1000.00),
            new Employee(2, "Sophia", 2000.00),
            new Employee(3, "Rose", 3000.00),
            new Employee(4, "Lily", 4000.00),
            new Employee(5, "Daisy", 5000.00),
            new Employee(6, "Jane", 5000.00),
            new Employee(7, "Jasmine", 6000.00),
            new Employee(8, "Jack", 6000.00),
            new Employee(9, "Poppy", 7000.00)
    );

    protected static List<List<Employee>> multidimensionalList = getMultidimensionalList();

    // 我们将薪资1000-3000,4000-500,6000-7000分别放在一起，然后在组合放在一起
    private static List<List<Employee>> getMultidimensionalList() {
        multidimensionalList = new ArrayList<>();
        multidimensionalList.add(list.stream().filter(employee -> employee.getSalary() >= 1000 && employee.getSalary() <= 3000).collect(Collectors.toList()));
        multidimensionalList.add(list.stream().filter(employee -> employee.getSalary() >= 4000 && employee.getSalary() <= 5000).collect(Collectors.toList()));
        multidimensionalList.add(list.stream().filter(employee -> employee.getSalary() >= 6000 && employee.getSalary() <= 7000).collect(Collectors.toList()));
        return multidimensionalList;
    }

}
