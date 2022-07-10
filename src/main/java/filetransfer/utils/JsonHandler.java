package filetransfer.utils;

import com.google.gson.Gson;
import filetransfer.bean.DirTree;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class JsonHandler {
    private static Gson gson;
    static {
        gson = new Gson();
    }
    public static String toJson(DirTree dirTree){
        return gson.toJson(dirTree);
    };
    /**
     * get json from request, then convert to map
     */
    public static Map JsonToMapFromRequest(HttpServletRequest request) {
        String jsonString = null;
        try {
            jsonString = getJson(request);
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap();
        }
        Gson gson = new Gson();
        return gson.fromJson(jsonString, Map.class);
    }

    public static String getJson(HttpServletRequest request) throws IOException {
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
}
