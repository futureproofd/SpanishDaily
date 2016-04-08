package to.marcus.rxtesting.util;

import org.joda.time.DateTime;

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
    private static final DateFormat formatted = new SimpleDateFormat("MMMM d", Locale.ENGLISH);
    private static final DateFormat formattedYr = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
    private static Date newDate;
    private static final DateTime today = new DateTime();
    //Defaults for word Sorting methods
    private static List<SectionedGridRecyclerViewAdapter.Section> sectionList;
    private static String month = "";
    private static String previousMonth = "";
    private static String startMonth = "";
    private static Map<String, Integer> monthHashMap;
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

    //For word DataSet, initial sorting
    public static Date formatDateStringNoYear(String date){
        try{
            newDate = formatted.parse(date);
        }catch (ParseException p){
            p.printStackTrace();
        }
        return newDate;
    }

    private static Date formatDateStringWithYear(String date){
        try{
            newDate = formattedYr.parse(date);
        }catch (ParseException p){
            p.printStackTrace();
        }
        return newDate;
    }

    //Compare dats between most recent word and today
    public static boolean isWordStale(String wordDate){
       boolean isStale;
       DateTime word = new DateTime(formatDateStringWithYear(wordDate));

        if(word.getYear() == today.getYear()) {
            isStale = word.getMonthOfYear() != today.getMonthOfYear() || word.getDayOfMonth() != today.getDayOfMonth();
        }else{
            isStale = true;
        }
        return isStale;
    }

    /*
        Helper methods for building word Sections
     */
    //Get month of first word in array
    private static String getStartMonth(ArrayList<Word> words){
        startMonth = words.get(0).getDate();
        return startMonth.substring(0, startMonth.indexOf(" "));
    }

    //build hashMap of Sections
    private static void buildMonthMappings(String month, int position){
        if(!getStartMonth(mWords).equals(month) || !previousMonth.equals(month)){
            monthHashMap.put(month, (position));
        }else{
            monthHashMap.put(month, 0);
        }
    }

    public static List<SectionedGridRecyclerViewAdapter.Section> getSections(ArrayList<Word> words){
        //reset list
        if(sectionList == null)
            sectionList = new ArrayList<>();
        //reset mappings
        if(monthHashMap == null)
            monthHashMap = new LinkedHashMap<>();

        //determine routing
        if(words.size() == 0){
            monthHashMap.put("No Words Avaliable", 0);
        }else{
            mWords = new ArrayList<>();
            Collections.sort(words);
            for(int i = 0; i < words.size(); i++) {
                Word w = words.get(i);
                if(w.getVisibility() == 1){
                    mWords.add(w);
                }
            }
            if(mWords.size() == 0){
                sectionList = getDefaultSections();
            }else{
                sectionList = getSectionsByMonth();
            }
        }
        mWords = null;
        return sectionList;
    }

    //default section if no words are visible
    private static List<SectionedGridRecyclerViewAdapter.Section> getDefaultSections(){
        monthHashMap.put("No Words Avaliable", 0);
        for(Map.Entry<String, Integer> entry : monthHashMap.entrySet()){
            sectionList.add(new SectionedGridRecyclerViewAdapter.Section(entry.getValue(),entry.getKey()));
        }
        return sectionList;
    }

    //Build Sections, sorted by month
    private static List<SectionedGridRecyclerViewAdapter.Section> getSectionsByMonth(){
        //Build hashMap
        for(int i = 0; i < mWords.size(); i++){
            month = mWords.get(i).getDate();
            month = month.substring(0, month.indexOf(" "));
            if(!previousMonth.equals(month) || i == 0){
                buildMonthMappings(month, i);
                previousMonth = month;
            }
        }
        //Remove existing hashMap sections
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

    //reset existing static references
    public static void removeSectionReferences(){
        sectionList = null;
        monthHashMap = null;
    }

}
