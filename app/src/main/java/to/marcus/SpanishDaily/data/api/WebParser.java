package to.marcus.SpanishDaily.data.api;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by marcus on 03/09/15
 * RxJava + JSoup
 */

public class WebParser {
    //EndPoints
    private static final String ENDPOINT = "http://www.spanishcentral.com/word-of-the-day";
    private static final String SOUNDBYTE_ENDPOINT = "http://media.merriam-webster.com/audio/prons/es/me/mp3/";
    //Elements
    private static final String USERAGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36";
    private static final String WORD_ELEMENT = "div[class=main_view_spanish_wod_word_text mobile-hide";
    private static final String DATE_ELEMENT = "div[class=main_view_spanish_wod_for_date mobile-hide";
    private static final String PHOTO_ELEMENT = "div[class=main_view_spanish_wod_photo";
    private static final String TRANSLATION_ELEMENT = "div[class=main_view_spanish_wod_translation_block_text";
    private static final String EXAMPLEESP_ELEMENT = "div[class=main_view_spanish_wod_example_block_spanish";
    private static final String EXAMPLEEN_ELEMENT ="div[class=main_view_spanish_wod_example_block_english";
    //SoundByte Params
    private static final String FILE_EXTENSION = "sp.mp3";
    //Static objects
    private static String elementHolder;

    @Inject public WebParser(){}

    /*
    Return ArrayList of elements required to build a Word Object
     */
    public static Observable<ArrayList<String>> parseWordElements(){
        return Observable.create(new Observable.OnSubscribe<ArrayList<String>>(){
            @Override
            public void call(Subscriber<? super ArrayList<String>> subscriber){
                try {
                    ArrayList<String> elementArray = new ArrayList<>();
                    Document doc;
                    doc = Jsoup.connect(ENDPOINT).userAgent(USERAGENT).get();
                    Elements elementDiv = doc.select(WORD_ELEMENT);
                    //get word
                    for (Element table : elementDiv.select("Table")) {
                        for (Element row : table.select("tr")){
                           elementHolder = row.select("td").get(0).text();
                            elementArray.add(0, elementHolder);
                            //get soundByte
                            elementHolder = row.select("a").attr("href");
                            Pattern pattern = Pattern.compile("file=(\\S+)sp&");
                            Matcher matcher = pattern.matcher(elementHolder);
                            if(matcher.find()){
                                elementHolder = matcher.group(1);
                                elementArray.add(1, elementHolder);
                            }else{
                                elementArray.add(1, "notFound");
                            }
                        }
                    }
                    elementDiv = doc.select(DATE_ELEMENT);
                    elementHolder = elementDiv.first().text();
                    elementArray.add(2, elementHolder);
                    elementDiv = doc.select(PHOTO_ELEMENT);
                    elementHolder = elementDiv.select("img").first().attr("src");
                    elementArray.add(3, elementHolder);
                    elementDiv = doc.select(TRANSLATION_ELEMENT);
                    elementHolder = elementDiv.select("p").first().text();
                    elementArray.add(4, elementHolder);
                    elementDiv = doc.select(EXAMPLEEN_ELEMENT);
                    elementHolder = elementDiv.text();
                    elementArray.add(5, elementHolder);
                    elementDiv = doc.select(EXAMPLEESP_ELEMENT);
                    elementHolder = elementDiv.text();
                    elementArray.add(6, elementHolder);
                    subscriber.onNext(elementArray);
                } catch (IOException e) {
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        });
    }

    /*
    Gets the MP3 soundByte from an onClick request
     */
    public static Observable<byte[]> parseSoundByte(String word){
        final String myword = word;
        return Observable.create(new Observable.OnSubscribe<byte[]>(){
            @Override
            public void call(Subscriber<? super byte[]> subscriber){
                String url = SOUNDBYTE_ENDPOINT
                        + myword.substring(0,1)+"/"
                        + myword
                        + FILE_EXTENSION;
                try{
                    subscriber.onNext(getUrlBytes(url));
                }catch (IOException e){
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        });
    }

    /*
    returns the byteArray of a formatted word URL
     */
    private static byte[] getUrlBytes(String urlStr) throws IOException{
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestProperty("User-Agent", USERAGENT);
        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            int bytesRead;
            byte[] buffer = new byte[1024];
            //fill up the bytes read
            while((bytesRead = in.read(buffer)) > 0){
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        }finally{
            connection.disconnect();
        }
    }

}


