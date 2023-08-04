package com.example.lipreading;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;
public class TranslateText implements Callable<String>{
    String textInput;
    public  TranslateText(String textInput){
        this.textInput = textInput;
    }

    @Override
    public String call() throws Exception {
        String textTranslated = "";
        String urlString = "";
        try {
            URL url;
            url = new URL(urlString+"?="+textInput);
            URLConnection urlConnection=url.openConnection();
            urlConnection.connect();
            BufferedReader br=new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line=br.readLine();
        } catch (MalformedURLException me){

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return textTranslated;
    }
}
