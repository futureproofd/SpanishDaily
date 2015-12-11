package to.marcus.rxtesting.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
    public String dataSetMode = "unfiltered"; //default dataset
    private final String UNFILTERED_MODE = Constants.MAIN_MODE;
    private final String DISMISSED_MODE = Constants.DISMISSED_MODE;
    private final String FAVORITES_MODE = Constants.FAVORITES_MODE;
    private final String SEARCH_MODE = Constants.SEARCH_MODE;
    private ArrayList<Word> mUnfilteredDataSet;
    public static HomeActivity instance = null;
    private static final String WORD_OBJECT = "WORD_OBJECT";
    @Bind(R.id.recycler_view) RecyclerView recyclerView;
    private WordRecyclerAdapter mWordRecyclerAdapter;
    private StaggeredGridLayoutManager mGridLayoutManager;
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
            dataSetMode = savedInstanceState.getString(Constants.MODE_KEY);
            selectDataSet(dataSetMode);
        }else{
            selectDataSet(dataSetMode);
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
        outState.putString(Constants.MODE_KEY, dataSetMode);
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

    public void initRecyclerView(){
        mGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mGridLayoutManager);
    }

    public void selectDataSet(String dataSetMode){
        if(mWordRecyclerAdapter == null){
            mHomePresenterImpl.selectDataset(dataSetMode);
        }else{
            switch (dataSetMode){
                case UNFILTERED_MODE:  mGridLayoutManager.setSpanCount(1);
                    break;
                case FAVORITES_MODE:   mGridLayoutManager.setSpanCount(2);
                    break;
                case DISMISSED_MODE:   mGridLayoutManager.setSpanCount(2);
                    break;
                case SEARCH_MODE:      mGridLayoutManager.setSpanCount(1);
                    break;
            }
            mWordRecyclerAdapter.resetDataSet(mUnfilteredDataSet);
            mWordRecyclerAdapter.setDataSetMode(dataSetMode);
            mWordRecyclerAdapter.getFilter().filter(dataSetMode);
        }
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
        if(mWordRecyclerAdapter == null){
            mUnfilteredDataSet = wordArrayList;
            mWordRecyclerAdapter = new WordRecyclerAdapter(wordArrayList, this, this);
            mWordRecyclerAdapter.getFilter().filter(dataSetMode);
            recyclerView.setAdapter(mWordRecyclerAdapter);
        }else{
            mWordRecyclerAdapter.getFilter().filter(dataSetMode);
            recyclerView.setAdapter(mWordRecyclerAdapter);
        }
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void refreshWordList(){
        mWordRecyclerAdapter.notifyDataSetChanged();
    }

    //RecyclerView click listeners
    @Override
    public void onObjectClick(View v, String itemId) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(WORD_OBJECT, mHomePresenterImpl.onElementSelected(itemId));
        String layoutType = (String)v.getTag();
        if(layoutType.equals("Search_Layout")){
            intent.putExtra("IMAGE", ImageUtility.getImage((ImageView) v.findViewById(R.id.search_history_image)).toByteArray());
            this.startActivity(intent);
        }else if (layoutType.equals("Card_Layout")){
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
        }else{
            //Obtained from SearchFilterAdapter click
            intent.putExtra("IMAGE", ImageUtility.getImage((ImageView) v.findViewById(R.id.search_row_image)).toByteArray());
            this.startActivity(intent);
        }
    }

    @Override
    public void onObjectMenuClick(View v, String itemId){
        //launch dialog fragment
        CardDialogFragment dialogFragment = new CardDialogFragment();
        Bundle args = new Bundle();
        args.putString("itemId", itemId);
        dialogFragment.setArguments(args);
        dialogFragment.show(getFragmentManager(), TAG);
    }

    //CardView dialog listeners
    @Override
    public void onDialogClickDismiss(CardDialogFragment dialogFragment, String itemId){
        mHomePresenterImpl.onDismissOptionSelected(itemId);
        mWordRecyclerAdapter.removeItem(itemId);
    }

    @Override
    public void onDialogClickFavorite(CardDialogFragment dialogFragment, String itemId){
        mHomePresenterImpl.onFavOptionSelected(itemId);
    }

}
