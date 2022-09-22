package com.example.thi_dawd;

public class Employee {
    private long id;
    private String name;
    private String designation;
    private Double salary;

    public Employee() {
        super();
    }

    public Employee(String name, String designation, Double salary) {
        this.name = name;
        this.designation = designation;
        this.salary = salary;
    }

    public Employee(long id, String name, String designation, Double salary) {
        super();
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.salary = salary;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}