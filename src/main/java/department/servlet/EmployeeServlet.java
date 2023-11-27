package department.servlet;

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

        if ("delete".equals(req.getParameter("action"))) {
            String employeeIdStr = req.getParameter("employeeId");
            if (employeeIdStr != null) {
                try {
                    int employeeId = Integer.parseInt(employeeIdStr);
                    service.removeEmployee(employeeId);
                    resp.sendRedirect(req.getContextPath() + "/employee");
                    return;
                } catch (SQLException e) {
                    e.printStackTrace();
                    printWriter.write("Error occurred while deleting employee.");
                    printWriter.close();
                    return;
                }
            }
        }

        if (req.getParameter("action") != null && req.getParameter("action").equals("new")) {
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
            printWriter.write("<html><head><title>Employee List</title></head><body>");
            printWriter.write("<a href='/'>Back</a><br><br>");

            printWriter.write("<a href='/employee?action=new'>New Employee</a><br><br>");
            printWriter.write("<ul>");
            for (Employee employee : employees) {
                printWriter.write("<li>Name: " + employee.getName() + ", Age: " + employee.getAge() + ", Salary: " + employee.getSalary());
                printWriter.write("<a href='/employee?action=delete&employeeId=" + employee.getId() + "'>Delete</a>");
                printWriter.write("<a href='/updateEmployee?employeeId=" + employee.getId() + "'>Update</a>");

                printWriter.write("</li>");
            }

            printWriter.write("</ul></body></html>");
        }
        printWriter.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        double salary = Double.parseDouble(request.getParameter("salary"));
        String department = request.getParameter("department");

        try {
            service.addEmployeeToDepartment(department, name, age, salary);
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Error occurred while creating employee.");
            return;
        }
        response.sendRedirect(request.getContextPath() + "/employee");
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeIdStr = request.getParameter("employeeId");

        if (employeeIdStr != null) {
            try {
                int employeeId = Integer.parseInt(employeeIdStr);
                service.removeEmployee(employeeId);
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().write("Error occurred while deleting employee.");
                return;
            }

            response.sendRedirect(request.getContextPath() + "/employee");
        } else {
            response.getWriter().write("No employee ID provided.");
        }
    }


    @Override
    public void destroy() {
        service.exit();
        super.destroy();
    }
}

