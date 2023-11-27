package department.servlet;

import department.data.dao.EmployeeDAO;
import department.data.model.Employee;
import department.di.annotation.Inject;
import department.di.factory.BeanFactory;
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

@WebServlet("/employee")
public class EmployeeServlet extends HttpServlet {

    private final CompanyService service = new CompanyService();

    public EmployeeServlet() throws SQLException {
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

        List<Employee> employees;
        try {
            employees = service.getAllEmployees();
        } catch (SQLException e) {
            // Обработка ошибки, если что-то пошло не так при получении списка
            e.printStackTrace();
            // Можно также отправить пользователю сообщение об ошибке
            printWriter.write("Error occurred while retrieving employees.");
            printWriter.close();
            return;
        }

        // Формируем HTML для вывода списка сотрудников
        printWriter.write("<html><body><h1>Employee List</h1><ul>");
        for (Employee employee : employees) {
            printWriter.write("<li>Name: " + employee.getName() + ", Age: " + employee.getAge() + ", Salary: " + employee.getSalary() + "</li>");
        }
        printWriter.write("</ul></body></html>");

        printWriter.close();
    }

    @Override
    public void destroy() {
        // Закрываем ресурсы при уничтожении сервлета
        //service.exit();
        super.destroy();
    }
}

