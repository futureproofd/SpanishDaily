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

/**
 * Created by marcus on 9/11/2015
 */
public class WordSerializer {
    private Context mAppContext;
    private String mFilename;
    private static final String JSON_WORD = "word";
    private static final String JSON_DATE = "date";
    private static final String JSON_URL = "url";
    private static final String JSON_SOUNDREF = "soundRef";
    private static final String JSON_TRANSLATION = "translation";
    private static final String JSON_EXAMPLEEN = "exampleEN";
    private static final String JSON_EXAMPLEESP = "exampleESP";

    public WordSerializer(Context c, String filename){
        mAppContext = c;
        mFilename = filename;
    }

    //Save Words to JSON
    public void saveWords(ArrayList<Word> words) throws JSONException, IOException {
        JSONArray array = new JSONArray();
        for(Word w : words)
            array.put(convertToJSON(w));

        Writer writer = null;
        try{
            OutputStream out = mAppContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        }finally {
            if(writer != null)
                writer.close();
        }
    }

    //convert each Word object variable
    public JSONObject convertToJSON(Word word) throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_WORD, word.getWord());
        json.put(JSON_DATE, word.getDate());
        json.put(JSON_URL, word.getImgUrl());
        json.put(JSON_SOUNDREF, word.getSoundRef());
        json.put(JSON_TRANSLATION, word.getTranslation());
        json.put(JSON_EXAMPLEEN, word.getExampleEN());
        json.put(JSON_EXAMPLEESP, word.getExampleESP());
        return json;
    }

    //load JSON Words file back into an arrayList of word objects
    public ArrayList<Word> loadWords() throws IOException, JSONException{
        ArrayList<Word> words = new ArrayList<Word>();
        BufferedReader reader = null;
        try{
            //open and read the file into a stringBuilder
            InputStream in = mAppContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));

            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null){
                jsonString.append(line);
            }
            //parse JSON using JSONTokener
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            //Build the array of words from JSONObjects (call the custom Word constructor)
            for (int i = 0; i < array.length(); i++){
                words.add(new Word(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e){

        } finally{
            if (reader != null)
                reader.close();
        }
        return words;
    }

}
