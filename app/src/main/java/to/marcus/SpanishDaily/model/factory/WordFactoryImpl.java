package to.marcus.SpanishDaily.model.factory;

import java.util.ArrayList;
/**
 * Created by marcus on 9/8/2015
 */

public class WordFactoryImpl{

    public static final class Word{
        public static to.marcus.SpanishDaily.model.Word newWordInstance(ArrayList<String> wordElements){
            final to.marcus.SpanishDaily.model.Word defaultWord = new to.marcus.SpanishDaily.model.Word();
                defaultWord.setWord(wordElements.get(0));
                defaultWord.setSoundRef(wordElements.get(1));
                defaultWord.setDate(wordElements.get(2));
                defaultWord.setImgUrl(wordElements.get(3));
                defaultWord.setTranslation(wordElements.get(4));
                defaultWord.setExampleEN(wordElements.get(5));
                defaultWord.setExampleESP(wordElements.get(6));
                defaultWord.setFavorite(0);
                defaultWord.setVisibility(1);
                defaultWord.setSearched(0);
                return defaultWord;
        }
    }
}
