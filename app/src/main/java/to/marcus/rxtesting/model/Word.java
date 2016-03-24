package to.marcus.rxtesting.model;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;
import to.marcus.rxtesting.util.DateUtility;

/**
 * Created by marcus on 9/4/2015
 */

public class Word implements Parcelable, Comparable {
    private String date;
    private String imgUrl;
    private String word;
    private String soundRef;
    private String translation;
    private String exampleEN;
    private String exampleESP;
    private int favorite;
    private int visibility;
    private int searched;

    //De-Serializer constructor
    public Word(JSONObject json) throws JSONException{
        word = json.getString("word");
        date = json.getString("date");
        imgUrl = json.getString("url");
        soundRef = json.getString("soundRef");
        translation = json.getString("translation");
        exampleEN = json.getString("exampleEN");
        exampleESP = json.getString("exampleESP");
        favorite = json.getInt("favorite");
        visibility = json.getInt("visibility");
        searched = json.getInt("searched");
    }

    //Factory constructor
    public Word(){}

    //Parcel constructor
    public Word(String date, String imgUrl, String word, String soundRef, String translation
            ,String exampleEN, String exampleESP, int favorite, int visibility, int searched){
        this.date = date;
        this.imgUrl = imgUrl;
        this.word = word;
        this.soundRef = soundRef;
        this.translation = translation;
        this.exampleEN = exampleEN;
        this.exampleESP = exampleESP;
        this.favorite = favorite;
        this.visibility = visibility;
        this.searched = searched;
    }

    public String getDate(){return date;}

    public void setDate(String date){this.date = date;}

    public String getImgUrl(){return imgUrl;}

    public void setImgUrl(String imgUrl){this.imgUrl = imgUrl;}

    public String getWord(){ return word;}

    public void setWord(String word){this.word = word;}

    public String getSoundRef(){return soundRef;}

    public void setSoundRef(String soundRef){this.soundRef = soundRef;}

    public String getTranslation(){return translation;}

    public void setTranslation(String translation){this.translation = translation;}

    public String getExampleEN(){return exampleEN;}

    public void setExampleEN(String example){this.exampleEN = example;}

    public String getExampleESP(){return exampleESP;}

    public void setExampleESP(String example){this.exampleESP = example;}

    public int getFavorite(){return favorite;}

    public void setFavorite(int favorite){this.favorite = favorite;}

    public int getVisibility(){return visibility;}

    public void setVisibility(int visibility){this.visibility = visibility;}

    public int getSearched(){return searched;}

    public void setSearched(int searched){this.searched = searched;}

    /*
    * Parcelable Methods
     */
    public static final Parcelable.Creator<Word> CREATOR = new Parcelable.Creator<Word>(){
        @Override
        public Word createFromParcel(Parcel parcel){ return new Word(parcel);}

        @Override
        public Word[] newArray(int i){ return new Word[i];}
    };

    private Word(Parcel in){
        this(
            in.readString(),
            in.readString(),
            in.readString(),
            in.readString(),
            in.readString(),
            in.readString(),
            in.readString(),
            in.readInt(),
            in.readInt(),
            in.readInt()
        );
    }

    @Override
    public void writeToParcel(Parcel out, int flags){
        out.writeString(date);
        out.writeString(imgUrl);
        out.writeString(word);
        out.writeString(soundRef);
        out.writeString(translation);
        out.writeString(exampleEN);
        out.writeString(exampleESP);
        out.writeInt(favorite);
        out.writeInt(visibility);
        out.writeInt(searched);
    }

    //Compare dates
    /*
    0 if the argument Date is equal to this Date; a value less than 0 if this Date is
    before the Date argument; and a value greater than 0 if this Date is after the Date argument.
     */
    @Override
    public int compareTo(Object wordToCompare) {
        Date dtCompareDate = DateUtility.formatDateStringNoYear(((Word)wordToCompare).getDate());
        Date dtOriginalDate = DateUtility.formatDateStringNoYear(this.getDate());
        return dtOriginalDate.compareTo(dtCompareDate);
    }

    //generally unused
    @Override
    public int describeContents(){return 0;}

}