package department.data.dao;

import department.data.model.Employee;
import department.di.annotation.Inject;
import department.di.annotation.Injectable;
import department.di.factory.BeanFactory;
import department.orm.DepartmentORM;
import department.orm.EmployeeORM;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Injectable
public class EmployeeDAO implements DAO<Employee>{
//    @Inject
//    private final EmployeeORM employeeORM = BeanFactory.getInstance().getBean(EmployeeORM.class);
    private final EmployeeORM employeeORM = new EmployeeORM();

    public EmployeeDAO() throws SQLException {
    }

    public void create(int depId, String name, int age, double salary) throws SQLException {
        employeeORM.beginTransaction();
        try {
            employeeORM.create(depId, name, age, salary);
            employeeORM.commitTransaction();
        } catch (SQLException e) {
            employeeORM.rollbackTransaction();
            throw e;
        }
    }

    @Override
    public Employee getById(int employeeId) throws SQLException {
        return employeeORM.getById(employeeId);
    }

    @Override
    public void update(Employee employee) throws SQLException {
        employeeORM.beginTransaction();
        try {
            employeeORM.update(employee);
            employeeORM.commitTransaction();
        } catch (SQLException e) {
            employeeORM.rollbackTransaction();
            throw e;
        }
    }

    @Override
    public void delete(int employeeId) throws SQLException {
        employeeORM.beginTransaction();
        try {
            employeeORM.delete(employeeId);
            employeeORM.commitTransaction();
        } catch (SQLException e) {
            employeeORM.rollbackTransaction();
            throw e;
        }
    }

    @Override
    public List<Employee> getAll() throws SQLException {
        return employeeORM.getAll();
    }

    @Override
    public Employee getByName(String name) {
        return employeeORM.getByName(name);
    }

    @Override
    public void close(){
        employeeORM.close();
    }
}

