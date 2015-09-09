package to.marcus.rxtesting.model;

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
    private ArrayList<String> examples;

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

    public ArrayList<String> getExamples() {
        return examples;
    }

    public void setExamples(ArrayList<String> examples) {
        this.examples = examples;
    }

}
