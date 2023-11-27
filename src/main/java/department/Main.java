package department;

import department.connection.DatabaseConnectionManager;
import department.di.annotation.Inject;
import department.di.factory.BeanFactory;
import department.view.ConsoleView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    @Inject
    static DatabaseConnectionManager connectionManager = BeanFactory.getInstance().getBean(DatabaseConnectionManager.class);


    public static void main(String[] args) throws SQLException {
//        Connection connection = null;
//
//        try {
//            connection = connectionManager.openConnection();
//            System.out.println("Соединение установлено.");
//
//            try (Statement statement = connection.createStatement()) {
//                String createTablesSQL = "CREATE TABLE IF NOT EXISTS department (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255));" +
//                        "CREATE TABLE IF NOT EXISTS employee (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), age INT, salary DOUBLE, department_id INT, FOREIGN KEY (department_id) REFERENCES department(id));";
//                statement.execute(createTablesSQL);
//                System.out.println("Таблицы созданы.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            connectionManager.closeConnection(connection);
//        }

        ConsoleView view = new ConsoleView();
        view.displayMenu();
    }
}
