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

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();

        if (req.getParameter("action") != null && req.getParameter("action").equals("new")) {
            // Отображение формы для создания нового сотрудника
            printWriter.write("<html><body>");
            printWriter.write("<h1>Create New Employee</h1>");
            printWriter.write("<form method='post'>");
            printWriter.write("Name: <input type='text' name='name'><br>");
            printWriter.write("Age: <input type='number' name='age'><br>");
            printWriter.write("Salary: <input type='text' name='salary'><br>");
            printWriter.write("Department: <input type='text' name='department'><br>");
            printWriter.write("<input type='submit' value='Create'>");
            printWriter.write("</form>");
            printWriter.write("</body></html>");
        } else {
            // Вывод списка сотрудников
            List<Employee> employees;
            try {
                employees = service.getAllEmployees();
            } catch (SQLException e) {
                e.printStackTrace();
                printWriter.write("Error occurred while retrieving employees.");
                printWriter.close();
                return;
            }

            printWriter.write("<html><body><h1>Employee List</h1>");
            printWriter.write("<a href='/employee?action=new'>New Employee</a><br><br>");
            printWriter.write("<ul>");
            for (Employee employee : employees) {
                printWriter.write("<li>Name: " + employee.getName() + ", Age: " + employee.getAge() + ", Salary: " + employee.getSalary() + "</li>");
            }
            printWriter.write("</ul></body></html>");
        }

        printWriter.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получение данных о новом сотруднике из параметров запроса
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        double salary = Double.parseDouble(request.getParameter("salary"));
        String department = request.getParameter("department");

        // Создание нового сотрудника


        // Сохранение нового сотрудника
        try {
            service.addEmployeeToDepartment(department, name, age, salary);
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Error occurred while creating employee.");
            return;
        }

        // Перенаправление на страницу списка сотрудников после создания
        response.sendRedirect(request.getContextPath() + "/employee");
    }

    @Override
    public void destroy() {
        // Закрываем ресурсы при уничтожении сервлета
        //service.exit();
        super.destroy();
    }
}

