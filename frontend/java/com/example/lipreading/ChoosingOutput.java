    package com.example.lipreading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;

public class ChoosingOutput extends AppCompatActivity {
    RadioButton rbtnTextOnly, rbtnAudioOnly, rbtnBoth;
    RadioGroup rbtnLangGrp;
    Spinner spnrAudioLang,spnrTextLang;
    Button btnContinue;
    String audioLanguage="Select Language", textLanguage="Select Language";
    String fileAddress;
    String output ="both";
    String emotion,textOutput;
    Translate translate;
    TextView txtText,txtEmotion;
    private boolean connected;

    String translatedText,translatedAudioText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosing_output);
        txtText = (TextView) findViewById(R.id.txtText);
        txtEmotion = (TextView) findViewById(R.id.txtEmotion);

        fileAddress = getIntent().getStringExtra("fileAddress");
        emotion = getIntent().getStringExtra("emotion");
        textOutput = getIntent().getStringExtra("textOutput");
        txtEmotion.setText("Emotion Detected : "+emotion);
        txtText.setText("Text Detected : "+textOutput);
        System.out.println("DHRUV "+emotion+" and "+textOutput);
//        emotion = "happy";
//        textOutput = "stop doing that?";
        rbtnTextOnly = (RadioButton) findViewById(R.id.rbtnTextOnly);
        rbtnAudioOnly = (RadioButton) findViewById(R.id.rbtnAudioOnly);
        rbtnLangGrp = (RadioGroup) findViewById(R.id.rbtnLangGrp);
        rbtnBoth = (RadioButton) findViewById(R.id.rbtnBoth);
        spnrAudioLang = (Spinner) findViewById(R.id.spnrAudioLang);
        spnrTextLang = (Spinner) findViewById(R.id.spnrTextLang);
        btnContinue = (Button) findViewById(R.id.btnContinue);

        rbtnTextOnly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                output = "text";
                spnrAudioLang.setEnabled(false);
                spnrTextLang.setEnabled(true);
            }
        });

        rbtnAudioOnly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                output = "audio";
                spnrAudioLang.setEnabled(true);
                spnrTextLang.setEnabled(false);
            }
        });

        rbtnBoth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                output = "both";
                spnrAudioLang.setEnabled(true);
                spnrTextLang.setEnabled(true);
            }
        });

        spnrAudioLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                audioLanguage = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spnrTextLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textLanguage = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((output.equals("audio") || output.equals("both"))&& audioLanguage.equals("Select Language")){
                    Toast.makeText(ChoosingOutput.this,"Please select Audio Language",Toast.LENGTH_SHORT).show();
                }
                else if((output.equals("text") || output.equals("both")) && textLanguage.equals("Select Language")){
                    Toast.makeText(ChoosingOutput.this,"Please select Text Language",Toast.LENGTH_SHORT).show();
                }
                else {
//                    AudioWithEmotion ad = new AudioWithEmotion();
//                    textOutput = "नमस्कार, मेरा नाम ध्रुव है";
//                    textOutput = "आप कैसे हैं";
                    //Thread to get translated text which will be printed
                    Thread textThread = new Thread(new Runnable() {
                        public void run() {
                            //ADD THREAD HERE TO TRANSLATE TO REQUIRED TEXT
                            LanguageAndCodes lang1 = new LanguageAndCodes();
                            String langCode = lang1.getLanguageCode(textLanguage);
                            langCode = langCode.substring(0, langCode.indexOf('-'));
                            System.out.println("LANGUAGE 2 : "+langCode);
                            TextTranslator txtTranslator = new TextTranslator();
                            translatedText = txtTranslator.execute(textOutput,langCode);
                            System.out.println("TRANSLATED TEXT : "+translatedText);
                        }
                    });
                    textThread.start();

                    try {
                        textThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

//                    Thread transliterateThread = new Thread(new Runnable() {
//                        public void run() {
//                            //ADD THREAD HERE TO TRANSLATE TO REQUIRED TEXT
//                            if (checkInternetConnection()) {
//
//                                //If there is internet connection, get translate service and start translation:
//                                getTranslateService();
//                                String txt = "My name is \""+textOutput+"\"";
//                                translate(txt);
//
//                            } else {
//                                //If not, display "no connection" warning:
//                                Toast.makeText(ChoosingOutput.this,"No Connection", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                    transliterateThread.start();
//                    try {
//                        transliterateThread.join();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }

                    //Thread to get translated text which will be said
                    Thread audioThread = new Thread(new Runnable() {
                        public void run() {
                            //ADD THREAD HERE TO TRANSLATE TO REQUIRED TEXT
                            LanguageAndCodes lang1 = new LanguageAndCodes();
                            String langCode = lang1.getLanguageCode(audioLanguage);
                            langCode = langCode.substring(0, langCode.indexOf('-'));
                            System.out.println("LANGUAGE 1 : "+langCode);
                            TextTranslator txtTranslator = new TextTranslator();
                            translatedAudioText = txtTranslator.execute(textOutput,langCode);
                        }
                    });
                    audioThread.start();


                    try {
                        audioThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(ChoosingOutput.this, translatedText, Toast.LENGTH_LONG).show();

                    Toast.makeText(ChoosingOutput.this, translatedAudioText, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(ChoosingOutput.this, Output.class);
                    i.putExtra("fileAddress", fileAddress);
                    i.putExtra("audioLanguage", audioLanguage);
                    i.putExtra("textLanguage", textLanguage);
                    i.putExtra("output", output);
                    i.putExtra("emotion", emotion);
                    i.putExtra("translatedText", translatedText);
                    i.putExtra("translatedAudioText", translatedAudioText);
                    startActivity(i);
                }
            }
        });

    }
//    public void getTranslateService() {
//
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//
//        try (InputStream is = getResources().openRawResource(R.raw.creds1)) {
//
//            //Get credentials:
//            final GoogleCredentials myCredentials = GoogleCredentials.fromStream(is);
//
//            //Set credentials and get translate service:
//            TranslateOptions translateOptions = TranslateOptions.newBuilder().setCredentials(myCredentials).build();
//            translate = translateOptions.getService();
//
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//
//        }
//    }

//    public void translate(String originalText) {
//
//        //Get input text to be translated:
//        Translation translation = translate.translate(originalText, Translate.TranslateOption.targetLanguage("en"), Translate.TranslateOption.model("base"));
//        String translatedText1 = translation.getTranslatedText();
//        System.out.println("OH MY GOD "+translatedText1);
//
//        //Translated text and original text are set to TextViews:
//
//    }

//    public boolean checkInternetConnection() {
//
//        //Check internet connection:
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        //Means that we are connected to a network (mobile or wi-fi)
//        connected = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
//                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
//
//        return connected;
//    }
}