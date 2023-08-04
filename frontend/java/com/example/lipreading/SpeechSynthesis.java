package com.example.lipreading;

import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.*;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class SpeechSynthesis {
    // This example requires environment variables named "SPEECH_KEY" and "SPEECH_REGION"
    private static String speechKey = "dca17b35110c4af1bcda001a3baa2f8b";
    private static String speechRegion = "centralindia";

    public static void getTranslatedAudio(String text, String audioLanguage,String emotion) {
        try{
            SpeechConfig speechConfig = SpeechConfig.fromSubscription(speechKey, speechRegion);

            speechConfig.setSpeechSynthesisVoiceName("en-US-JennyNeural");//change would happen here

            SpeechSynthesizer speechSynthesizer = new SpeechSynthesizer(speechConfig);

            // Get text from the console and synthesize to the default speaker.
            System.out.println("TEXT TO TRANSLATE : "+text);
            if (text.isEmpty())
            {
                return;
            }

            SpeechSynthesisResult speechSynthesisResult = speechSynthesizer.SpeakTextAsync(text).get();
            text = "Hi, my name is Dhruv";
            if (speechSynthesisResult.getReason() == ResultReason.SynthesizingAudioCompleted) {
                System.out.println("Speech synthesized to speaker for text [" + text + "]");
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

        }catch(Exception e){
            System.out.println("THE PROBLEM HERE IS");
            e.printStackTrace();
        }

    }
}