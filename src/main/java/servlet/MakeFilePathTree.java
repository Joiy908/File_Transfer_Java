package servlet;

import bean.DirTree;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;

public class MakeFilePathTree extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. generate absolute path of target dir
        String targetDirPath = getTargetDirPath(request);
        // 2. generate dirTree
        DirTree dirTree = null;
        try {
            dirTree = generateDirTree(targetDirPath,request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 3. turn dirPath to json
        Gson gson = new Gson();
        String s = gson.toJson(dirTree);
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(s);
    }

    static String getJson(HttpServletRequest request) throws IOException {
        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
        return body;
    }

    /**
     * generate absolute path from request
     */
    static String getTargetDirPath(HttpServletRequest request) throws IOException {
        // 1. get FrontRootDirPath
        Gson gson = new Gson();
        String jsonString = getJson(request);
        Map data = gson.fromJson(jsonString, Map.class);
        String UerFrontRootDirPath = (String) data.get("dirPath");

        // 2. get local root dir and front end root dir
        ServletContext servletContext = request.getServletContext();
        String rootPathLocal = servletContext.getInitParameter("ROOT_PATH_LOCAL");
        String rootPathFrontEnd = servletContext.getInitParameter("ROOT_PATH_FRONT_END");
        // 3. merge them to an absolute path
        String path = UerFrontRootDirPath.replace(rootPathFrontEnd, "");
        return rootPathLocal + path;
    }


    /**
     * generate DirTree whose currDirName is relative path beginning with "./file"
     * assume dirPath exists!
     */
    static DirTree generateDirTree(String dirPath,HttpServletRequest request) throws Exception {
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

}
