package com.example.lipreading;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;
public class DetectEmotion implements Callable<String>{
    String fileAddress;
    public  DetectEmotion(String fileAddress){
        this.fileAddress = fileAddress;
    }

    @Override
    public String call() throws Exception {
        String emotionDetected = "";
        String urlString = "";
        try {
            URL url;
            url = new URL(ColabLink.url+"/upload?file="+fileAddress);
            URLConnection urlConnection=url.openConnection();
            urlConnection.connect();
            BufferedReader br=new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line="";
            String finalStr="";
            System.out.println("PRINTED: "+line + " ");
            while(line!=null){
                if(!line.equals("")){
                    finalStr+=line;
                }
                line=br.readLine();
            }
            System.out.println("Out of here with : "+finalStr);
        } catch (MalformedURLException me){

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return emotionDetected;
    }
}
