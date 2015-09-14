package to.marcus.rxtesting.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;
import to.marcus.rxtesting.BaseApplication;
import to.marcus.rxtesting.R;
import to.marcus.rxtesting.injection.component.DaggerWordInteractorComponent;
import to.marcus.rxtesting.injection.module.ActivityModule;
import to.marcus.rxtesting.injection.module.WordInteractorModule;
import to.marcus.rxtesting.model.Word;
import to.marcus.rxtesting.presenter.TestPresenterImpl;
import to.marcus.rxtesting.presenter.view.HomeView;
import to.marcus.rxtesting.ui.adapter.WordRecyclerAdapter;

/*
* Main, list (card) view
 */

public class HomeActivity extends ActionBarActivity implements HomeView {
    public final String TAG = HomeActivity.class.getSimpleName();
    @Bind(R.id.card_view) RecyclerView cardView;

    @Inject
    TestPresenterImpl mTestPresenterImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initInjector();
        initRecyclerView();
        mTestPresenterImpl.initPresenter(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mTestPresenterImpl.onStart();
    }

    @Override
    public void onStop(){
        super.onStop();
        mTestPresenterImpl.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initInjector(){
        BaseApplication baseApplication = (BaseApplication)getApplication();
        DaggerWordInteractorComponent.builder()
                .activityModule(new ActivityModule(this))
                .baseAppComponent(baseApplication.getBaseAppComponent())
                .wordInteractorModule(new WordInteractorModule())
                .build().inject(this);
    }

    private void initRecyclerView(){
        //cardView = (RecyclerView) findViewById(R.id.card_view);
        cardView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        cardView.setLayoutManager(llm);
    }

    /*
    * View Implementations
     */
    @Override
    public void showLoading() {
        Log.i(TAG, "loading initiated");
    }

    @Override
    public void hideLoading() {
        Log.i(TAG, "loading stopped");
    }

    @Override
    public void showWordList(ArrayList<Word> wordArrayList){
        WordRecyclerAdapter wordRecyclerAdapter = new WordRecyclerAdapter(wordArrayList);
        cardView.setAdapter(wordRecyclerAdapter);
    }

    @Override
    public void updateWordList(){
        cardView.getAdapter().notifyDataSetChanged();
    }
}
