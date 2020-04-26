package com.fengwenyi.study_stream;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Erwin Feng
 * @since 2020/4/27 2:45
 */
public class StreamMap extends Base {

    public static void main(String[] args) {
        List<Leader> leaders = list.stream().map(employee -> {
            Leader leader = new Leader();
            leader.setName(employee.getName());
            leader.setSalary(employee.getSalary());
            return leader;
        }).peek(System.out::println).collect(Collectors.toList());
    }

    static class Leader {
        private String name;
        private Double salary;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getSalary() {
            return salary;
        }

        public void setSalary(Double salary) {
            this.salary = salary;
        }

        public Leader() {
        }

        @Override
        public String toString() {
            return "Leader{" +
                    "name='" + name + '\'' +
                    ", salary=" + salary +
                    '}';
        }
    }

}
