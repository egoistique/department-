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

        if ("delete".equals(req.getParameter("action"))) {
            String departmentIdStr = req.getParameter("departmentId");
            if (departmentIdStr != null) {
                try {
                    int departmentId = Integer.parseInt(departmentIdStr);
                    service.deleteDepartment(departmentId);
                    resp.sendRedirect(req.getContextPath() + "/department");
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
            printWriter.write("<h1>Create New Department</h1>");
            printWriter.write("<form method='post'>");
            printWriter.write("Name: <input type='text' name='name'><br>");
            printWriter.write("<input type='submit' value='Create'>");
            printWriter.write("</form>");
            printWriter.write("</body></html>");
        } else {
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
                printWriter.write("<a href='/department?action=delete&departmentId=" + department.getId() + "'>Delete</a>");
                printWriter.write("<a href='/updateDepartment?departmentId=" + department.getId() + "'>Update</a>");
            }
            printWriter.write("</body></html>");
        }

        printWriter.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        try {
            service.addDepartment(name);
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Error occurred while creating department.");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/department");
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String departmentIdStr = request.getParameter("departmentId");

        if (departmentIdStr != null) {
            try {
                int departmentId = Integer.parseInt(departmentIdStr);
                service.deleteDepartment(departmentId);
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().write("Error occurred while deleting department.");
                return;
            }

            response.sendRedirect(request.getContextPath() + "/department");
        } else {
            response.getWriter().write("No department ID provided.");
        }
    }

    @Override
    public void destroy() {
        service.exit();
        super.destroy();
    }
}

