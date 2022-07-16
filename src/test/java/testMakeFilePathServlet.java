import org.junit.jupiter.api.Test;

import java.io.File;

public class testMakeFilePathServlet {
    @Test
    void testGetFileName() {
        String path1 = "D:\\filetransfer.servlet\\file.txt";
        String path2 = "D:/src/file.txt";
        String ex = "file.txt";
//        Assertions.assertEquals(getFilename(path1), ex);
//        Assertions.assertEquals(getFilename(path2), ex);

    }

    @Test
    void test1() {
        File file = new File("D:/src/Py_projects/projects_2022/file_transfer/files");
        System.out.println("file = " + file);
        System.out.println(file.isDirectory());
    }

}
