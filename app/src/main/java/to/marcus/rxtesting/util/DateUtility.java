package to.marcus.rxtesting.util;

import org.joda.time.DateTime;
import org.joda.time.Days;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by marcus on 9/11/2015
 */
public class DateUtility {
    private static final DateFormat formatted = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
    private static Date newDate;
    private static DateTime today = new DateTime();

    public static Date formatDateString(String date){
        try{
            newDate = formatted.parse(date);
        }catch (ParseException p){
            p.printStackTrace();
        }
        return newDate;
    }

    public static boolean isWordStale(Date wordDate){
        DateTime word = new DateTime(wordDate);
        int diff = Days.daysBetween(today.minusHours(7), word).getDays();
        word = null;
        if(diff < 0){
            return true;
        }else{
            return false;
        }
    }

}
