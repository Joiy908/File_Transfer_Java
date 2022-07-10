package filetransfer.servlet;

import filetransfer.service.URLHandler;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

public class DownloadFileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* get filePath and return file */
        String filePath = request.getParameter("filePath");
        URLHandler urlHandler = new URLHandler();
        String absFilePath = urlHandler.getAbsFilePath(filePath, request.getServletContext());
        File f = new File(absFilePath);
        if (!f.isFile()) {
            response.getWriter().write("File do not exist, please check the path");
        }
        // get fileName and encode
        String fileName = URLEncoder.encode(f.getName(), "utf-8");
        // get and set mimeType
        String mimeType = getServletContext().getMimeType(fileName);
        response.setContentType(mimeType);
        // set file name, attachment tells the browser download the file, not to show it
        response.addHeader("Content-Disposition", "attachment; filename="+fileName);

        // read and write file to response
        try (InputStream is = new FileInputStream(absFilePath);
             ServletOutputStream os = response.getOutputStream();) {
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1){
                os.write(buffer,0,len);
            }
        }

    }
}
