package to.marcus.rxtesting.ui.activity;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.transition.Transition;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;
import to.marcus.rxtesting.BaseApplication;
import to.marcus.rxtesting.R;
import to.marcus.rxtesting.injection.component.DaggerWordInteractorComponent;
import to.marcus.rxtesting.injection.module.ActivityModule;
import to.marcus.rxtesting.injection.module.WordInteractorModule;
import to.marcus.rxtesting.model.Word;
import to.marcus.rxtesting.presenter.DetailPresenterImpl;
import to.marcus.rxtesting.presenter.view.DetailView;
import to.marcus.rxtesting.ui.adapter.TransitionAdapter;
import to.marcus.rxtesting.util.TransitionUtility;
import to.marcus.rxtesting.util.UIUtility;

/**
 * get the details of a clicked word object
 */
public class DetailActivity extends AppCompatActivity implements DetailView,
        Palette.PaletteAsyncListener{
    private static final String WORD_OBJECT = "WORD_OBJECT";
    @Inject DetailPresenterImpl mDetailPresenterImpl;
    @Bind(R.id.imgDetailWord)   ImageView imgWord;
    @Bind(R.id.txtDetailWord)   TextView strWord;
    @Bind(R.id.body_container)  LinearLayout bodyContainer;
    @Bind(R.id.txtTranslation)  TextView strTranslation;
    @Bind(R.id.txtExampleEn)    TextView strExampleEN;
    @Bind(R.id.txtExampleESP)   TextView strExampleESP;
    @Bind(R.id.btn_narration)   to.marcus.rxtesting.ui.widgets.FloatingActionButton btnNarration;
    @Bind(R.id.trans_img)       ImageView imgTrans;
    @Bind(R.id.example_img)     ImageView imgExample;
    private String txtSoundRef;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        initInjector();
        mDetailPresenterImpl.initPresenter(this);
        initWindowTransition();
        showWordDetails();

        btnNarration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailActivity.this, "play sound", Toast.LENGTH_SHORT).show();
                mDetailPresenterImpl.onElementSelected(txtSoundRef);
            }
        });
    }

    private void initInjector(){
        BaseApplication baseApplication = (BaseApplication)getApplication();
        DaggerWordInteractorComponent.builder()
                .activityModule(new ActivityModule(this))
                .baseAppComponent(baseApplication.getBaseAppComponent())
                .wordInteractorModule(new WordInteractorModule())
                .build().injectDetail(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showNotification(String notification){
        Toast.makeText(this, notification, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showWordDetails(){
        Word word = getIntent().getParcelableExtra(WORD_OBJECT);
        txtSoundRef = word.getSoundRef();
        byte[] byteArray = getIntent().getByteArrayExtra("IMAGE");
        Bitmap detailBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        Palette.generateAsync(detailBitmap, this);
        imgWord.setImageBitmap(detailBitmap);
        strWord.setText(word.getWord());
        strTranslation.setText(word.getTranslation());
        strExampleEN.setText(word.getExampleEN());
        strExampleESP.setText(word.getExampleESP());
    }

    //todo cache mp3?
    @Override
    public void onClickPlayback(byte[] soundByte) {
        try{
            File tempMp3 = File.createTempFile("temp","mp3");
            tempMp3.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempMp3);
            fos.write(soundByte);
            fos.close();
            // FIXME: 9/25/2015 expensive object
            MediaPlayer mediaPlayer = new MediaPlayer();
            // FIXME: 9/25/2015 expensive object
            FileInputStream fis = new FileInputStream(tempMp3);
            mediaPlayer.setDataSource(fis.getFD());
            mediaPlayer.prepare();
            mediaPlayer.start();
          //  mediaPlayer.release();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onGenerated(Palette palette) {
        if(palette != null){
            final Palette.Swatch darkVibrantSwatch    = palette.getDarkVibrantSwatch();
            final Palette.Swatch darkMutedSwatch      = palette.getDarkMutedSwatch();
            final Palette.Swatch lightVibrantSwatch   = palette.getLightVibrantSwatch();
            final Palette.Swatch lightMutedSwatch     = palette.getLightMutedSwatch();
            final Palette.Swatch mutedSwatch          = palette.getMutedSwatch();

            final Palette.Swatch wordElementColor = (darkMutedSwatch != null)
                    ? darkMutedSwatch : darkVibrantSwatch;

            final Palette.Swatch btnElementColor = (mutedSwatch != null)
                    ? mutedSwatch : lightMutedSwatch;

            final Palette.Swatch bodyElementColor = (lightMutedSwatch != null)
                    ? lightMutedSwatch : lightVibrantSwatch;

            setWordColorElement(wordElementColor);
            setBtnColorElement(btnElementColor);
            setBodyColorElement(bodyElementColor);
        }
    }

    private void setWordColorElement(Palette.Swatch swatch){
        if(swatch != null) {
            strWord.setBackgroundColor(swatch.getRgb());
        }
    }

    private void setBtnColorElement(Palette.Swatch swatch){
        if(swatch != null){
            btnNarration.setBackgroundColor(swatch.getRgb());
        }
    }

    private void setBodyColorElement(Palette.Swatch swatch){
        if(swatch != null){
            bodyContainer.setBackgroundColor(swatch.getRgb());
        }
    }

    private void initWindowTransition(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            UIUtility.setTranslucentStatusBar(this);
            initEnterTransition();
        }else{
            btnNarration.animate().alpha(1.0f);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initEnterTransition() {
        getWindow().setEnterTransition(TransitionUtility.makeEnterTransition());
        getWindow().getEnterTransition().addListener(new TransitionAdapter() {
            @Override
            public void onTransitionEnd(Transition transition) {
                btnNarration.animate().alpha(1.0f);
                getWindow().getEnterTransition().removeListener(this);
            }
        });
    }

}
