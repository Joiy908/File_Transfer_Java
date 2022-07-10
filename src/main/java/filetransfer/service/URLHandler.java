package filetransfer.service;

import javax.servlet.ServletContext;

public class URLHandler {
    /**
     * convert front-end relative filePath like "./files/file1.txt"
     * to absolute path.
     */
    public String getAbsFilePath(String urlFilePath, ServletContext servletContext) {
        // get local root dir and front end root dir
        String rootPathLocal = servletContext.getInitParameter("ROOT_PATH_LOCAL");
        String rootPathFrontEnd = servletContext.getInitParameter("ROOT_PATH_FRONT_END");

        // 3. merge them to an absolute path
        String path = urlFilePath.replace(rootPathFrontEnd, "");
        return rootPathLocal + path;
    }

}
