package department.data.repository;

import department.data.model.Department;

public interface Repository {
    void createDepartment(Department department);

    void deleteDepartment(String departmentName);

    Department getDepartmentByName(String departmentName);

    //List<Department> findAll();
}

