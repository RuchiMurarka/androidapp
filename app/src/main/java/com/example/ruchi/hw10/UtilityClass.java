package com.example.ruchi.hw10;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ruchi on 11/27/2016.
 */

public final class UtilityClass {

    private UtilityClass(){

    }
     public static String partycheck(String s)
     {
         if(s.equals("D"))
             return "Democratic";
         else if(s.equals("R"))
             return "Republican";
         else
             return "Independent";
     }
    public static String capitalize(String str){

        StringBuffer stringbf = new StringBuffer();
        Matcher m = Pattern.compile("([a-z])([a-z]*)",
                Pattern.CASE_INSENSITIVE).matcher(str);
        while (m.find()) {
            m.appendReplacement(stringbf,
                    m.group(1).toUpperCase() + m.group(2).toLowerCase());
        }
        Log.i("stringgggg",m.appendTail(stringbf).toString());
        return m.appendTail(stringbf).toString();
    }
    public static String validate(String str)
    {
        if(str.equals("null"))
            return ("N.A.");
        else
            return str;
    }
     static public String formating(String date)
     {

         if(date!="null") {
                try {
                    SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-mm-dd");
                    Date dateObj = curFormater.parse(date);
                    SimpleDateFormat postFormater = new SimpleDateFormat("MMM dd, yyyy");
                  String  data1 = postFormater.format(dateObj);
                    Log.i("dateee ",data1);
                    return data1;
                 }catch (Exception e)
                {
                      e.printStackTrace();
                }
         }
         return null;
     }
    static public String convert(String p)
    {
        if(p.equals("true"))
        return "Active";
        else
            return "New";
    }


}
