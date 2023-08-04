package com.example.lipreading;

import java.util.concurrent.Callable;
public class TranslateAudioWithEmotion implements Callable<String>{
    String textInput;
    String emotionDetected;
    public TranslateAudioWithEmotion(String textInput,String emotionDetected){
        this.textInput = textInput;
        this.emotionDetected = emotionDetected;
    }

    @Override
    public String call() throws Exception {
        SpeechSynthesis syn = new SpeechSynthesis();
        System.out.println("I AM HEREEEEEEEE");
        syn.getTranslatedAudio("HELLO THERE, MY NAME IS DHRUV","a","V");

//        String urlString = "";
//        try {
//            URL url;
//            url = new URL(urlString+"?="+textInput+"&="+emotionDetected);
//            URLConnection urlConnection=url.openConnection();
//            urlConnection.connect();
//            BufferedReader br=new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//            String line=br.readLine();
//        } catch (MalformedURLException me){
//
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
        return "";
    }
}
