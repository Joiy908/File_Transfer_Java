package filetransfer.bean;

import java.util.List;

public class DirTree {
    private String currentDirName;
    private List<String> subFolderList;
    private List<String> subFileList;

    public String getCurrentDirName() {
        return currentDirName;
    }

    public void setCurrentDirName(String currentDirName) {
        this.currentDirName = currentDirName;
    }

    public List<String> getSubFolderList() {
        return subFolderList;
    }

    public void setSubFolderList(List<String> subFolderList) {
        this.subFolderList = subFolderList;
    }

    public List<String> getSubFileList() {
        return subFileList;
    }

    public void setSubFileList(List<String> subFileList) {
        this.subFileList = subFileList;
    }

    public DirTree() {
    }

    public DirTree(String currentDirName, List<String> subFolderList, List<String> subFileList) {
        this.currentDirName = currentDirName;
        this.subFolderList = subFolderList;
        this.subFileList = subFileList;
    }

    @Override
    public String toString() {
        return "DirTree{" +
                "currentDirName='" + currentDirName + '\'' +
                ", subFolderList=" + subFolderList.toString() +
                ", subFileList=" + subFileList.toString() +
                '}';
    }
}
