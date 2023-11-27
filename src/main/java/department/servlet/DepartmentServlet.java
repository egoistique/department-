package department.servlet;

import department.data.model.Department;
import department.data.model.Employee;
import department.service.CompanyService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/department")
public class DepartmentServlet extends HttpServlet {

    private final CompanyService service = new CompanyService();

    public DepartmentServlet() throws SQLException {
    }

    public void init(ServletConfig servletConfig) {
        try {
            super.init(servletConfig);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();

        List<Department> departments;
        try {
            departments = service.getAllDepartments();
        } catch (SQLException e) {
            e.printStackTrace();
            printWriter.write("Error occurred while retrieving departments.");
            printWriter.close();
            return;
        }

        // Формируем HTML для вывода списка отделов и сотрудников
        printWriter.write("<html><body><h1>Departments and Employees</h1>");
        for (Department department : departments) {
            printWriter.write("<h2>Name: " + department.getName() + "</h2>");
            try {
                printWriter.write("<p>Number of employees: " + service.getEmployeesFromDepartment(department.getId()).size() + "</p>");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Получаем список сотрудников отдела
            List<Employee> employees = null;
            try {
                employees = service.getEmployeesFromDepartment(department.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            printWriter.write("<p>Employees:</p><ul>");

            assert employees != null;
            for (Employee employee : employees) {
                printWriter.write("<li>Name: " + employee.getName() + ", Age: " + employee.getAge() + ", Salary: " + employee.getSalary() + "</li>");
            }
            printWriter.write("</ul>");
        }
        printWriter.write("</body></html>");

        printWriter.close();
    }


    @Override
    public void destroy() {
        // Закрываем ресурсы при уничтожении сервлета
        //service.exit();
        super.destroy();
    }
}

