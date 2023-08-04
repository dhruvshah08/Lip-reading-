
package com.example.lipreading;


        import android.content.ContentResolver;
        import android.net.Uri;
        import android.util.Log;

        import java.io.BufferedReader;
        import java.io.BufferedWriter;
        import java.io.DataOutputStream;
//        import java.io.File;
        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.OutputStream;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.OutputStreamWriter;
        import java.io.PrintWriter;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.net.URLConnection;
        import java.nio.charset.StandardCharsets;
        import java.util.concurrent.Callable;

        import okhttp3.MediaType;
        import okhttp3.MultipartBody;
        import okhttp3.OkHttpClient;
        import okhttp3.Request;
        import okhttp3.RequestBody;
        import okhttp3.Response;

public class UploadVideoFile implements Callable<String> {
    Uri videoUri;
     static final String TAG = "MainActivity";
    ContentResolver contentResolver;
    public  UploadVideoFile(Uri videoUri, ContentResolver contentResolver){

        System.out.println("IN CONSTRUCTOR");
        this.videoUri = videoUri;
        this.contentResolver = contentResolver;
    }

    @Override
    public String call() throws Exception {

        String url = ColabLink.url+"/upload_hindi";


            // Create a File object from the video file path
        String path = videoUri.getPath();
        String filePath = path.replace("/document/primary:", "/storage/emulated/0/");
        Log.d(TAG,"FILE PATH IS: "+filePath);

        // Create the connection
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

        // Set the request method to POST
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        // Set the Content-Type header
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");

        // Open a file input stream for the .mp4 file
        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);

        // Create the request body
        String boundary = "----WebKitFormBoundary7MA4YWxkTrZu0gW";
        String lineBreak = "\r\n";

        OutputStream outputStream = connection.getOutputStream();
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream));

        // Add the file field
        writer.append("--").append(boundary).append(lineBreak);
        writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"").append(file.getName()).append("\"").append(lineBreak);
        writer.append("Content-Type: video/mp4").append(lineBreak);
        writer.append(lineBreak);
        writer.flush();

        // Write the file content to the request body
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();

        // Add the closing boundary
        writer.append(lineBreak);
        writer.append("--").append(boundary).append("--").append(lineBreak);
        writer.flush();

        // Close the streams
        fileInputStream.close();
        writer.close();

        // Get the response
        int responseCode = connection.getResponseCode();
        InputStream responseStream = (responseCode == HttpURLConnection.HTTP_OK) ? connection.getInputStream() : connection.getErrorStream();
        BufferedReader responseReader = new BufferedReader(new InputStreamReader(responseStream));

        // Read the response
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = responseReader.readLine()) != null) {
            response.append(line);
        }

        // Close the response streams
        responseReader.close();
        responseStream.close();

        // Print the response
        System.out.println("Response 1: " + response.toString());

        // Disconnect the connection
        connection.disconnect();



        return response.toString();

    }
}

