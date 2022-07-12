package filetransfer.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class UploadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().write("hhwer");
        System.out.println("get a request");
    }
}
