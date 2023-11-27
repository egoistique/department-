package department.orm;

import department.connection.DatabaseConnectionManager;
import department.data.model.Employee;
import department.di.annotation.Inject;
import department.di.factory.BeanFactory;
import department.exception.ORMException;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeORM {
    private Connection connection;
//    @Inject
//    DatabaseConnectionManager connectionManager = BeanFactory.getInstance().getBean(DatabaseConnectionManager.class);
    DatabaseConnectionManager connectionManager = new DatabaseConnectionManager();
    public EmployeeORM() throws SQLException {
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
    public void create(int depId, String name, int age, double salary) throws SQLException {
        String sql = "INSERT INTO employee (name, age, salary, department_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setInt(2, age);
            statement.setDouble(3, salary);
            statement.setInt(4, depId);
            statement.executeUpdate();
        }
    }


    public Employee getById(int employeeId) throws SQLException {
        String sql = "SELECT * FROM employee WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, employeeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Employee(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("age"),
                            resultSet.getDouble("salary"),
                            resultSet.getInt("department_id")
                    );
                } else {
                    return null;
                }
            }
        }
    }


    public void update(Employee employee) throws SQLException {
        String sql = "UPDATE employee SET name = ?, age = ?, salary = ?, department_id = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employee.getName());
            statement.setInt(2, employee.getAge());
            statement.setDouble(3, employee.getSalary());
            statement.setInt(4, employee.getDepartmentId());
            statement.setInt(5, employee.getId());
            statement.executeUpdate();
        }
    }

    public void delete(int employeeId) throws SQLException {
        String sql = "DELETE FROM employee WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, employeeId);
            statement.executeUpdate();
        }
    }

    public List<Employee> getAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employee";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                employees.add(new Employee(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        resultSet.getDouble("salary"),
                        resultSet.getInt("department_id")
                ));
            }
        }
        return employees;
    }

    public Employee getByName(String name){
        return null;
    }
}

