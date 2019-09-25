package cui.student.domain;

import cui.student.entity.Config;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        Properties pro =new Properties();
        Config config=new Config();
        pro.load(Main.class.getClassLoader().getResourceAsStream("properties.properties"));
        config.setUrl(pro.getProperty("url"));
        config.setMark(pro.getProperty("mark"));
        config.setReplace(pro.getProperty("replace"));
        config.setCount(Integer.valueOf(pro.getProperty("count")));
        config.setLocation(new File(pro.getProperty("location")));

    }
    public Set<String> requestUrlByCount(String url,int count){
        try {
            URL urlMain=new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlMain.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            for (int i=0;i<count ;i++){}
                connection.connect();
                if (connection.getResponseCode()==200){

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
