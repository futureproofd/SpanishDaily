package to.marcus.rxtesting.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
import to.marcus.rxtesting.presenter.DetailPresenterImpl;
import to.marcus.rxtesting.presenter.view.BaseView;

/**
 * get the details of a clicked word object
 */
public class DetailActivity extends Activity implements BaseView {
    private static final String WORD_OBJECT = "WORD_OBJECT";
    @Inject DetailPresenterImpl mDetailPresenterImpl;
    @Bind(R.id.imgDetailWord)   ImageView imgWord;
    @Bind(R.id.txtDetailWord)   TextView strWord;
    @Bind(R.id.txtTranslation)  TextView strTranslation;
    @Bind(R.id.txtExampleEn)    TextView strExampleEN;
    @Bind(R.id.txtExampleESP)   TextView strExampleESP;
    @Bind(R.id.btn_narration)   ImageButton btnNarration;
    private String txtWord;
    private String txtSoundRef;
    private static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        initInjector();
        mDetailPresenterImpl.initPresenter(this);
        showWordDetails();

        btnNarration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailActivity.this, "play sound",Toast.LENGTH_SHORT).show();
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
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imgWord.setImageBitmap(bitmap);
        strWord.setText(word.getWord());
        strTranslation.setText(word.getTranslation());
        strExampleEN.setText(word.getExampleEN());
        strExampleESP.setText(word.getExampleESP());
    }

    @Override
    public void showWordList(ArrayList<Word> words) {

    }

    @Override
    public void updateWordList() {

    }

    @Override
    public Activity getActivity(){
        return this;
    }

    // FIXME: 9/25/2015 should be a view Interface method - needs a cache
    public static void playSound(byte[] soundByte){
        try{
            File tempMp3 = File.createTempFile("test","mp3");
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
            String s = ex.toString();
            ex.printStackTrace();
        }

    }

}
