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
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import to.marcus.rxtesting.presenter.view.HomeView;
import to.marcus.rxtesting.ui.adapter.RecyclerViewItemClickListener;
import to.marcus.rxtesting.ui.adapter.RecyclerViewMenuClickListener;
import to.marcus.rxtesting.ui.adapter.WordRecyclerAdapter;

/*
* Main, list (card) view
 */

public class HomeActivity extends Activity implements HomeView,RecyclerViewItemClickListener,
        RecyclerViewMenuClickListener{
    public final String TAG = HomeActivity.class.getSimpleName();
    private static final String WORD_OBJECT = "WORD_OBJECT";
    //NavDrawer
    @Bind(R.id.navList) ListView navList;
    @Bind(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mNavAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    //RecyclerView
    @Bind(R.id.recycler_view) RecyclerView recyclerView;
    @Inject HomePresenterImpl mHomePresenterImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initInjector();
        initRecyclerView();
        mHomePresenterImpl.initPresenter(this);
        //Navigation Drawer
        addDrawerItems();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        setupDrawer();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
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
        }else if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        WordRecyclerAdapter wordRecyclerAdapter = new WordRecyclerAdapter(wordArrayList, this, this);
        recyclerView.setAdapter(wordRecyclerAdapter);
    }

    @Override
    public void updateWordList(){
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    /*
    RecyclerView click listeners
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

    @Override
    public void onObjectMenuClick(View v, int position){
        Log.i(TAG, "clicked for dialog");
        //launch dialog fragment
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

    /*
    Navigation Drawer setup
     */
    private void addDrawerItems() {
        String[] optionsArray = { "Opciones", "Buscar", "Favoritos", "About"};
        mNavAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, optionsArray);
        navList.setAdapter(mNavAdapter);
        navList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "clicked " + position);
            }
        });
    }

    private void setupDrawer(){
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout
                ,R.string.drawer_open, R.string.drawer_close){
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(R.string.navigation_title);
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view){
                super.onDrawerClosed(view);
                getActionBar().setTitle(getTitle().toString());
                invalidateOptionsMenu();
            }

        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }
}
