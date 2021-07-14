package com.example.covidhospitals;
import java.util.Calendar;
public class MyUtils {
    public static String getTimeInMs(){
        String ret=""+Calendar.getInstance().getTimeInMillis();
        return ret.substring(ret.length()-8);
    }
}
