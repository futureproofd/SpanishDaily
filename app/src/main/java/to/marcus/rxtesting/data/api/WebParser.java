package to.marcus.rxtesting.data.api;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import javax.inject.Inject;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by marcus on 03/09/15
 * RxJava + JSoup
 */

public class WebParser {
    private final String TAG = WebParser.class.getSimpleName();
    private static final String WEBROOT ="http://www.spanishcentral.com";
    private static final String ENDPOINT = "http://www.spanishcentral.com/word-of-the-day";
    private static final String USERAGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36";
    private static final String WORD_ELEMENT = "div[class=main_view_spanish_wod_word_text mobile-hide";
    private static final String PHOTO_ELEMENT = "div[class=main_view_spanish_wod_photo";
    private static final String TRANSLATION_ELEMENT = "div[class=main_view_spanish_wod_translation_block_text";
    private static final String EXAMPLEESP_ELEMENT = "div[class=main_view_spanish_wod_example_block_spanish";
    private static final String EXAMPLEEN_ELEMENT ="div[class=main_view_spanish_wod_example_block_english";

    @Inject public WebParser(){}

    public static Observable<String> parseWord(){
        return Observable.create(new Observable.OnSubscribe<String>(){
             @Override
             public void call(Subscriber<? super String> subscriber){
                 try {
                     Elements wordDiv = Jsoup.connect(ENDPOINT).userAgent(USERAGENT).get()
                             .select(WORD_ELEMENT);
                     for (Element table : wordDiv.select("Table")) {
                         for (Element row : table.select("tr")) {
                             String word = row.select("td").get(0).text();
                             subscriber.onNext(word);
                         }
                     }
                 } catch (IOException e) {
                     subscriber.onError(e);
                 }
                 subscriber.onCompleted();
             }
        });
    }

    public static Observable<String> parseImage(){
        return Observable.create(new Observable.OnSubscribe<String>(){
            @Override
            public void call(Subscriber<? super String> subscriber){
                try{
                    Elements imgDiv = Jsoup.connect(ENDPOINT).userAgent(USERAGENT).get()
                            .select(PHOTO_ELEMENT);
                    String imgsrc = WEBROOT + imgDiv.select("img").first().attr("src");
                    subscriber.onNext(imgsrc);
                }catch (IOException e){
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<String> parseTranslation(){
        return Observable.create(new Observable.OnSubscribe<String>(){
            @Override
            public void call(Subscriber<? super String> subscriber){
                try {
                    Elements translationDiv = Jsoup.connect(ENDPOINT).userAgent(USERAGENT).get()
                            .select(TRANSLATION_ELEMENT);
                    String translation = translationDiv.select("p").first().text();
                    subscriber.onNext(translation);
                }catch(IOException e) {
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<ArrayList<String>> parseExamples(){
        return Observable.create(new Observable.OnSubscribe<ArrayList<String>>() {
            @Override
            public void call(Subscriber<? super ArrayList<String>> subscriber) {
                try {
                    ArrayList<String> transList = new ArrayList<String>();
                    //get example ESPANOL
                    String examplesDivEsp = Jsoup.connect(ENDPOINT).userAgent(USERAGENT).get()
                            .select(EXAMPLEESP_ELEMENT).text();
                    //get example INGLES
                    String examplesDivEn = Jsoup.connect(ENDPOINT).userAgent(USERAGENT).get()
                            .select(EXAMPLEEN_ELEMENT).text();
                    transList.add(examplesDivEn);
                    transList.add(examplesDivEsp);
                    subscriber.onNext(transList);
                } catch (IOException e) {
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        });
    }


}


