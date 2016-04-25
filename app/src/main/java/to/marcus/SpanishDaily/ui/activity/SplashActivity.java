package to.marcus.SpanishDaily.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by marcus on 3/4/2016
 * Launch on open
 * configures default option preferences
 */
public class SplashActivity extends AppCompatActivity {
    /// TODO: 4/13/2016 add a custom splashscreen logo
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
