package com.mirai.water.sweetbot.util;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author by MechellWater
 * @date : 2021-01-27 01:20
 */
@Component
public class BangumiUtil {
    String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    public String getYearAnimeList() {
        String year = new SimpleDateFormat("YYYY").format(Calendar.getInstance().getTime());
        return year;
    }

    public String getMonthAnimeList() {
        String month = new SimpleDateFormat("M").format(Calendar.getInstance().getTime());

/*        switch (month) {
            case "1", "2", "3":
                month = "1";
                break;
            case "4", "5", "6":
                month = "4";
                break;
            case "7", "8", "9":
                month = "7";
                break;
            case "10", "11", "12":
                month = "10";
                break;
        }
        return month;*/
        switch (month) {
            case "1", "2", "3":
                month = "q1";
                break;
            case "4", "5", "6":
                month = "q2";
                break;
            case "7", "8", "9":
                month = "q3";
                break;
            case "10", "11", "12":
                month = "q4";
                break;
        }
        return month;
    }


    public String getWeek() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK);
        return weekDays[w-1];
    }

    public String getWeek(int i) {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK);//5
        //i = -1  nowWeek=5;yesterday=4;
/*        if (w < 0){
            w = 0;
        }*/
        if(w==1&&i==-2){
            w = 7;
        }else if (w==7&&i==0){
            w=1;
        }else if (i==-2){
            w-=2;
        }
        return weekDays[w];
    }
}
