package cn.org.mrliu.http.entity;

import java.io.File;
import java.io.Serializable;

/**
 * @author MrLiu
 * @date 2016/12/20
 */
public class FileOption implements Serializable {
    private static final long serialVersionUID = -6537750811371671536L;
    private String name;
    private File file;
    private String contentType;

    public FileOption() {
    }

    public FileOption(String name, File file) {
        this.name = name;
        this.file = file;
    }

    public FileOption(String name, File file, String contentType) {
        this.name = name;
        this.file = file;
        this.contentType = contentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "FileOption{" +
                "name='" + name + '\'' +
                ", file=" + file +
                ", contentType='" + contentType + '\'' +
                '}';
    }
}
