package department.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//@WebServlet("/")
//public class IndexServlet extends HttpServlet {
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.sendRedirect(req.getContextPath() + "/employee");
//    }
//}
@WebServlet("/")
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html><body>");
        out.println("<h1>Welcome!</h1>");
        out.println("<form action=\"department\" method=\"get\">");
        out.println("<input type=\"submit\" value=\"Department\">");
        out.println("</form>");
        out.println("<form action=\"employee\" method=\"get\">");
        out.println("<input type=\"submit\" value=\"Employee\">");
        out.println("</form>");
        out.println("</body></html>");

        out.close();
    }
}

