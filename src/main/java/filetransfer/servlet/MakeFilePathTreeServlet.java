package filetransfer.servlet;

import filetransfer.bean.DirTree;
import filetransfer.service.MakeFilePathTreeService;
import filetransfer.utils.JsonHandler;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class MakeFilePathTreeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dirTreeJson = MakeFilePathTreeService.getDirTreeJson(request);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(dirTreeJson);
    }
}
