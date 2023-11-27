package department.data.repository;

import department.di.annotation.Inject;
import department.di.annotation.Injectable;
import department.data.datastore.CompanyDataStore;
import department.data.model.Department;
import department.data.model.Employee;

import java.util.List;

/**
 * Класс репозитория, отвечает за добавление, поиск и тд
 */
@Injectable
public class EmployeeRepository {

 //   @Inject
//    CompanyDataStore store;
 CompanyDataStore store = new CompanyDataStore();
    public void createEmployee(Employee employee){
        store.employees.add(employee);
    }

    public void deleteEmployee(String empName) {
        store.employees.removeIf(employee -> employee.getName().equals(empName));
    }


    public Department getDepartmentByName(String departmentName) {
        return null;
    }

    public Employee getEmployeeById(int employeeId){
        return store.employees.get(employeeId);
    }

    public List<Employee> getAll(){
        return store.employees;
    }


    public void createDepartment(Department department) {

    }


    public void deleteDepartment(Department department) {

    }
}

