package com.example.lipreading;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;
public class ProcessLipReading implements Callable<String>{
    String fileAddress;
    public  ProcessLipReading(String fileAddress){
        this.fileAddress = fileAddress;
    }

    @Override
    public String call() throws Exception {
        String detectText = "";
        String urlString = "";
        try {
            URL url;
            url = new URL(urlString+"?="+fileAddress);
            URLConnection urlConnection=url.openConnection();
            urlConnection.connect();
            BufferedReader br=new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line=br.readLine();
        } catch (MalformedURLException me){

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return detectText;
    }
}
