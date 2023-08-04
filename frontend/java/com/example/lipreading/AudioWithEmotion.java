package com.example.lipreading;

import android.util.Log;
import android.widget.Toast;

import com.microsoft.cognitiveservices.speech.AudioDataStream;
import com.microsoft.cognitiveservices.speech.CancellationReason;
import com.microsoft.cognitiveservices.speech.PropertyId;
import com.microsoft.cognitiveservices.speech.ResultReason;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisCancellationDetails;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisResult;
import com.microsoft.cognitiveservices.speech.SpeechSynthesizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class AudioWithEmotion {
    String TAG="ChoosingOutput";
    public AudioWithEmotion(String textInput,String emotion,String audioLanguage) {
//        SpeechConfig speechConfig = SpeechConfig.fromSubscription("dca17b35110c4af1bcda001a3baa2f8b", "centralindia");
//        SpeechSynthesizer synthesizer = new SpeechSynthesizer(speechConfig, null);
//
//        String ssml = xmlToString("ssml.xml");
//        SpeechSynthesisResult result = synthesizer.SpeakSsml(ssml);
//            AudioDataStream stream = AudioDataStream.fromResult(result);
//        System.out.println("RESULT: "+result.toString());
//        System.out.println("STREAM: "+stream.toString());
//        stream.saveToWavFileAsync("./op1.wav");
        try{



        SpeechConfig speechConfig = SpeechConfig.fromSubscription("d773e27c410d449b8a2d2cc3465b4cbc", "centralindia");

        // Required for WordBoundary event sentences.
        speechConfig.setProperty(PropertyId.SpeechServiceResponse_RequestSentenceBoundary, "true");

        String speechSynthesisVoiceName = "";
        if("bn-IN".equals(audioLanguage)){
            speechSynthesisVoiceName = "bn-IN-BashkarNeural";
        }else if("en-IN".equals(audioLanguage)){
            speechSynthesisVoiceName = "en-IN-PrabhatNeural";
        }else if("gu-IN".equals(audioLanguage)){
            speechSynthesisVoiceName = "gu-IN-NiranjanNeural";
        }else if("hi-IN".equals(audioLanguage)){
            speechSynthesisVoiceName = "hi-IN-MadhurNeural";
        }else if("kn-IN".equals(audioLanguage)){
            speechSynthesisVoiceName = "kn-IN-GaganNeural";
        }else if("ml-IN".equals(audioLanguage)){
            speechSynthesisVoiceName = "ml-IN-MidhunNeural";
        }else if("mr-IN".equals(audioLanguage)){
            speechSynthesisVoiceName = "mr-IN-ManoharNeural";
        }else if("ta-IN".equals(audioLanguage)){
            speechSynthesisVoiceName = "ta-IN-ValluvarNeural";
        }else if("te-IN".equals(audioLanguage)){
            speechSynthesisVoiceName = "te-IN-MohanNeural";
        }else if("ur-IN".equals(audioLanguage)){
            speechSynthesisVoiceName = "ur-IN-SalmanNeural";
        }

        String ssml = String.format("<speak version='1.0' xml:lang='%s' xmlns='http://www.w3.org/2001/10/synthesis' xmlns:mstts='http://www.w3.org/2001/mstts'>",audioLanguage)
                .concat(String.format("<voice name='%s'>", speechSynthesisVoiceName))
                .concat(String.format("<mstts:express-as style=\"%s\" styledegree=\"2\">",emotion))
                .concat(String.format("%s",textInput))
                .concat("</mstts:express-as>")
                .concat("</voice>")
                .concat("</speak>");

        SpeechSynthesizer speechSynthesizer = new SpeechSynthesizer(speechConfig);
        {
            // Synthesize the SSML
            System.out.println("SSML to synthesize:");
            System.out.println(ssml);
            SpeechSynthesisResult speechSynthesisResult = speechSynthesizer.SpeakSsmlAsync(ssml).get();

            if (speechSynthesisResult.getReason() == ResultReason.SynthesizingAudioCompleted) {
                System.out.println("SynthesizingAudioCompleted result");
            }
            else if (speechSynthesisResult.getReason() == ResultReason.Canceled) {
                SpeechSynthesisCancellationDetails cancellation = SpeechSynthesisCancellationDetails.fromResult(speechSynthesisResult);
                System.out.println("CANCELED: Reason=" + cancellation.getReason());

                if (cancellation.getReason() == CancellationReason.Error) {
                    System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                    System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
                    System.out.println("CANCELED: Did you set the speech resource key and region values?");
                }
            }
        }
        speechSynthesizer.close();

        }catch( ExecutionException | InterruptedException e){
            e.printStackTrace();
           Log.e(TAG,e.getMessage());
            Log.d(TAG,e.getMessage());
        }
    }

    private static String xmlToString(String filePath) {
        File file = new File(filePath);
        StringBuilder fileContents = new StringBuilder((int)file.length());

        try (Scanner scanner = new Scanner(file)) {
            while(scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine() + System.lineSeparator());
            }
            return fileContents.toString().trim();
        } catch (FileNotFoundException ex) {
            return "File not found.";
        }
    }


}
