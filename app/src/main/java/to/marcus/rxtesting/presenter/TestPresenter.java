package to.marcus.rxtesting.presenter;

import android.util.Log;
import java.util.ArrayList;
import javax.inject.Inject;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import to.marcus.rxtesting.data.ParserTest;

/**
 * Created by marcus on 9/2/2015
 * test
 */
public class TestPresenter {
    private final ParserTest mParserTest;
    private static final String TAG = TestPresenter.class.getSimpleName();


    @Inject public TestPresenter(ParserTest parserTest){
        mParserTest = parserTest;
    }

    public void initPresenter(){
        //show loading indicator
        pullWordFromNetwork();
    }

    private void pullWordFromNetwork(){
        mParserTest.parseWord()
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<String>(){
                    @Override
                    public void call(String s) {
                        Log.i(TAG, s);
                    }
                });

        mParserTest.parseImage()
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i(TAG, s);
                    }
                });

        mParserTest.parseTranslation()
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<String>(){
                    @Override
                    public void call(String s){
                        Log.i(TAG, s);
                    }
                });

        mParserTest.parseExamples()
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<ArrayList<String>>() {
                    @Override
                    public void call(ArrayList<String> s) {
                        String trans1 = s.get(0);
                        String trans2 = s.get(1);
                        Log.i(TAG, trans1 + " " + trans2);
                    }
                });
    }

}
