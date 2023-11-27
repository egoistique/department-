package department.orm;

import department.connection.DatabaseConnectionManager;
import department.data.model.Department;
import department.data.model.Employee;
import department.di.annotation.Inject;
import department.di.annotation.Injectable;
import department.di.factory.BeanFactory;
import department.exception.ORMException;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Injectable
public class DepartmentORM {
    private final Connection connection;
//    @Inject
//    DatabaseConnectionManager connectionManager = BeanFactory.getInstance().getBean(DatabaseConnectionManager.class);

    DatabaseConnectionManager connectionManager = new DatabaseConnectionManager();

    public DepartmentORM() throws SQLException {
        connection = connectionManager.openConnection();
    }

    public void beginTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    public void commitTransaction() throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    public void rollbackTransaction() {
        try {
            connection.rollback();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            ORMException ormException = new ORMException("Ошибка при откате транзакции");
            ormException.printMessage();
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connectionManager.closeConnection(connection);
                connection.close();
            }
        } catch (SQLException e) {
            ORMException ormException = new ORMException("Ошибка при закрытии соединения с базой данных");
            ormException.printMessage();
        }
    }

    public void create(String name) throws SQLException {
        String sql = "INSERT INTO department (name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.executeUpdate();
        }
    }

    public Department getById(int departmentId) throws SQLException {
        String sql = "SELECT * FROM department WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, departmentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Department(resultSet.getInt("id"), resultSet.getString("name"));
                } else {
                    return null;
                }
            }
        }
    }

    public Department getByName(String departmentName) throws SQLException {
        String sql = "SELECT * FROM department WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, departmentName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Department(resultSet.getInt("id"),resultSet.getString("name"));
                } else {
                    return null;
                }
            }
        }
    }

    public void delete(int departmentId) throws SQLException {
        String deleteEmployeesSql = "DELETE FROM Employee WHERE department_id = ?";
        try (PreparedStatement deleteEmployeesStatement = connection.prepareStatement(deleteEmployeesSql)) {
            deleteEmployeesStatement.setInt(1, departmentId);
            deleteEmployeesStatement.executeUpdate();
        }

        String deleteDepartmentSql = "DELETE FROM department WHERE id = ?";
        try (PreparedStatement deleteDepartmentStatement = connection.prepareStatement(deleteDepartmentSql)) {
            deleteDepartmentStatement.setInt(1, departmentId);
            deleteDepartmentStatement.executeUpdate();
        }
    }

    public List<Department> getAll() throws SQLException {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM department";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                departments.add(new Department(resultSet.getInt("id"), resultSet.getString("name")));
            }
        }
        return departments;
    }

    public List<Employee> getEmployeesFromDepartment(int depId) throws SQLException {
        List<Employee> employees = new ArrayList<>();

        String sql = "SELECT id, name, age, salary, department_id FROM Employee WHERE department_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, depId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int employeeId = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int age = resultSet.getInt("age");
                    double salary = resultSet.getDouble("salary");

                    Employee employee = new Employee(employeeId, name, age, salary, depId);
                    employees.add(employee);
                }
            }
        }

        return employees;
    }

    public void update(Department department) throws SQLException {
        String sql = "UPDATE department SET name = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, department.getName());
            statement.setInt(2, department.getId());
            statement.executeUpdate();
        }
    }
}
