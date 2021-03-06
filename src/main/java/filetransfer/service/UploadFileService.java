package filetransfer.service;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class UploadFileService {
    private static final int MAX_MEM_SIZE = 5120; //5*1024 = 5 KB
    private static String filePath;
    // file uploaded threshold, unit: byte
    private static final long MAX_FILE_SIZE = 10737418240L; //10GB


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
        // Get the file location where it would be stored.
        String dirPath = request.getParameter("dirPath");
        String absDirPath = new URLHandler().getAbsFilePath(
                dirPath, request.getServletContext());
        if (!new File(absDirPath).isDirectory()) {
            response.getWriter().write("dirPath is not exist.");
            return;
        }
        filePath = absDirPath;

        // Check that we have a file upload request
        response.setContentType("text/html");
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
            response.getWriter().write("No file part!");
            return;
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
            List<FileItem> fileItems = upload.parseRequest(request);

            // Process the uploaded file items
            for (FileItem f : fileItems) {
                if (f.isFormField()) {
                    response.getWriter().write("data structure err.");
                    return;
                }
                // Get the uploaded file parameters
                String fieldName = f.getFieldName();
                String fileName = f.getName();
                String contentType = f.getContentType();
                boolean isInMemory = f.isInMemory();
                long sizeInBytes = f.getSize();
                if (sizeInBytes > MAX_FILE_SIZE) {
                    response.getWriter().write("Upload fails, file is too large!");
                    return;
                }
                // Write the file
                File file = new File(filePath + "/" + fileName);
                f.write(file);

                response.getWriter().write("Uploaded successfully!");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}
