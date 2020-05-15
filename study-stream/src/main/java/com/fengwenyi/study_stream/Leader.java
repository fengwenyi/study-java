package com.fengwenyi.study_stream;

/**
 * @author Erwin Feng
 * @since 2020/5/13
 */
public class Leader {

    /** 员工姓名 */
    private String name;

    /** 员工薪酬 */
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
