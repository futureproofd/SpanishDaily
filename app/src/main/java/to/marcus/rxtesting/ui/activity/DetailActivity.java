package to.marcus.rxtesting.ui.activity;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.leakcanary.RefWatcher;

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
    private static final String TAG = DetailActivity.class.getSimpleName();
    private static final String WORD_OBJECT = "WORD_OBJECT";
    @Inject DetailPresenterImpl mDetailPresenterImpl;
    @Bind(R.id.detail_toolbar)  Toolbar mToolbar;
    @Bind(R.id.imgDetailWord)   ImageView imgWord;
    @Bind(R.id.nav_back_arrow)  ImageView btnNavBk;
    @Bind(R.id.txtDetailWord)   TextView strWord;
    @Bind(R.id.body_container)  LinearLayout bodyContainer;
    @Bind(R.id.txtTranslation)  TextView strTranslation;
    @Bind(R.id.txtExampleEn)    TextView strExampleEN;
    @Bind(R.id.txtExampleESP)   TextView strExampleESP;
    @Bind(R.id.btn_narration)   FloatingActionButton btnNarration;
    @Bind(R.id.trans_img)       ImageView imgTrans;
    @Bind(R.id.example_img)     ImageView imgExample;
    private String txtSoundRef;
    private Word mWord;
    MenuItem actionFavorite;
    private int mActionFavoriteColor;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        initInjector();
        mDetailPresenterImpl.initPresenter(this);
        initWindowTransition();
        showWordDetails();
        initToolbar();


        btnNarration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailActivity.this, R.string.playback_toast, Toast.LENGTH_SHORT).show();
                mDetailPresenterImpl.onElementSelected(txtSoundRef);
            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        /*
        RefWatcher refWatcher = BaseApplication.getRefWatcher(this);
        refWatcher.watch(txtSoundRef);
        refWatcher.watch(imgWord);
        refWatcher.watch(this);
        */
    }

    private void initInjector(){
        BaseApplication baseApplication = (BaseApplication)getApplication();
        DaggerWordInteractorComponent.builder()
            .activityModule(new ActivityModule(this))
            .baseAppComponent(baseApplication.getBaseAppComponent())
            .wordInteractorModule(new WordInteractorModule())
            .build().injectDetail(this);
    }

    private void initToolbar() {
        mToolbar.inflateMenu(R.menu.menu_details);
        actionFavorite = mToolbar.getMenu().findItem(R.id.action_favorite);

        if (mWord.getFavorite() == 1) {
            actionFavorite.setIcon(R.drawable.ic_star_white_24dp);
        } else {
            actionFavorite.setIcon(R.drawable.ic_star_outline_white_24dp);
        }

        if(mToolbar != null){
            mToolbar.setTitle("");
            btnNavBk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int id = item.getItemId();
                    switch(id){
                        case R.id.action_favorite:
                            if (mWord.getFavorite() == 1){
                                actionFavorite.setIcon(R.drawable.ic_star_outline_white_24dp);
                                showNotification(getString(R.string.notification_removed));
                                mWord.setFavorite(0);
                                mDetailPresenterImpl.onFavoriteToggled(mWord);
                            }else{
                                actionFavorite.setIcon(R.drawable.ic_star_white_24dp);
                                showNotification(getString(R.string.notification_added));
                                mWord.setFavorite(1);
                                mDetailPresenterImpl.onFavoriteToggled(mWord);
                            }
                            actionFavorite.getIcon().setColorFilter(new PorterDuffColorFilter(mActionFavoriteColor, PorterDuff.Mode.MULTIPLY));
                            break;
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public void showLoading(){}

    @Override
    public void hideLoading(){}

    @Override
    public void showNotification(String notification){
        Toast.makeText(this, notification, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showWordDetails(){
        mWord = getIntent().getParcelableExtra(WORD_OBJECT);
        txtSoundRef = mWord.getSoundRef();
        byte[] byteArray = getIntent().getByteArrayExtra("IMAGE");
        Bitmap detailBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        Palette.generateAsync(detailBitmap, this);
        imgWord.setImageBitmap(detailBitmap);
        strWord.setText(mWord.getWord());
        strTranslation.setText(mWord.getTranslation());
        strExampleEN.setText(UIUtility.formatStringByExample(mWord.getExampleEN()));
        strExampleESP.setText(UIUtility.formatStringByExample(mWord.getExampleESP()));
    }

    //todo cache mp3?
    @Override
    public void onClickPlayback(byte[] soundByte) {
        try{
            File tempMp3 = File.createTempFile(getString(R.string.temp_filename),getString(R.string.filetype_mp3));
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
            mActionFavoriteColor = swatch.getRgb();
            strWord.setBackgroundColor(swatch.getRgb());
            imgTrans.setColorFilter(swatch.getRgb());
            imgExample.setColorFilter(swatch.getRgb());
            btnNavBk.setColorFilter(swatch.getRgb());
            actionFavorite.getIcon().setColorFilter(new PorterDuffColorFilter(swatch.getRgb(), PorterDuff.Mode.MULTIPLY));
        }
    }

    private void setBtnColorElement(Palette.Swatch swatch){
        if(swatch != null){
            btnNarration.setBackgroundTintList(ColorStateList.valueOf(swatch.getRgb()));
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
