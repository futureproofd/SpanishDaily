package to.marcus.rxtesting.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by marcus on 3/4/2016
 * Launch only upon new install
 *  configures default option preferences
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
