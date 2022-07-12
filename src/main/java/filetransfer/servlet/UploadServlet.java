package filetransfer.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class UploadServlet extends HttpServlet {
    private String filePath;
    private File file ;
    // file uploaded threshold, unit: byte
    private long maxFileSize = 10*10240*1000; //10GB

    public void init( ){
        // Get the file location where it would be stored.
        filePath = getServletContext().getInitParameter("ROOT_PATH_LOCAL");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        if 'file' not in request.files:
            # flash('No file part')
            return 'No file part'
        f = request.files.get('file')
        dir_path = request.form.get('dirPath')
        if not is_allowed_file(f.filename):
            return f'upload fails, file type of {f.filename} is not permitted.'
        if not os.path.exists(dir_path):
            return f"upload fails, {dir_path} don't exist.'"
        f.save(os.path.join(dir_path, f.filename))  # 保存文件
        return 'upload successfully!'
         */
        // Check that we have a file upload request
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // Location to save data that is larger than maxMemSize=10MB.
        factory.setRepository(new File("D:\\temp"));

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // maximum file size to be uploaded.
        upload.setSizeMax( maxFileSize );

        try {
            // Parse the request to get file items.
            List fileItems = upload.parseRequest(request);

            // Process the uploaded file items
            Iterator i = fileItems.iterator();

            while ( i.hasNext () ) {
                FileItem fi = (FileItem)i.next();
                if ( !fi.isFormField () ) {
                    // Get the uploaded file parameters
                    String fieldName = fi.getFieldName();
                    String fileName = fi.getName();
                    String contentType = fi.getContentType();
                    boolean isInMemory = fi.isInMemory();
                    long sizeInBytes = fi.getSize();

                    // Write the file
                    file = new File( filePath + "/" + fileName) ;
//                    if( fileName.lastIndexOf("\\") >= 0 ) {
//                        file = new File( filePath + fileName.substring( fileName.lastIndexOf("\\"))) ;
//                    } else {
//                        file = new File( filePath + fileName.substring(fileName.lastIndexOf("\\")+1)) ;
//                    }
                    fi.write( file );
                    response.getWriter().write("Uploaded Filename: " + fileName);
//                    out.println("Uploaded Filename: " + fileName + "<br>");
                }
            }
//            out.println("</body>");
//            out.println("</html>");
        } catch(Exception ex) {
            System.out.println(ex);
        }
    }
}
