package testBean;

import bean.DirTree;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

public class TestBean {
    @Test
    void testDirTree() {
        String dirPath = "D:\\src\\Py_projects\\projects_2022\\file_transfer\\files\\test1";
        File currDir = new File(dirPath);
        File[] files = currDir.listFiles();
        ArrayList<String> subFolders = new ArrayList<>();
        ArrayList<String> subFiles = new ArrayList<>();
        for (File file : files) {
            if (file.isDirectory()) {
                subFolders.add(file.toString());
            }
            if (file.isFile()) {
                subFiles.add(file.toString());
            }
        }
        DirTree dirTree = new DirTree("dirPath", subFolders, subFiles);
        System.out.println(dirTree.getSubFileList());
    }
}
