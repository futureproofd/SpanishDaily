package to.marcus.rxtesting.presenter.view;

import java.util.ArrayList;

import to.marcus.rxtesting.model.Word;

/**
 * Created by marcus on 9/14/2015
 */
public interface BaseView {
    void showLoading();
    void hideLoading();
    void showWordDetails(Word word);
    void showWordList(ArrayList<Word> words);
    void updateWordList();
}
