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

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();

        if (req.getParameter("action") != null && req.getParameter("action").equals("new")) {
            // Отображение формы для создания нового отдела
            printWriter.write("<html><body>");
            printWriter.write("<h1>Create New Department</h1>");
            printWriter.write("<form method='post'>");
            printWriter.write("Name: <input type='text' name='name'><br>");
            printWriter.write("<input type='submit' value='Create'>");
            printWriter.write("</form>");
            printWriter.write("</body></html>");
        } else {
            // Вывод списка отделов
            List<Department> departments;
            try {
                departments = service.getAllDepartments();
            } catch (SQLException e) {
                e.printStackTrace();
                printWriter.write("Error occurred while retrieving departments.");
                printWriter.close();
                return;
            }

            printWriter.write("<html><body><h1>Departments</h1>");
            printWriter.write("<a href='/department?action=new'>New Department</a><br><br>");
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
        }

        printWriter.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получение данных о новом отделе из параметров запроса
        String name = request.getParameter("name");

        // Создание нового отдела


        // Сохранение нового отдела
        try {
            service.addDepartment(name);
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Error occurred while creating department.");
            return;
        }

        // Перенаправление на страницу списка отделов после создания
        response.sendRedirect(request.getContextPath() + "/department");
    }


    @Override
    public void destroy() {
        // Закрываем ресурсы при уничтожении сервлета
        //service.exit();
        super.destroy();
    }
}

