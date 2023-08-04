package com.example.lipreading;

import java.util.HashMap;
import java.util.Map;

public class LanguageAndCodes {
    private Map<String,String>  mapOfLanguageCodes = new HashMap<>();
    public LanguageAndCodes(){
        mapOfLanguageCodes.put("Bengali", "bn-IN");
        mapOfLanguageCodes.put("English", "en-IN");
        mapOfLanguageCodes.put("Gujarati", "gu-IN");
        mapOfLanguageCodes.put("Hindi", "hi-IN");
        mapOfLanguageCodes.put("Kannada", "kn-IN");
        mapOfLanguageCodes.put("Malayalam", "ml-IN");
        mapOfLanguageCodes.put("Marathi","mr-IN" );
        mapOfLanguageCodes.put("Tamil", "ta-IN");
        mapOfLanguageCodes.put("Telugu", "te-IN");
        mapOfLanguageCodes.put("Urdu", "ur-IN");
    }
    public String getLanguageCode(String language){
        return mapOfLanguageCodes.get(language);
    }
}
