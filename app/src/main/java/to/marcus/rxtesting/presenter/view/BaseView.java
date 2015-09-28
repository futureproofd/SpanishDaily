package to.marcus.rxtesting.presenter.view;

import android.app.Activity;
import java.util.ArrayList;
import to.marcus.rxtesting.model.Word;

/**
 * Created by marcus on 9/14/2015
 */
public interface BaseView {
    void showLoading();
    void hideLoading();
    void showWordDetails();
    void showWordList(ArrayList<Word> words);
    void updateWordList();
    Activity getActivity();
}
