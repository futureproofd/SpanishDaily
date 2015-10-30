package to.marcus.rxtesting.model;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by marcus on 9/11/2015
 * Serialize/De-serialization JSON of Words Array and App Preferences
 */
public class ObjectSerializer{
    private Context mAppContext;
    private String mFilename;
    private static final String JSON_WORD = "word";
    private static final String JSON_DATE = "date";
    private static final String JSON_URL = "url";
    private static final String JSON_SOUNDREF = "soundRef";
    private static final String JSON_TRANSLATION = "translation";
    private static final String JSON_EXAMPLEEN = "exampleEN";
    private static final String JSON_EXAMPLEESP = "exampleESP";
    private static final String JSON_FAVORITE = "favorite";
    private static final String JSON_VISIBILITY = "visibility";

    public ObjectSerializer(Context c, String filename){
        mAppContext = c;
        mFilename = filename;
    }

    public void saveWords(ArrayList<Word> words) throws JSONException, IOException {
        JSONArray array = new JSONArray();
        for(Word w : words)
            array.put(convertToJSON(w));
        writeToJSON(mFilename,array);
    }

    public ArrayList<Word> loadWords() throws IOException, JSONException{
        ArrayList<Word> words = new ArrayList<>();
        BufferedReader reader = null;
        //parse JSON using JSONTokener
        JSONArray array = (JSONArray) new JSONTokener(getJSONString(mFilename).toString()).nextValue();
        //Build the array of words from JSONObjects (call the custom Word constructor)
        for (int i = 0; i < array.length(); i++){
            words.add(new Word(array.getJSONObject(i)));
        }
        if (reader != null)
            reader.close();
        return words;
    }

    public void savePreferences(Map<String,Boolean> preferences) throws JSONException, IOException{
        JSONObject jsonObj = new JSONObject();
        Iterator<Map.Entry<String, Boolean>> entries = preferences.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, Boolean> entry = entries.next();
            jsonObj.put(entry.getKey(),entry.getValue());
        }
        writeToJSON(mFilename,jsonObj);
    }

    public HashMap<String,Boolean> loadPreferences() throws JSONException, IOException{
        HashMap<String, Boolean> prefs = new HashMap<>();
        BufferedReader reader = null;
        JSONObject jObject = new JSONObject(getJSONString(mFilename).toString());
        Iterator<?> keys = jObject.keys();
        while( keys.hasNext() ){
            String key = (String)keys.next();
            Boolean value = jObject.getBoolean(key);
            prefs.put(key, value);
        }
        if (reader != null)
            reader.close();
        return prefs;
    }

    /*
    JSON Read/Write Methods
    */
    public JSONObject convertToJSON(Word word) throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_WORD, word.getWord());
        json.put(JSON_DATE, word.getDate());
        json.put(JSON_URL, word.getImgUrl());
        json.put(JSON_SOUNDREF, word.getSoundRef());
        json.put(JSON_TRANSLATION, word.getTranslation());
        json.put(JSON_EXAMPLEEN, word.getExampleEN());
        json.put(JSON_EXAMPLEESP, word.getExampleESP());
        json.put(JSON_FAVORITE, word.getFavorite());
        json.put(JSON_VISIBILITY, word.getVisibility());
        return json;
    }

    private StringBuilder getJSONString(String filename) throws IOException{
        BufferedReader reader = null;
        StringBuilder jsonString = new StringBuilder();
        //open and read the file into a stringBuilder
        try{
            InputStream in = mAppContext.openFileInput(filename);
            reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = reader.readLine()) != null){
                jsonString.append(line);
            }
        }catch (FileNotFoundException e){

        }finally {
            if (reader != null)
                reader.close();
        }
        return jsonString;
    }

    private void writeToJSON(String filename, Object jsonObj)throws IOException{
        Writer writer = null;
        try{
            OutputStream out = mAppContext.openFileOutput(filename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(jsonObj.toString());
        }finally {
            if(writer != null)
                writer.close();
        }
    }
}