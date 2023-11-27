package department.view;
import java.sql.SQLException;

public interface View {

    void displayMenu() throws SQLException;

    void addEmployee() throws SQLException;

    void deleteEmployee() throws SQLException;

    void addDepartment() throws SQLException;

    void deleteDepartment() throws SQLException;

    void viewDepartments() throws SQLException;
}