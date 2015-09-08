package to.marcus.rxtesting;

import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import javax.inject.Inject;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import to.marcus.rxtesting.data.ParserTest;
import to.marcus.rxtesting.injection.component.DaggerModelComponent;
import to.marcus.rxtesting.injection.module.ActivityModule;
import to.marcus.rxtesting.injection.module.ModelModule;
import to.marcus.rxtesting.presenter.TestPresenter;


public class HomeActivity extends ActionBarActivity {
    public final String TAG = HomeActivity.class.getSimpleName();
    ParserTest mParserTest;

    @Inject
    TestPresenter mTestPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initInjector();
        mTestPresenter.initPresenter();
    }

    public void enableStrictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
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
        DaggerModelComponent.builder()
                .activityModule(new ActivityModule(this))
                .baseAppComponent(baseApplication.getBaseAppComponent())
                .modelModule(new ModelModule())
                .build().inject(this);
    }
}
