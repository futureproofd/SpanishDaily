package to.marcus.rxtesting.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.ListView;
import android.widget.Toast;
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
import to.marcus.rxtesting.util.ImageUtility;

/*
* Main, list (card) view
 */
public class HomeActivity extends Activity implements HomeView
        ,RecyclerViewItemClickListener
        ,RecyclerViewMenuClickListener
        ,CardDialogFragment.CardDialogListener{
    public final String TAG = HomeActivity.class.getSimpleName();
    private static final String WORD_OBJECT = "WORD_OBJECT";
    //NavDrawer
    @Bind(R.id.navList) ListView navList;
    @Bind(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mNavAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    //RecyclerView
    @Bind(R.id.recycler_view) RecyclerView recyclerView;
    private WordRecyclerAdapter mWordRecyclerAdapter;
    @Inject HomePresenterImpl mHomePresenterImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initInjector();
        initRecyclerView();
        mHomePresenterImpl.initPresenter(this);
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

    //View Implementations
    @Override
    public void showLoading() {
        Log.i(TAG, "loading initiated");
    }

    @Override
    public void hideLoading() {
        Log.i(TAG, "loading stopped");
    }

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
    public void refreshWordList(){
        mWordRecyclerAdapter.notifyDataSetChanged();
    }

    //RecyclerView click listeners
    @Override
    public void onObjectClick(View v, int position) {
        Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
        intent.putExtra(WORD_OBJECT, mHomePresenterImpl.onElementSelected(position));
        intent.putExtra("IMAGE",ImageUtility.getImage((ImageView)v.findViewById(R.id.imgWord)).toByteArray());
        //animateTransition(v, intent);
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

    //Navigation Drawer setup
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
