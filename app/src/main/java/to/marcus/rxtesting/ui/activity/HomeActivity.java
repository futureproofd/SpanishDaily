package to.marcus.rxtesting.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.ArrayList;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;
import to.marcus.rxtesting.BaseApplication;
import to.marcus.rxtesting.Constants;
import to.marcus.rxtesting.R;
import to.marcus.rxtesting.injection.component.DaggerWordInteractorComponent;
import to.marcus.rxtesting.injection.module.ActivityModule;
import to.marcus.rxtesting.injection.module.WordInteractorModule;
import to.marcus.rxtesting.model.Word;
import to.marcus.rxtesting.presenter.HomePresenterImpl;
import to.marcus.rxtesting.presenter.view.HomeView;
import to.marcus.rxtesting.ui.CardDialogFragment;
import to.marcus.rxtesting.ui.adapter.RecyclerViewItemClickListener;
import to.marcus.rxtesting.ui.adapter.RecyclerViewMenuClickListener;
import to.marcus.rxtesting.ui.adapter.WordRecyclerAdapter;
import to.marcus.rxtesting.util.ImageUtility;

/*
* Main, list (card) view
 */
public class HomeActivity extends BaseActivity implements HomeView
        ,RecyclerViewItemClickListener
        ,RecyclerViewMenuClickListener
        ,CardDialogFragment.CardDialogListener {
    public final String TAG = HomeActivity.class.getSimpleName();
    public int datasetMode = 0; //default dataset
    public static HomeActivity instance = null;
    private static final String WORD_OBJECT = "WORD_OBJECT";
    @Bind(R.id.recycler_view) RecyclerView recyclerView;
    private WordRecyclerAdapter mWordRecyclerAdapter;
    @Inject HomePresenterImpl mHomePresenterImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        ButterKnife.bind(this);
        initInjector();
        initRecyclerView();
        mHomePresenterImpl.initPresenter(this);
        if(savedInstanceState != null){
            datasetMode = savedInstanceState.getInt(Constants.MODE_KEY);
            mHomePresenterImpl.selectDataset(datasetMode);
        }else{
            mHomePresenterImpl.selectDataset(datasetMode);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mHomePresenterImpl.onStart();
    }

    @Override
    public void onStop(){
        super.onStop();
        mHomePresenterImpl.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putInt(Constants.MODE_KEY, datasetMode);
        super.onSaveInstanceState(outState);
    }

    private void initInjector(){
        BaseApplication baseApplication = (BaseApplication)getApplication();
        DaggerWordInteractorComponent.builder()
                .activityModule(new ActivityModule(this))
                .baseAppComponent(baseApplication.getBaseAppComponent())
                .wordInteractorModule(new WordInteractorModule())
                .build().injectHome(this);
    }

    private void initRecyclerView(){
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
    }

    public void selectDataset(int datasetMode){
        this.datasetMode = datasetMode;
        mHomePresenterImpl.selectDataset(datasetMode);
    }

    //View Implementations
    @Override
    public void showLoading(){Log.i(TAG, "loading initiated");}

    @Override
    public void hideLoading(){Log.i(TAG, "loading stopped");}

    @Override
    public void showNotification(String notification){
        Toast.makeText(this, notification, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showWordList(ArrayList<Word> wordArrayList){
        mWordRecyclerAdapter = new WordRecyclerAdapter(wordArrayList, this, this);
        recyclerView.setAdapter(mWordRecyclerAdapter);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void refreshWordList(){mWordRecyclerAdapter.notifyDataSetChanged();}

    //RecyclerView click listeners
    @Override
    public void onObjectClick(View v, int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(WORD_OBJECT, mHomePresenterImpl.onElementSelected(position));
        intent.putExtra("IMAGE", ImageUtility.getImage((ImageView) v.findViewById(R.id.imgWord)).toByteArray());

        ImageView wordImage = (ImageView)v.findViewById(R.id.imgWord);
        LinearLayout wordString = (LinearLayout)v.findViewById(R.id.wordNameHolder);

           String transitionImgName = this.getString(R.string.transition_img);
           String transitionWordName = this.getString(R.string.transition_word);
           Pair<View, String> p1 = Pair.create((View) wordImage, transitionImgName);
           Pair<View, String> p2 = Pair.create((View) wordString, transitionWordName);

        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, p1, p2);
        ActivityCompat.startActivity(this, intent, options.toBundle());
        this.startActivity(intent);
    }

    @Override
    public void onObjectMenuClick(View v, int position){
        //launch dialog fragment
        CardDialogFragment dialogFragment = new CardDialogFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        dialogFragment.setArguments(args);
        dialogFragment.show(getFragmentManager(), TAG);
    }

    //CardView dialog listeners
    @Override
    public void onDialogClickDismiss(CardDialogFragment dialogFragment, int position){
        mHomePresenterImpl.onDismissOptionSelected(position);
    }

    @Override
    public void onDialogClickFavorite(CardDialogFragment dialogFragment, int position){
        mHomePresenterImpl.onFavOptionSelected(position);
    }
}
