package cui.student.entity;

import java.io.File;

public class Config {
    String url;
    String mark;
    File replace;
    Integer count;
    File location;

    public Config() {
    }

    public Config(String url, String mark, File replace, Integer count, File location) {
        this.url = url;
        this.mark = mark;
        this.replace = replace;
        this.count = count;
        this.location = location;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public File getReplace() {
        return replace;
    }

    public void setReplace(File replace) {
        if (!replace.isDirectory()) {
            System.out.println("替换文件为NULL");
            System.exit(0);
        }
        this.replace=replace;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public File getLocation() {
        return location;
    }

    public void setLocation(File location) {
        try {
            if (!location.exists()) {
                location.mkdirs();
            }
            this.location = location;
        }catch (Exception e){
            this.location=new File(this.getClass().getClassLoader().getResource("/").getPath());
        }
    }

}
