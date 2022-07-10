package filetransfer.service;

import filetransfer.bean.DirTree;
import filetransfer.utils.JsonHandler;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class MakeFilePathTreeService {
    /**
     * generate absolute path from request
     */
    public static String getTargetDirPath(HttpServletRequest request) throws IOException {
        // 1. get FrontRootDirPath
        Map data = JsonHandler.JsonToMapFromRequest(request);
        String UerFrontRootDirPath = (String) data.get("dirPath");

        return new URLHandler().getAbsFilePath(UerFrontRootDirPath, request.getServletContext());
    }

    /**
     * generate DirTree whose currDirName is relative path beginning with "./file"
     * assume dirPath exists!
     */
    public static DirTree generateDirTree(String dirPath, HttpServletRequest request) throws Exception {
        File currDir = new File(dirPath);
        if (!currDir.isDirectory()) {
            throw new Exception("No such dir.");
        }
        File[] files = currDir.listFiles();
        ArrayList<String> subFolders = new ArrayList<>();
        ArrayList<String> subFiles = new ArrayList<>();
        for (File file : files) {
            if (file.isDirectory()) {
                subFolders.add(file.getName());
            }
            if (file.isFile()) {
                subFiles.add(file.getName());
            }
        }
        // 1 convert absPath to relative path
        // 1.1 get local root dir and front end root dir
        ServletContext servletContext = request.getServletContext();
        String rootPathLocal = servletContext.getInitParameter("ROOT_PATH_LOCAL");
        String rootPathFrontEnd = servletContext.getInitParameter("ROOT_PATH_FRONT_END");
        // 1.2 cut path and turn "\\" to "/"
        dirPath = dirPath.replace(rootPathLocal, "");
        dirPath = dirPath.replace("\\", "/");
        dirPath = rootPathFrontEnd + dirPath;
        return new DirTree(dirPath, subFolders, subFiles);
    }

    public static String getDirTreeJson(HttpServletRequest request) {
        // 1. generate absolute path of target dir
        String targetDirPath = null;
        try {
            targetDirPath = getTargetDirPath(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 2. generate dirTree
        DirTree dirTree = null;
        try {
            dirTree = generateDirTree(targetDirPath, request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 3. turn dirPath to json
        return JsonHandler.toJson(dirTree);
    }
}
