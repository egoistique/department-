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
public class DepartmentRepository implements Repository {

    @Inject
    CompanyDataStore store ;

    @Override
    public void createDepartment(Department department){
        store.departments.add(department);
    }
    @Override
    public void deleteDepartment(String departmentName){
        store.departments.removeIf(department -> department.getName().equals(departmentName));
    }
    @Override
    public Department getDepartmentByName(String departmentName){
        for (Department department : store.departments) {
            if (department.getName().equals(departmentName)) {
                return department;
            }
        }
        return null;
    }
    public List<Department> getAllDepartments(){
        return store.departments;
    }

    public void addEmployeeToDepartment(Employee employee, String depName){
        Department department = getDepartmentByName(depName);

        if (department != null) {
            department.addEmployee(employee);
        } else {
            System.out.println("Отдел с именем " + depName + " не найден.");
        }
    }

//    public void deleteEmployee(String employeeName, String dep){
//        for (Department d : store.departments) {
//            if(d.getName().equals(dep)) {
//                for (Employee e : d.getEmployeesIds()) {
//                    if (e.getName().equals(employeeName)) {
//                        d.removeEmp(e);
//                        return;
//                    }
//                }
//            }
//        }
//    }
}

