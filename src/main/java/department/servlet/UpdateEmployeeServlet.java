package department.servlet;

import department.data.model.Employee;
import department.service.CompanyService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/updateEmployee")
public class UpdateEmployeeServlet extends HttpServlet {

    private final CompanyService service = new CompanyService();

    public UpdateEmployeeServlet() throws SQLException {
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();

        String employeeIdStr = req.getParameter("employeeId");

        if (employeeIdStr != null) {
            int employeeId = Integer.parseInt(employeeIdStr);

            try {
                Employee employee = service.getEmployee(employeeId);

                printWriter.write("<html><body>");
                printWriter.write("<h1>Update Employee</h1>");
                printWriter.write("<a href='/employee'>Back</a><br><br>");
                printWriter.write("<form method='post'>");
                printWriter.write("Name: <input type='text' name='name' value='" + employee.getName() + "'><br>");
                printWriter.write("Age: <input type='number' name='age' value='" + employee.getAge() + "'><br>");
                printWriter.write("Salary: <input type='text' name='salary' value='" + employee.getSalary() + "'><br>");
                printWriter.write("Department ID: <input type='number' name='departmentId' value='" + employee.getDepartmentId() + "'><br>");
                printWriter.write("<input type='hidden' name='employeeId' value='" + employeeId + "'>");
                printWriter.write("<input type='submit' value='OK'>");
                printWriter.write("</form>");
                printWriter.write("</body></html>");
            } catch (SQLException e) {
                e.printStackTrace();
                printWriter.write("Error occurred while retrieving employee data.");
            }
        } else {
            printWriter.write("Employee ID not provided.");
        }

        printWriter.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int employeeId = Integer.parseInt(request.getParameter("employeeId"));
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        double salary = Double.parseDouble(request.getParameter("salary"));
        int departmentId = Integer.parseInt(request.getParameter("departmentId"));
        try {
            service.editEmployee(new Employee(employeeId, name, age, salary, departmentId));
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Error occurred while updating employee data.");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/employee");
    }
}
