package to.marcus.rxtesting.ui.activity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;
import to.marcus.rxtesting.BaseApplication;
import to.marcus.rxtesting.R;
import to.marcus.rxtesting.injection.component.DaggerWordInteractorComponent;
import to.marcus.rxtesting.injection.module.ActivityModule;
import to.marcus.rxtesting.injection.module.WordInteractorModule;
import to.marcus.rxtesting.presenter.BasePresenterImpl;
import to.marcus.rxtesting.presenter.view.BaseView;
import to.marcus.rxtesting.service.ServiceController;
import to.marcus.rxtesting.service.WordNotificationService;
import to.marcus.rxtesting.ui.adapter.RecyclerViewItemClickListener;
import to.marcus.rxtesting.ui.adapter.SearchAdapterClickListener;
import to.marcus.rxtesting.ui.adapter.SearchFilterAdapter;
import to.marcus.rxtesting.ui.fragment.OptionsFragment;

/**
 * Created by marcus on 10/19/2015.
 * BaseActivity to setup navigation framework
 */
public class BaseActivity extends AppCompatActivity implements BaseView, TextWatcher, SearchAdapterClickListener{
    private final static String TAG = BaseActivity.class.getSimpleName();
    private final static int REQ_CODE = 1337;
    private boolean mKeyboardStatus;
    private final static String ALARM_ACTION = "ALARM_ACTION";
    private static SharedPreferences.OnSharedPreferenceChangeListener mListener;
    private static SharedPreferences sharedPrefs;
    private SearchFilterAdapter mSearchFilterAdapter;
    @Bind(R.id.toolbar)         Toolbar mToolbar;
    @Bind(R.id.search_query_btn)ImageView mSearchBtn;
    @Bind(R.id.search_clear_btn)ImageView mSearchClrBtn;
    @Bind(R.id.search_box)      AutoCompleteTextView mSearchBox;
    @Bind(R.id.drawer_layout)   DrawerLayout mDrawerLayout;
    @Bind(R.id.nav_drawer)      NavigationView mNavDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    @Inject BasePresenterImpl mBasePresenterImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);
        initInjector();
        mBasePresenterImpl.initPresenter(this);
        initToolbar();
        initSearchBox();
        setupDrawer();
        setupDrawerNav(mNavDrawer);
        initSharedPrefsListener();
        initNotificationService();
    }

    private void initInjector(){
        BaseApplication baseApplication = (BaseApplication)getApplication();
        DaggerWordInteractorComponent.builder()
                .activityModule(new ActivityModule(this))
                .baseAppComponent(baseApplication.getBaseAppComponent())
                .wordInteractorModule(new WordInteractorModule())
                .build().injectBase(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPrefs.registerOnSharedPreferenceChangeListener(mListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sharedPrefs.unregisterOnSharedPreferenceChangeListener(mListener);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            getFragmentManager().popBackStack();
        }
        if (!isKeyboardActive()){
            showKeyboard();
        }else{
            dismissKeyboard();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    private void initToolbar(){
        setSupportActionBar(mToolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Spanish WOTD");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    private void initSearchBox(){
        if(mSearchFilterAdapter == null){
            mSearchFilterAdapter = new SearchFilterAdapter(this, R.layout.activity_base,
                    R.id.search_row_text, mBasePresenterImpl.getWordList(),this);
            mSearchBox.setAdapter(mSearchFilterAdapter);
            mSearchBox.addTextChangedListener(this);
        }
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                HomeActivity.instance.selectDataSet("search");
                getSupportActionBar().setTitle("");
                mSearchBtn.setVisibility(View.GONE);
                mSearchClrBtn.setVisibility(View.VISIBLE);
                mSearchBox.setVisibility(View.VISIBLE);
                mSearchBox.requestFocus();
            }
        });
        mSearchClrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchClrBtn.setVisibility(View.GONE);
                mSearchBtn.setVisibility(View.VISIBLE);
                mSearchBox.setVisibility(View.GONE);
            }
        });
        mSearchBox.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!isKeyboardActive()) {
                    showKeyboard();
                }else{
                    dismissKeyboard();
                    mSearchBox.clearFocus();
                }
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count){
        if(!s.toString().equals("")){
            mSearchFilterAdapter.getFilter().filter(s.toString());
        }else if(start == 0 && before > 0 && count == 0){
            mSearchFilterAdapter.notifyDataSetInvalidated();
        }
    }

    @Override
    public void afterTextChanged(Editable s){}

    //SearchAdapter result click listener
    @Override
    public void onSearchResultClick(View v, String itemId){
        mBasePresenterImpl.setSearched(itemId);
        HomeActivity.instance.onObjectClick(v, itemId);
    }

    private void setupDrawer(){
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView){}
            @Override
            public void onDrawerClosed(View view){}
        };
    }

    private void setupDrawerNav(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem){
        android.app.Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_options:
                fragmentClass = OptionsFragment.class;
                try{
                    fragment = (android.app.Fragment) fragmentClass.newInstance();
                }catch (Exception e) {
                    e.printStackTrace();
                }
                // Insert the fragment by replacing any existing fragment
                getFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.nav_favorites:
                HomeActivity.instance.selectDataSet("favorites");
                break;
            case R.id.nav_home:
                HomeActivity.instance.selectDataSet("unfiltered");
                break;
            case R.id.nav_history:
                HomeActivity.instance.selectDataSet("dismissed");
                break;
            case R.id.nav_search:
                HomeActivity.instance.selectDataSet("search");
            default:
                fragmentClass = OptionsFragment.class;
        }
        // Highlight the selected item, update the title, and close the drawer
        menuItem.setChecked(true);
        mDrawerLayout.closeDrawers();
        getSupportActionBar().setTitle(menuItem.getTitle());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void initSharedPrefsListener(){
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mListener = new SharedPreferences.OnSharedPreferenceChangeListener(){
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPrefs, String key){
                mBasePresenterImpl.onPrefSelected(key, sharedPrefs.getBoolean(key, false));
            }
        };
    }

    private void initNotificationService(){
        Context appContext = getApplicationContext();
        boolean alarmActive = (PendingIntent.getService(appContext
                ,REQ_CODE
                ,new Intent(appContext, WordNotificationService.class).setAction(ALARM_ACTION)
                ,PendingIntent.FLAG_NO_CREATE) != null);
        if(mBasePresenterImpl.isNotification()){
            if(!alarmActive){
                ServiceController.queueService(appContext);
            }
        }else{
            cancelNotification();
        }
    }

    private void cancelNotification(){
        Context appContext = getApplicationContext();
        PendingIntent.getService(appContext
                ,REQ_CODE
                ,new Intent(appContext, WordNotificationService.class).setAction(ALARM_ACTION)
                ,PendingIntent.FLAG_UPDATE_CURRENT)
                .cancel();
    }

    //todo maybe put these in a utility class
    public void dismissKeyboard(){
        InputMethodManager imm =(InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mSearchBox.getWindowToken(), 0);
    }

    public void showKeyboard(){
        InputMethodManager imm =(InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        mKeyboardStatus = true;
    }

    private boolean isKeyboardActive(){
        return mKeyboardStatus;
    }

}
