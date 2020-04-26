package com.fengwenyi.study_stream;

import java.util.Arrays;
import java.util.List;

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
            new Employee(6, "Jasmine", 6000.00),
            new Employee(7, "Poppy", 7000.00)
    );

}
