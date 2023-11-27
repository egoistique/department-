package department.servlet;
import department.data.model.Department;
import department.service.CompanyService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/updateDepartment")
public class UpdateDepartmentServlet extends HttpServlet {
    private final CompanyService service = new CompanyService();

    public UpdateDepartmentServlet() throws SQLException {
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();

        String departmentIdStr = req.getParameter("departmentId");

        if (departmentIdStr != null) {
            int departmentId = Integer.parseInt(departmentIdStr);

            try {
                Department department = service.getDepartment(departmentId);

                printWriter.write("<html><body>");
                printWriter.write("<h1>Update Department</h1>");
                printWriter.write("<a href='/department'>Back</a><br><br>");
                printWriter.write("<form method='post'>");
                printWriter.write("Name: <input type='text' name='name' value='" + department.getName() + "'><br>");
                printWriter.write("<input type='hidden' name='departmentId' value='" + departmentId + "'>");
                printWriter.write("<input type='submit' value='OK'>");
                printWriter.write("</form>");
                printWriter.write("</body></html>");
            } catch (SQLException e) {
                e.printStackTrace();
                printWriter.write("Error occurred while retrieving department data.");
            }
        } else {
            printWriter.write("Department ID not provided.");
        }

        printWriter.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int departmentId = Integer.parseInt(request.getParameter("departmentId"));
        String name = request.getParameter("name");

        try {
            service.editDepartment(new Department(departmentId, name));
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Error occurred while updating department data.");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/department");
    }
}
