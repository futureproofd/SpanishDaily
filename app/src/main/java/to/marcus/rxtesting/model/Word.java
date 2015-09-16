package to.marcus.rxtesting.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by marcus on 9/4/2015
 */

public class Word implements Parcelable {
    public static final String TAG = Word.class.getSimpleName();
    private String date;
    private String imgUrl;
    private String word;
    private String translation;
    private String exampleEN;
    private String exampleESP;

    //De-Serializer constructor
    public Word(JSONObject json) throws JSONException{
        word = json.getString("word");
        date = json.getString("date");
        imgUrl = json.getString("url");
        translation = json.getString("translation");
        exampleEN = json.getString("exampleEN");
        exampleESP = json.getString("exampleESP");
    }

    //Factory constructor
    public Word(){}

    //Parcel constructor
    public Word(String date, String imgUrl, String word, String translation, String exampleEN, String exampleESP){
        this.date = date;
        this.imgUrl = imgUrl;
        this.word = word;
        this.translation = translation;
        this.exampleEN = exampleEN;
        this.exampleESP = exampleESP;
    }

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

    /*
    * Parcelable Methods
     */
    public static final Parcelable.Creator<Word> CREATOR = new Parcelable.Creator<Word>(){
        @Override
        public Word createFromParcel(Parcel parcel) {
            return new Word(parcel);
        }

        @Override
        public Word[] newArray(int i) {
            return new Word[i];
        }
    };

    private Word(Parcel in){
        this(
                in.readString(),
                in.readString(),
                in.readString(),
                in.readString(),
                in.readString(),
                in.readString()
        );
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(date);
        out.writeString(imgUrl);
        out.writeString(word);
        out.writeString(translation);
        out.writeString(exampleEN);
        out.writeString(exampleESP);
    }

    //generally not used
    @Override
    public int describeContents() {
        return 0;
    }
}
