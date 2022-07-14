package filetransfer.service;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class UploadFileService {
    private static final int MAX_MEM_SIZE = 5120; //5*1024 = 5 KB
    private static String filePath;
    private static File file;
    // file uploaded threshold, unit: byte
    private static final long MAX_FILE_SIZE = 10*10240*1000; //10GB

    public static void handleUploadedFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
//        response.setContentType("text/html;charset=UTF-8");
        // Get the file location where it would be stored.
        filePath = request.getServletContext().getInitParameter("ROOT_PATH_LOCAL");
        // Check that we have a file upload request
        response.setContentType("text/html");
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
            response.getWriter().write("No file part!");
        }

        DiskFileItemFactory factory = new DiskFileItemFactory();

        // maximum size that will be stored in memory
        factory.setSizeThreshold(MAX_MEM_SIZE);

        // Location to save data that is larger than maxMemSize=10MB.
        factory.setRepository(new File(filePath));

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // maximum file size to be uploaded.
        upload.setSizeMax(MAX_FILE_SIZE);

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
                }
            }
        } catch(Exception ex) {
            System.out.println(ex);
        }
    }
}
