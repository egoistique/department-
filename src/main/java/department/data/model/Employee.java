package department.data.model;

public class Employee {
    private int id;
    private String name;
    private int age;
    private double salary;
    private int department_id;

    public Employee(int id, String name, int age, double salary, int department_id) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.department_id = department_id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getSalary() {
        return salary;
    }

    public int getDepartmentId() {
        return department_id;
    }
}

