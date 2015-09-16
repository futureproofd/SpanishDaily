package to.marcus.rxtesting.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import javax.inject.Inject;
import to.marcus.rxtesting.BaseApplication;
import to.marcus.rxtesting.R;
import to.marcus.rxtesting.injection.component.DaggerWordInteractorComponent;
import to.marcus.rxtesting.injection.module.ActivityModule;
import to.marcus.rxtesting.injection.module.WordInteractorModule;
import to.marcus.rxtesting.model.Word;
import to.marcus.rxtesting.presenter.DetailPresenterImpl;
import to.marcus.rxtesting.presenter.view.BaseView;

/**
 * get the details of a clicked word object
 */
public class DetailActivity extends AppCompatActivity implements BaseView {

    @Inject
    DetailPresenterImpl mDetailPresenterImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initInjector();
        mDetailPresenterImpl.initPresenter(this);
        mDetailPresenterImpl.getWordParcel(getIntent());
    }


    private void initInjector(){
        BaseApplication baseApplication = (BaseApplication)getApplication();
        DaggerWordInteractorComponent.builder()
                .activityModule(new ActivityModule(this))
                .baseAppComponent(baseApplication.getBaseAppComponent())
                .wordInteractorModule(new WordInteractorModule())
                .build().injectDetail(this);
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showWordDetails(Word word) {

    }

    @Override
    public void showWordList(ArrayList<Word> words) {

    }

    @Override
    public void updateWordList() {

    }

}
