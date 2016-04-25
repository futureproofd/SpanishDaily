package to.marcus.SpanishDaily.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import javax.inject.Inject;

import rx.functions.Action1;
import to.marcus.SpanishDaily.BaseApplication;
import to.marcus.SpanishDaily.R;
import to.marcus.SpanishDaily.data.interactor.WordInteractorImpl;
import to.marcus.SpanishDaily.injection.component.DaggerWordInteractorComponent;
import to.marcus.SpanishDaily.injection.module.ActivityModule;
import to.marcus.SpanishDaily.injection.module.WordInteractorModule;
import to.marcus.SpanishDaily.model.Word;
import to.marcus.SpanishDaily.model.factory.WordFactoryImpl;
import to.marcus.SpanishDaily.model.repository.RepositoryImpl;
import to.marcus.SpanishDaily.presenter.HomePresenterImpl;
import to.marcus.SpanishDaily.ui.activity.DetailActivity;
import to.marcus.SpanishDaily.util.ImageUtility;

/**
 * Created by marcus on 10/30/2015
 */
public class WordNotificationService extends IntentService {
    private static final String TAG = WordNotificationService.class.getSimpleName();
    private myTarget mTarget;
    private PendingIntent resultPendingIntent;
    private Handler uiHandler;
    @Inject HomePresenterImpl mHomePresenterImpl;
    @Inject RepositoryImpl mRepository;
    @Inject WordInteractorImpl mWordInteractor;

    public WordNotificationService(){
        super("WordNotificationService");
    }

    @Override
    public void onCreate(){
        super.onCreate();
        initInjector();
        mTarget = new myTarget();
    }

    //Start handler in this method to use main thread
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        uiHandler = new Handler();
        return super.onStartCommand(intent, flags, startId);
    }

    private void initInjector(){
        BaseApplication baseApplication = (BaseApplication)getApplication();
        DaggerWordInteractorComponent.builder()
                .activityModule(new ActivityModule(this))
                .baseAppComponent(baseApplication.getBaseAppComponent())
                .wordInteractorModule(new WordInteractorModule())
                .build().injectService(this);
    }

    @Override
    protected void onHandleIntent(Intent intent){
        synchronized (this){
            if (mHomePresenterImpl.isWordStale()){
                mWordInteractor.execute()
                    .subscribe(
                            new Action1<ArrayList<String>>() {
                                @Override
                                public void call(ArrayList<String> elements) {
                                    Word word = WordFactoryImpl.Word.newWordInstance(elements);
                                    mRepository.addWord(word);
                                }
                            },
                            new Action1<Throwable>() {
                                @Override
                                public void call(Throwable error) {
                                    Log.i(TAG, "not received");
                                }
                            }
                    );
                try{
                    wait(8000);
                    startImageThread();
                    wait(8000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                buildNotification();
            } else {
                Log.i(TAG, "word is already up-to-date");
            }
        }
    }

    private void startImageThread(){
        uiHandler.post(new Runnable(){
            @Override
            public void run(){
                Picasso.with(getApplicationContext())
                        .load(mRepository.getLatestWord().getImgUrl())
                        .into(mTarget);
            }
        });
    }

    //Helpers
    private void buildIntent(ByteArrayOutputStream image){
        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra("WORD_OBJECT", mRepository.getLatestWord());
        detailIntent.putExtra("IMAGE", image.toByteArray());

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
        stackBuilder.addParentStack(DetailActivity.class);
        stackBuilder.addNextIntent(detailIntent);

        resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void buildNotification(){
        Notification.Builder n = new Notification.Builder(this);
        n.setContentTitle(getString(R.string.notification_title))
                .setContentText(mRepository.getLatestWord().getWord())
                .setSmallIcon(R.drawable.ic_stat_book_256)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent);

        NotificationManager notificationManager =
                (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, n.build());
    }

    /*
    Class to handle imageThread
     */
    private final class myTarget implements Target{
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            buildIntent(ImageUtility.convertImage(bitmap));
        }
        @Override
        public void onBitmapFailed(Drawable errorDrawable){}

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable){}
    }

}
