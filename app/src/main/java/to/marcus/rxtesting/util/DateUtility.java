package to.marcus.rxtesting.util;

import org.joda.time.DateTime;
import org.joda.time.Days;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import to.marcus.rxtesting.model.Word;
import to.marcus.rxtesting.ui.adapter.SectionedGridRecyclerViewAdapter;

/**
 * Created by marcus on 9/11/2015
 */
public class DateUtility {
    private static final DateFormat formatted = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
    private static Date newDate;
    private static DateTime today = new DateTime();
    //Defaults for word Sorting methods
    private static List<SectionedGridRecyclerViewAdapter.Section> sectionList = new ArrayList<>();
    private static String month = "";
    private static String previousMonth = "";
    private static String startMonth = "";
    private static Map<String, Integer> monthHashMap = new LinkedHashMap<>();
    private static ArrayList<Word> mWords = new ArrayList<>();
    private static final String JANUARY = "January";
    private static final String FEBRUARY = "February";
    private static final String MARCH = "March";
    private static final String APRIL = "April";
    private static final String MAY = "May";
    private static final String JUNE = "June";
    private static final String JULY = "July";
    private static final String AUGUST = "August";
    private static final String SEPTEMBER = "September";
    private static final String OCTOBER = "October";
    private static final String NOVEMBER = "November";
    private static final String DECEMBER = "December";

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
        int diff = Days.daysBetween(today, word).getDays();
        word = null;
        return diff < 0;
    }

    /*
        Helper methods for building word Sections
     */
    //Get month of first word in array
    private static String getStartMonth(ArrayList<Word> words){
        startMonth = words.get(0).getDate();
        return startMonth.substring(0, startMonth.indexOf(" "));
    }

    //add to hashMap
    private static void buildMonthMappings(String month, int position){
        if(!getStartMonth(mWords).equals(month)){
            monthHashMap.put(month, position);
        }else{
            monthHashMap.put(month, 0);
        }
    }

    //Build Sections, sorted by month
    public static List<SectionedGridRecyclerViewAdapter.Section> sortWordsByMonth(ArrayList<Word> words){
        //reset list
        if(sectionList.size() > 0)
            sectionList.clear();

        int i = 0;
        //Sort array by Month
        mWords = words;
        Collections.sort(mWords);

        while(i < mWords.size()){
            month = mWords.get(i).getDate();
            month = month.substring(0, month.indexOf(" "));
            if(!previousMonth.equals(month)) {
                switch (month) {
                    case JANUARY:
                        buildMonthMappings(JANUARY, i);
                        previousMonth = month;
                        break;
                    case FEBRUARY:
                        buildMonthMappings(FEBRUARY, i);
                        previousMonth = month;
                        break;
                    case MARCH:
                        buildMonthMappings(MARCH, i);
                        break;
                    case APRIL:
                        buildMonthMappings(APRIL, i);
                        break;
                    case MAY:
                        buildMonthMappings(MAY, i);
                        break;
                    case JUNE:
                        buildMonthMappings(JUNE, i);
                        break;
                    case JULY:
                        buildMonthMappings(JULY, i);
                        break;
                    case AUGUST:
                        buildMonthMappings(AUGUST, i);
                        break;
                    case SEPTEMBER:
                        buildMonthMappings(SEPTEMBER, i);
                        break;
                    case OCTOBER:
                        buildMonthMappings(OCTOBER, i);
                        break;
                    case NOVEMBER:
                        buildMonthMappings(NOVEMBER, i);
                        break;
                    case DECEMBER:
                        buildMonthMappings(DECEMBER, i);
                        break;
                }
            }
            i++;
        }

        for(Map.Entry<String, Integer> entry : monthHashMap.entrySet()){
            switch (entry.getKey()){
                case JANUARY:
                    if(entry.getValue() == 0 && !getStartMonth(mWords).equals(JANUARY)){
                        monthHashMap.remove(JANUARY);
                    }
                    break;
                case FEBRUARY:
                    if(entry.getValue() == 0 && !getStartMonth(mWords).equals(FEBRUARY)){
                        monthHashMap.remove(FEBRUARY);
                    }
                    break;
                case MARCH:
                    if(entry.getValue() == 0 && !getStartMonth(mWords).equals(MARCH)){
                        monthHashMap.remove(MARCH);
                    }
                    break;
                case APRIL:
                    if(entry.getValue() == 0 && !getStartMonth(mWords).equals(APRIL)){
                        monthHashMap.remove(APRIL);
                    }
                    break;
                case MAY:
                    if(entry.getValue() == 0 && !getStartMonth(mWords).equals(MAY)){
                        monthHashMap.remove(MAY);
                    }
                    break;
                case JUNE:
                    if(entry.getValue() == 0 && !getStartMonth(mWords).equals(JUNE)){
                        monthHashMap.remove(JUNE);
                    }
                    break;
                case JULY:
                    if(entry.getValue() == 0 && !getStartMonth(mWords).equals(JULY)){
                        monthHashMap.remove(JULY);
                    }
                    break;
                case AUGUST:
                    if(entry.getValue() == 0 && !getStartMonth(mWords).equals(AUGUST)){
                        monthHashMap.remove(AUGUST);
                    }
                    break;
                case SEPTEMBER:
                    if(entry.getValue() == 0 && !getStartMonth(mWords).equals(SEPTEMBER)){
                        monthHashMap.remove(SEPTEMBER);
                    }
                    break;
                case OCTOBER:
                    if(entry.getValue() == 0 && !getStartMonth(mWords).equals(OCTOBER)){
                        monthHashMap.remove(OCTOBER);
                    }
                    break;
                case NOVEMBER:
                    if(entry.getValue() == 0 && !getStartMonth(mWords).equals(NOVEMBER)){
                        monthHashMap.remove(NOVEMBER);
                    }
                    break;
                case DECEMBER:
                    if(entry.getValue() == 0 && !getStartMonth(mWords).equals(DECEMBER)){
                        monthHashMap.remove(DECEMBER);
                    }
                    break;
            }
        }
        for(Map.Entry<String, Integer> entry : monthHashMap.entrySet()){
            sectionList.add(new SectionedGridRecyclerViewAdapter.Section(entry.getValue(),entry.getKey()));
        }
        return sectionList;
    }

    public static List<SectionedGridRecyclerViewAdapter.Section> getSectionList(){
        return sectionList;
    }

}
