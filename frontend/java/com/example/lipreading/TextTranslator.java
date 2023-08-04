package com.example.lipreading;

import java.io.IOException;

        import com.google.gson.*;

import org.json.JSONArray;
import org.json.JSONException;

import okhttp3.MediaType;
        import okhttp3.OkHttpClient;
        import okhttp3.Request;
        import okhttp3.RequestBody;
        import okhttp3.Response;

public class TextTranslator {
    private static String key = "03c96eb3eb0241e5a91b9fe039fcef26";

    // location, also known as region.
    // required if you're using a multi-service or regional (not global) resource. It can be found in the Azure portal on the Keys and Endpoint page.
    private static String location = "centralindia";


    // Instantiates the OkHttpClient.
    OkHttpClient client = new OkHttpClient();

    // This function performs a POST request.
    public String Post(String textInput, String languageTo) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,
                "[{\"Text\": \""+textInput+"!\"}]");
        Request request = new Request.Builder()
                .url("https://api.cognitive.microsofttranslator.com/translate?api-version=3.0&from=en&to="+languageTo)
                .post(body)
                .addHeader("Ocp-Apim-Subscription-Key", key)
                // location required if you're using a multi-service or regional (not global) resource.
                .addHeader("Ocp-Apim-Subscription-Region", location)
                .addHeader("Content-type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    // This function prettifies the json response.
    public static String prettify(String json_text) {
        JsonParser parser = new JsonParser();
        JsonElement json = parser.parse(json_text);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            JSONArray array = new JSONArray(gson.toJson(json));
            String output1 = array.getJSONObject(0).getString("translations");
            JSONArray array1 = new JSONArray(output1);
            String translatedText = array1.getJSONObject(0).getString("text");
            return translatedText;
        } catch (JSONException e) {
            e.printStackTrace();
        }
      return "";
    }

    public static String execute(String textInput,String languageTo) {
        try {
            TextTranslator translateRequest = new TextTranslator();
            String response = translateRequest.Post(textInput,languageTo);
            return prettify(response);
        } catch (Exception e) {
            System.out.println("PROBLEM ");
            e.printStackTrace();
        }
        return "";
    }
}