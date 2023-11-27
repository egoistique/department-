package department.connection;

import department.di.annotation.Inject;
import department.di.annotation.Injectable;
import department.di.factory.BeanFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Injectable
public class DatabaseConnectionManager {
//    @Inject
//    ConnectionConfiguration configuration = BeanFactory.getInstance().getBean(ConnectionConfiguration.class);
    ConnectionConfiguration configuration = new ConnectionConfiguration();
    public Connection openConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL Driver not found", e);
        }

        String jdbcUrl = configuration.getJdbcUrl();
        String username = configuration.getUsername();
        String password = configuration.getPassword();

        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    public void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("manager closed connection");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
