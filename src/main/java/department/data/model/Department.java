package department.data.model;

import java.util.ArrayList;
import java.util.List;

public class Department implements Entity{
    private int id;
    private String name;
    private List<Integer> employeesIds;
    private int numberOfEmployees;

    public Department(int id, String name) {
        this.id = id;
        this.name = name;
        this.employeesIds = new ArrayList<>();
        this.numberOfEmployees = employeesIds.size();
    }

    public List<Integer> getEmployeesIds() {
        return employeesIds;
    }

    public void setEmployeesIds(List<Integer> employeesIds) {
        this.employeesIds = employeesIds;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfEmployees() {
        numberOfEmployees = employeesIds.size();
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(int numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public void addEmployee(Employee employee) {
        if (this.employeesIds == null) {
            this.employeesIds = new ArrayList<>();
        }
        this.employeesIds.add(employee.getId());
    }
    public void removeEmp(Employee e){
        employeesIds.remove(e);
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
