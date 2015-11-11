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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import to.marcus.rxtesting.ui.fragment.OptionsFragment;
import to.marcus.rxtesting.ui.fragment.FavoritesFragment;

/**
 * Created by marcus on 10/19/2015.
 * BaseActivity to setup navigation framework
 */
public class BaseActivity extends AppCompatActivity implements BaseView{
    private final static String TAG = BaseActivity.class.getSimpleName();
    private final static int REQ_CODE = 1337;
    private final static String ALARM_ACTION = "ALARM_ACTION";
    private static SharedPreferences.OnSharedPreferenceChangeListener mListener;
    private static SharedPreferences sharedPrefs;
    @Bind(R.id.toolbar)         Toolbar mToolbar;
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

    private void setupDrawer(){
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView){

            }
            @Override
            public void onDrawerClosed(View view){

            }
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
                break;
            case R.id.nav_favorites:
                fragmentClass = FavoritesFragment.class;
                break;
            default:
                fragmentClass = OptionsFragment.class;
        }
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

}
