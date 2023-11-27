package department.data.datastore;

import department.data.model.Department;
import department.data.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class CompanyDataStore {
    public List<Employee> employees = new ArrayList<>();
    public List<Department> departments = new ArrayList<>();

    public CompanyDataStore(){

        Department d1 = new Department(1, "Dep1" );
        Department d2 = new Department(2, "Dep2" );
        Department d3 = new Department(3, "Dep3" );

        Employee e1 = new Employee(1,"Вася", 20, 90000, 1);
        Employee e2 = new Employee(2, "Петя", 21, 60000, 2);
        Employee e3 = new Employee(3, "Маша", 22, 80000, 3);
        Employee e4 = new Employee(4, "Оля", 23, 95000, 1);
        Employee e5 = new Employee(5, "Вика", 24, 100000, 2);

        d1.addEmployee(e1);
        d1.addEmployee(e4);
        d2.addEmployee(e2);
        d2.addEmployee(e5);
        d3.addEmployee(e3);

        employees.add(e1);
        employees.add(e2);
        employees.add(e3);
        employees.add(e4);
        employees.add(e5);

        departments.add(d1);
        departments.add(d2);
        departments.add(d3);
    }
}
