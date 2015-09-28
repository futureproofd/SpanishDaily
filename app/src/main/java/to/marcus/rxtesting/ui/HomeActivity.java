package to.marcus.rxtesting.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.io.ByteArrayOutputStream;
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
import to.marcus.rxtesting.presenter.HomePresenterImpl;
import to.marcus.rxtesting.presenter.view.BaseView;
import to.marcus.rxtesting.ui.adapter.RecyclerViewItemClickListener;
import to.marcus.rxtesting.ui.adapter.WordRecyclerAdapter;

/*
* Main, list (card) view
 */

public class HomeActivity extends Activity implements BaseView, RecyclerViewItemClickListener{
    public final String TAG = HomeActivity.class.getSimpleName();
    private static final String WORD_OBJECT = "WORD_OBJECT";
    @Bind(R.id.recycler_view) RecyclerView recyclerView;

    @Inject
    HomePresenterImpl mHomePresenterImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initInjector();
        initRecyclerView();
        mHomePresenterImpl.initPresenter(this);
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
                .build().injectHome(this);
    }

    private void initRecyclerView(){
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
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
        WordRecyclerAdapter wordRecyclerAdapter = new WordRecyclerAdapter(wordArrayList, this);
        recyclerView.setAdapter(wordRecyclerAdapter);
    }

    @Override
    public void showWordDetails(){
    }

    @Override
    public void updateWordList(){
        Log.i(TAG, "updated adapter dataset");
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public Activity getActivity(){
        return this;
    }

    /*
    RecyclerView click listener
     */
    @Override
    public void onObjectClick(View v, int position) {
        Word detailWord = mHomePresenterImpl.onElementSelected(position);
        Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
        intent.putExtra(WORD_OBJECT, detailWord);
        //create bitmap intent
            ImageView wordImage = (ImageView)v.findViewById(R.id.imgWord);
            Drawable mDrawable = wordImage.getDrawable();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ((BitmapDrawable)mDrawable).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
        intent.putExtra("IMAGE", stream.toByteArray());
        //animateTransition(v, intent);
        this.startActivity(intent);
    }

    private void animateTransition(View view, Intent intent){
        ImageView wordImage = (ImageView)view.findViewById(R.id.imgWord);
        LinearLayout wordString = (LinearLayout)view.findViewById(R.id.wordNameHolder);

        String transitionImgName = this.getString(R.string.transition_img);
        String transitionWordName = this.getString(R.string.transition_word);
        Pair<View, String> p1 = Pair.create((View) wordImage, transitionImgName);
        Pair<View, String> p2 = Pair.create((View) wordString, transitionWordName);

        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(HomeActivity.this,p1,p2);
        ActivityCompat.startActivity(this, intent, options.toBundle());
    }
}
