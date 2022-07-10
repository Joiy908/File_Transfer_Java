import org.junit.jupiter.api.Test;

public class testMakeFilePathServlet {
    @Test
    void testGetFileName() {
        String path1 = "D:\\filetransfer.servlet\\file.txt";
        String path2 = "D:/src/file.txt";
        String ex = "file.txt";
//        Assertions.assertEquals(getFilename(path1), ex);
//        Assertions.assertEquals(getFilename(path2), ex);

    }

}
