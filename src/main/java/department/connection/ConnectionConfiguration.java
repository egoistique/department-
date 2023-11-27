package department.connection;

import department.di.annotation.Injectable;

@Injectable
public class ConnectionConfiguration {
    private String jdbcUrl;
    private String username;
    private String password;

    public ConnectionConfiguration() {
        this.jdbcUrl = "jdbc:postgresql://localhost:5432/department";
        this.username = "postgres";
        this.password = "postgres";
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

