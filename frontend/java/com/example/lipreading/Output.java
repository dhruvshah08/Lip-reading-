package com.example.lipreading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.net.URI;
import java.net.URISyntaxException;

public class Output extends AppCompatActivity {
    TextView txtTextOutput;
    VideoView viewVideo;
    ImageButton btnHome;
    String audioLanguage, textLanguage,output;
    String fileAddress,textOutput;
    Uri uri ;
    String translatedText, translatedAudioText,emotion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);
        txtTextOutput = (TextView) findViewById(R.id.txtTextOutput);
        viewVideo = (VideoView) findViewById(R.id.viewVideo);
        btnHome = (ImageButton) findViewById(R.id.btnHome);
        fileAddress = getIntent().getStringExtra("fileAddress");
        uri = Uri.parse(fileAddress);

        audioLanguage = getIntent().getStringExtra("audioLanguage");
        textLanguage = getIntent().getStringExtra("textLanguage");
        output = getIntent().getStringExtra("output");
        emotion = getIntent().getStringExtra("emotion");
        translatedText = getIntent().getStringExtra("translatedText");
        System.out.println("I GOT : "+translatedText);
        translatedAudioText = getIntent().getStringExtra("translatedAudioText");
//        Toast.makeText(Output.this,output+" " + audioLanguage + " " + textLanguage,Toast.LENGTH_LONG).show();


        if((output.equals("text") || output.equals("both"))){
            txtTextOutput.setText(translatedText);
        }
        if((output.equals("audio") || output.equals("both"))){

            //Detect emotion first

            //Audio Output
            LanguageAndCodes languageAndCodes = new LanguageAndCodes();
            String langCode = languageAndCodes.getLanguageCode(audioLanguage);
            AudioWithEmotion audioWithEmotion = new AudioWithEmotion(translatedAudioText,emotion,langCode);
        }

        String path = uri.getPath();
        String filePath = path.replace("/document/primary:", "/storage/emulated/0/");
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(viewVideo);
        viewVideo.setMediaController(mediaController);

        // Set the local file path programmatically
//                String filePath = "/sdcard/videos/sample.mp4";
//                Uri videoUri = Uri.parse(filePath);
        viewVideo.setVideoURI(uri);

        viewVideo.start();  // Start playing the video
//        Log.d(TAG,"The video path here is :" + filePath);




        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Output.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}