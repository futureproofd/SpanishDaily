package to.marcus.rxtesting.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.ImageButton;
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

/**
 * get the details of a clicked word object
 */
public class DetailActivity extends Activity implements DetailView,
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
    private String txtWord;
    private String txtSoundRef;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        initInjector();
        mDetailPresenterImpl.initPresenter(this);
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
    public void showWordDetails(){
        Word word = getIntent().getParcelableExtra(WORD_OBJECT);
        txtWord = word.getWord();
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
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onGenerated(Palette palette) {
        if(palette != null){
            setWordColorElement(palette.getDarkMutedSwatch());
            setBtnColorElement(palette.getMutedSwatch());
            setBodyColorElement(palette.getLightMutedSwatch());
        }
    }

    private void setWordColorElement(Palette.Swatch swatch){
        if(swatch != null) {
            strWord.setBackgroundColor(swatch.getRgb());
            imgTrans.setColorFilter(swatch.getRgb(), PorterDuff.Mode.MULTIPLY);
            imgExample.setColorFilter(swatch.getRgb(), PorterDuff.Mode.MULTIPLY);
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
}
