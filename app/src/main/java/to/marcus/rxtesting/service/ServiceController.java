package to.marcus.rxtesting.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

/**
 * Created by marcus on 31/10/15
 * Controls the frequency (time) for when the service runs
 * Accounts for jitter, by adding up to an hour offset, depending on random switch value
 */
public class ServiceController {
    private static final Long ALARM_START;
    private static Random rand = new Random();
    private static final int REQ_CODE = 1337;
    private static final String ALARM_ACTION = "ALARM_ACTION";

    static{
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        calendar.setTimeInMillis(calendar.getTimeInMillis());
        calendar.set(Calendar.HOUR_OF_DAY, timeInterval(1,8));
        calendar.set(Calendar.MINUTE, timeInterval(1,59));
        ALARM_START = calendar.getTimeInMillis();
    }

    public static void startService(Context context){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, WordNotificationService.class).setAction(ALARM_ACTION);
        PendingIntent pendingIntent = PendingIntent.getService(context
                , REQ_CODE
                , intent
                , PendingIntent.FLAG_CANCEL_CURRENT
            );
        Long offset = ALARM_START;
        switch(timeInterval(0,3)){
            case 0: offset = ALARM_START + 900000;  break;
            case 1: offset = ALARM_START + 1800000; break;
            case 2: offset = ALARM_START + 2700000; break;
            case 3: offset = ALARM_START + 3600000; break;
        }
        alarmManager.setInexactRepeating(AlarmManager.RTC
                ,offset
                ,AlarmManager.INTERVAL_DAY
                ,pendingIntent
        );
    }

    public static int timeInterval(int min, int max){
        return rand.nextInt(max - min) + min;
    }

}
