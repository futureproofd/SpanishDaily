package to.marcus.rxtesting.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by marcus on 9/4/2015
 */

public class Word {
    public static final String TAG = Word.class.getSimpleName();
    private String date;
    private String imgUrl;
    private String word;
    private String translation;
    private String exampleEN;
    private String exampleESP;

    public Word(JSONObject json) throws JSONException{
        word = json.getString("word");
        date = json.getString("date");
        imgUrl = json.getString("url");
        translation = json.getString("translation");
        exampleEN = json.getString("exampleEN");
        exampleESP = json.getString("exampleESP");
    }

    //default constructor for factory
    public Word(){}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getExampleEN() {
        return exampleEN;
    }

    public void setExampleEN(String example) {
        this.exampleEN = example;
    }

    public String getExampleESP() {
        return exampleESP;
    }

    public void setExampleESP(String example) {
        this.exampleESP = example;
    }

}
