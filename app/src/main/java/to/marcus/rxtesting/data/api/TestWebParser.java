package to.marcus.rxtesting.data.api;

import java.util.ArrayList;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by marcus on 3/15/2016
 * Solely for test purposes.
 * https://i.ytimg.com/vi/A_BGCbup3Jg/hqdefault.jpg
 * https://thewordofwatches.files.wordpress.com/2013/06/skx0074.jpg
 * https://s-media-cache-ak0.pinimg.com/736x/32/cf/09/32cf091ad9aed3141dbe80c504bd8b6b.jpg
 * https://farm1.static.flickr.com/544/19204630923_4411fb4722.jpg
 * https://60.media.tumblr.com/b27809bb80250743f89ac4ed5f2ede80/tumblr_nd463yMRw01tofazho1_500.jpg
 * http://www.fototime.com/%7B9DBE1E2D-113A-4425-BA00-6FBA223C559E%7D/origpict/DSCN4890.JPG
 * http://www.fototime.com/%7BCC6E3F0C-A874-4BCB-AE04-5C27458A02B9%7D/origpict/Dscn4904.jpg
 * http://www.fototime.com/%7BDE6FCBE8-C4A0-4174-A0DA-1A4F7E618AED%7D/origpict/DSCN4905.JPG
 * https://i.imgur.com/wSmKrRO.jpg -- big word causes crash
 *
 */
public class TestWebParser {
    private static ArrayList<String> elementArray = new ArrayList<>();

    public static Observable<ArrayList<String>> parseWordElements(){
        return Observable.create(new Observable.OnSubscribe<ArrayList<String>>(){
            @Override
            public void call(Subscriber<? super ArrayList<String>> subscriber){
                    elementArray.add(0, "Test Mar24");
                    elementArray.add(1, "http://www.universal-soundbank.com/802a/805020000000000000000000000pkjn800000000000000000000000000000090/g/85055050505050505050505/k/2143.mp3");
                    elementArray.add(2, "March 24, 2016");
                    elementArray.add(3, "https://i.imgur.com/wSmKrRO.jpg");
                    elementArray.add(4, "translation test");
                    elementArray.add(5, "example en test");
                    elementArray.add(6, "example esp test");
                    subscriber.onNext(elementArray);
                    subscriber.onCompleted();
            }
        });
    }
}
