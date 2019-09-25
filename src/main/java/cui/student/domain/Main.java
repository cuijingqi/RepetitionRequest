package cui.student.domain;

import cui.student.entity.Config;

import java.io.*;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class Main {
    public static void main(String[] args)throws IOException {
        Main main=new Main();
        StringBuffer sb=new StringBuffer();
        Config config = main.initApp();
        Set<String> replaces = main.repetitionMarkByFile(config.getUrl(), config.getMark(), config.getReplace());
        Iterator<String> iterator = replaces.iterator();
        while (iterator.hasNext()){
            Set<String> results = main.requestUrlByCount(iterator.next(), config.getCount());
            Iterator<String> resultsIterator = results.iterator();
            while (resultsIterator.hasNext()){
                sb.append(resultsIterator.next());
                sb.append("/r/n");
            }
        }
        BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(config.getLocation()+"/response.txt",true));
        bufferedWriter.write(sb.toString());
    }
    public Config initApp()throws IOException {
        Properties pro =new Properties();
        Config config=new Config();
        pro.load(Main.class.getClassLoader().getResourceAsStream("properties.properties"));
        config.setUrl(pro.getProperty("url"));
        config.setMark(pro.getProperty("mark"));
        config.setReplace(new File(pro.getProperty("replace")));
        config.setCount(Integer.valueOf(pro.getProperty("count")));
        config.setLocation(new File(pro.getProperty("location")));
        return config;
    }
    public  Set<String> repetitionMarkByFile(String url,String mark,File replace){
        Set<String> result=new HashSet();
        Set<String> replaces=new HashSet();
        try {
            if (replace.isDirectory()){
                FileReader fileReader =new FileReader(replace);
                BufferedReader bufferedReader =new BufferedReader(fileReader);
                do{
                    replaces.add(bufferedReader.readLine());
                }while (bufferedReader.read()!=-1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Iterator<String> iterator = replaces.iterator();
        while (iterator.hasNext()){
            result.add(url.replace(mark,iterator.next()));
        }
        return result;
    }
    public Set<String> requestUrlByCount(String url,int count){
        Set<String> results=new HashSet();
        results.add(url);
        while (count>0) {
            int finalCount = count;
            new Thread(()-> {
                try {
                    URL urlMain = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) urlMain.openConnection();
                    connection.setRequestProperty("accept", "*/*");
                    connection.setRequestProperty("connection", "Keep-Alive");
                    connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                    connection.connect();
                    if (connection.getResponseCode() == 200) {
                        results.add(finalCount +":"+connection.getResponseMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
            count--;
        }
        return results;
    }
}
