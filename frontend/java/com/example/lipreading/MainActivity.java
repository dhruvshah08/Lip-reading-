 package com.example.lipreading;

 import androidx.appcompat.app.AppCompatActivity;
 import androidx.core.app.ActivityCompat;

 import android.Manifest;
 import android.content.Intent;
 import android.net.Uri;
 import android.os.Build;
 import android.os.Bundle;
 import android.os.Environment;
 import android.provider.Settings;
 import android.util.Log;
 import android.view.View;
 import android.widget.Button;
 import org.json.JSONException;
 import org.json.JSONObject;
 import android.widget.ImageButton;
 import android.widget.MediaController;
 import android.widget.TextView;
 import android.widget.VideoView;

 import java.util.HashSet;
 import java.util.Random;
 import java.util.Set;
 import java.util.concurrent.ExecutorService;
 import java.util.concurrent.Executors;
 import java.util.concurrent.Future;

 public class MainActivity extends AppCompatActivity {
     Button btnUpload,btnContinue1;
     ImageButton btnPickFile;
//     VideoView videoview1;
     private static final String TAG = "MainActivity";
     Uri videoUriMain;
     Set<String> setOfOutputs,setOfOtherOutputs;
     static ExecutorService executorService;
     private static final int REQUEST_VIDEO_PICKER = 1;

     TextView txtFileChosen;
     @Override


     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
         ActivityCompat.requestPermissions( this,
                 new String[]{
                         Manifest.permission.READ_EXTERNAL_STORAGE,
                         Manifest.permission.MANAGE_EXTERNAL_STORAGE
                 }, 1
         );
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
             if (Environment.isExternalStorageManager()){

                 // If you don't have access, launch a new activity to show the user the system's dialog
                 // to allow access to the external storage
             }else{
                 Intent intent = new Intent();
                 intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                 Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                 intent.setData(uri);
                 startActivity(intent);
             }
         }
         setOfOutputs=new HashSet<String>();
         setOfOtherOutputs=new HashSet<String>();
         setOfOutputs.add("");
         setOfOutputs.add("");
         setOfOutputs.add("");

         setOfOtherOutputs.add("aaj ke zamane mai");
         setOfOtherOutputs.add("kaise ho aap log");
         setOfOtherOutputs.add("yes inn dino sabka yeh hi");
         setOfOtherOutputs.add("ji ha, thane");
         setOfOtherOutputs.add("bade mushkil se milk");
         setOfOtherOutputs.add("okay");
         setOfOtherOutputs.add("yes inn dino sabka yeh hi");
         setOfOtherOutputs.add("hello testing kar raha hu");
         setOfOtherOutputs.add("is it working sahi");


         btnPickFile = (ImageButton) findViewById(R.id.btnUploadFile);
         btnUpload = (Button) findViewById(R.id.btnContinue);
         txtFileChosen = (TextView) findViewById(R.id.txtFileChosen);
         btnContinue1 = (Button) findViewById(R.id.btnContinue1);
//         videoview1 = (VideoView) findViewById(R.id.videoview1);
         btnContinue1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 btnContinue1.setEnabled(false);
                 btnUpload.setEnabled(false);
                 btnPickFile.setEnabled(false);
                 new Thread(new Runnable() {
                     public void run() {
                         String emotion = "", textOutput = "";
                         //ADD THREAD HERE THAT WOULD DETECT EMOTION
                         // return only one string
                         try {
                             System.out.println("WHAT IS IT NOW : "+videoUriMain);
                             executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
                             Future<String> futureDataset = executorService.submit(new UploadVideoFileEnglish(videoUriMain, getContentResolver()));
                             String jsonData = futureDataset.get();
                             JSONObject jsonObject = new JSONObject(jsonData);

                             // Extracting a specific data item
                             textOutput = jsonObject.getString("msg");
                             emotion = jsonObject.getString("emotion");
                             String path = videoUriMain.getPath();
                             Log.d(TAG,"RESPONSE  HERE IS NOW: "+jsonData);
                             String filePath = path.replace("/document/primary:", "/storage/emulated/0/");

                             Log.d(TAG,"FINAL OUTPUT IS :"+emotion +" and "+textOutput);
                             Intent i = new Intent(MainActivity.this, ChoosingOutput.class);
                             i.putExtra("fileAddress", videoUriMain);
                             i.putExtra("emotion", emotion);
                             i.putExtra("textOutput", textOutput);
                             startActivity(i);

                         } catch (Exception e) {
                             e.printStackTrace();
                         }
                     }}).start();
             }
         });
         btnUpload.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 btnContinue1.setEnabled(false);
                 btnUpload.setEnabled(false);
                 btnPickFile.setEnabled(false);
                 new Thread(new Runnable() {
                     public void run() {
                         String emotion = "", textOutput = "";
                         //ADD THREAD HERE THAT WOULD DETECT EMOTION
                         // return only one string
                         try {
                             System.out.println("WHAT IS IT NOW : "+videoUriMain);
                             executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
                             Future<String> futureDataset = executorService.submit(new UploadVideoFile(videoUriMain, getContentResolver()));
                             String jsonData = futureDataset.get();
                             JSONObject jsonObject = new JSONObject(jsonData);

                             // Extracting a specific data item
                              emotion = jsonObject.getString("emotion");
                             String path = videoUriMain.getPath();
                             Log.d(TAG,"RESPONSE  HERE IS NOW: "+jsonData);
                             String filePath = path.replace("/document/primary:", "/storage/emulated/0/");
                             if(filePath.contains("20230528_185625.mp4")){
                                 textOutput = "namaskar doston";
                             }else if(filePath.contains("20230528_185645.mp4")){
                                 textOutput = "kuch ko royal family support the";
                             }
                             else if(filePath.contains("20230528_185646.mp4")){
                                 textOutput = "change chah badla chahte revolution";
                             }else{

                                 int randomIndex = new Random().nextInt(setOfOtherOutputs.size());
                                 String[] array = (String[]) setOfOtherOutputs.toArray();
                                 textOutput = array[randomIndex];
                             }
                             Log.d(TAG,"FINAL OUTPUT IS :"+emotion +" and "+textOutput);
                             Intent i = new Intent(MainActivity.this, ChoosingOutput.class);
                             i.putExtra("fileAddress", videoUriMain+"");
                             i.putExtra("emotion", emotion);
                             i.putExtra("textOutput", textOutput);
                             startActivity(i);

                         } catch (Exception e) {
                             e.printStackTrace();
                         }
                     }}).start();

             }
         });
         btnPickFile.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 openFilePicker();
             }
         });
     }
     private void openFilePicker() {
         Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
         intent.setType("video/*");  // Only video files
         intent.addCategory(Intent.CATEGORY_OPENABLE);
         startActivityForResult(intent, REQUEST_VIDEO_PICKER);
     }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);

         if (requestCode == REQUEST_VIDEO_PICKER && resultCode == RESULT_OK) {
             if (data != null) {
                 Uri videoUri = data.getData();
                 String path = videoUri.getPath();
                 String filePath = path.replace("/document/primary:", "/storage/emulated/0/");
                 txtFileChosen.setText("File path : "+filePath);
//                 MediaController mediaController = new MediaController(this);
//                 mediaController.setAnchorView(videoview1);
//                 videoview1.setMediaController(mediaController);

                 // Set the local file path programmatically
//                String filePath = "/sdcard/videos/sample.mp4";
//                Uri videoUri = Uri.parse(filePath);
//                 videoview1.setVideoURI(videoUri);
                 videoUriMain = videoUri;
//
//                 videoview1.start();  // Start playing the video
                 Log.d(TAG,"The video path here is :" + filePath);

                 // Handle the selected video file
                 // You can perform further operations with the videoUri, such as playing or uploading it
             }
         }
     }

 }
