package com.fengwenyi.study_stream;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Erwin Feng
 * @since 2020/4/27 2:08
 */
public class StreamFilter extends Base {

    public static void main(String[] args) {
        List<Employee> employees = list.stream().filter(employee -> employee.getSalary() > 5000.00).peek(System.out::println).collect(Collectors.toList());
    }

}
