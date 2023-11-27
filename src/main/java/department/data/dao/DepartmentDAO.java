package department.data.dao;

import department.data.model.Department;
import department.data.model.Employee;
import department.di.annotation.Inject;
import department.di.annotation.Injectable;
import department.di.factory.BeanFactory;
import department.orm.DepartmentORM;

import java.sql.*;
import java.util.List;

@Injectable
public class DepartmentDAO implements DAO<Department> {
//    @Inject
//    private final DepartmentORM departmentORM = BeanFactory.getInstance().getBean(DepartmentORM.class);

    private final DepartmentORM departmentORM = new DepartmentORM();

    public DepartmentDAO() throws SQLException {
    }

    public void create(String name) throws SQLException {
        departmentORM.beginTransaction();
        try {
            departmentORM.create(name);
            departmentORM.commitTransaction();
        } catch (SQLException e) {
            departmentORM.rollbackTransaction();
            throw e;
        }
    }

    @Override
    public Department getById(int departmentId) throws SQLException {
        return departmentORM.getById(departmentId);
    }

    @Override
    public Department getByName(String departmentName) throws SQLException {
        return departmentORM.getByName(departmentName);
    }

    @Override
    public void delete(int departmentId) throws SQLException {
        departmentORM.beginTransaction();
        try {
            departmentORM.delete(departmentId);
            departmentORM.commitTransaction();
        } catch (SQLException e) {
            departmentORM.rollbackTransaction();
            throw e;
        }
    }

    @Override
    public List<Department> getAll() throws SQLException {
        return departmentORM.getAll();
    }

    public List<Employee> getEmployeesFromDepartment(int depId) throws SQLException {
        return departmentORM.getEmployeesFromDepartment(depId);
    }

    @Override
    public void update(Department department) throws SQLException {
        departmentORM.beginTransaction();
        try {
            departmentORM.update(department);
            departmentORM.commitTransaction();
        } catch (SQLException e) {
            departmentORM.rollbackTransaction();
            throw e;
        }
    }
    @Override
    public void close(){
        departmentORM.close();
    }
}