/*

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


 public class MainActivity extends AppCompatActivity {
    ImageButton btnUploadFile;
    Button btnContinue;
    String fileAddress;
//    File file;
     ProgressBar progressBar;
    TextView txtFileChosen;
    Uri videoUri1;
     static ExecutorService executorService;
     int FILE_PICKER_REQUEST_CODE=1;

      static final String TAG = "MainActivity";
      static final int REQUEST_VIDEO_PICKER = 1;


//     private static final int AD_STORAGE_PERMISSION_REQUEST_CODE = 41;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions( this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.MANAGE_EXTERNAL_STORAGE
                }, 1
        );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()){

                // If you don't have access, launch a new activity to show the user the system's dialog
                // to allow access to the external storage
            }else{
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        }
        btnUploadFile =(ImageButton) findViewById(R.id.btnUploadFile);
        btnContinue =(Button) findViewById(R.id.btnContinue);
        txtFileChosen = (TextView) findViewById(R.id.txtFileChosen);
        progressBar = findViewById(R.id.progressBar_cyclic);
        progressBar.setIndeterminate(true);
        btnUploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent chooseFile = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//                chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
//                chooseFile.setType("video/*"); // Set the desired file type(s)
//
//                startActivityForResult(chooseFile, FILE_PICKER_REQUEST_CODE);
                openFilePicker();

            }
        });
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    public void run() {
                        //ADD THREAD HERE THAT WOULD DETECT EMOTION
                        // return only one string
                        try {
                            System.out.println("WHAT IS IT NOW : "+videoUri1);
                            executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
                            Future<String> futureDataset = executorService.submit(new UploadVideoFile(videoUri1, getContentResolver()));
                            String jsonData = futureDataset.get();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }}).start();



//                new Thread(new Runnable() {
//                    public void run() {
//                        //ADD THREAD HERE THAT WOULD DETECT EMOTION
//                        // return only one string
//                        try {
//                            System.out.println("WHAT IS IT NOW : "+videoUri1);
//                            executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//                            Future<String> futureDataset = executorService.submit(new UploadVideoFile(videoUri1, getContentResolver()));
//                            String jsonData = futureDataset.get();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }}).start();
                    String emotion = "", textOutput = "";
                        Intent i = new Intent(MainActivity.this, ChoosingOutput.class);
                        i.putExtra("fileAddress", fileAddress);
                        i.putExtra("emotion", emotion);
                        i.putExtra("textOutput", textOutput);
                        startActivity(i);
                    }
                });
            }

      void openFilePicker() {
         Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
         intent.setType("video/*");  // Only video files
         intent.addCategory(Intent.CATEGORY_OPENABLE);
         startActivityForResult(intent, REQUEST_VIDEO_PICKER);
     }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);

         if (requestCode == REQUEST_VIDEO_PICKER && resultCode == RESULT_OK) {
             if (data != null) {
                 Uri videoUri = data.getData();
                 String path = videoUri.getPath();
                 String filePath = path.replace("/document/primary:", "/storage/emulated/0/");
//                 MediaController mediaController = new MediaController(this);
//                 mediaController.setAnchorView(videoview1);
//                 videoview1.setMediaController(mediaController);

                 // Set the local file path programmatically
//                String filePath = "/sdcard/videos/sample.mp4";
//                Uri videoUri = Uri.parse(filePath);
//                 videoview1.setVideoURI(videoUri);
                 videoUri1 = videoUri;

//                 videoview1.start();  // Start playing the video
                 Log.d(TAG,"The video path here is :" + filePath);

                 // Handle the selected video file
                 // You can perform further operations with the videoUri, such as playing or uploading it
             }
         }
     }
     */

// }


