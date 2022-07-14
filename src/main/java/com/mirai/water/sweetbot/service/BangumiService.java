package com.mirai.water.sweetbot.service;

import com.alibaba.fastjson.JSONObject;
import com.mirai.water.sweetbot.entity.bangumi.BangumiInfo;
import com.mirai.water.sweetbot.entity.bangumi.Item;
import com.mirai.water.sweetbot.util.BangumiUtil;
import com.mirai.water.sweetbot.util.FeignClentService;
import com.mirai.water.sweetbot.util.FeignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author by MechellWater
 * @date : 2021-01-26 20:15
 */
@Service
public class BangumiService {
    @Autowired
    FeignUtil feignUtil;

    @Autowired
    BangumiUtil bangumiUtil;


    public JSONObject getAnimeInfo() {
        //String year = bangumiUtil.getYearAnimeList();
        //String month = bangumiUtil.getMonthAnimeList();

        FeignClentService feignClentService = feignUtil.getFeignClient("https://bgmlist.com/api/v1/bangumi/onair");
        JSONObject jsonObject = JSONObject.parseObject(feignClentService.getBangumiResult());
        return jsonObject;
    }

    public List<Item> getBangumiList() {
        JSONObject jsonObject = getAnimeInfo();
        BangumiInfo bangumiInfoEntity = jsonObject.toJavaObject(BangumiInfo.class);
        return bangumiInfoEntity.getItems();
    }


    public String getAnimeResult() throws ParseException {
        List<Item> bangumiInfoList = getBangumiList();
        StringBuilder AnimeResult = new StringBuilder("");
        String week;
        for (Item item : bangumiInfoList) {
            week = getWeek(item);
            if (bangumiUtil.getWeek().equals(week)&&item.getBroadcast()!= null) {
                AnimeResult.append("中文译名: " + item.getTitleTranslate().getZhHans()).append('\n');
                AnimeResult.append("番剧原名: " + item.getTitle()).append('\n');
                AnimeResult.append("更新时间: " + week).append('\n');
                AnimeResult.append("播出时间: " + item.getBroadcast().substring(2, 12)).append('\n').append('\n').append('\n');
            }else if(bangumiUtil.getWeek().equals(week)) {
                AnimeResult.append("中文译名: " + item.getTitleTranslate().getZhHans()).append('\n');
                AnimeResult.append("番剧原名: " + item.getTitle()).append('\n');
                AnimeResult.append("更新时间: " + week).append('\n');
                AnimeResult.append("播出时间: " + item.getBegin().substring(0, 10)).append('\n').append('\n').append('\n');
            }
        }
        return AnimeResult.toString();
    }

    private String getWeek(Item item) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date date;
        if (item.getBroadcast() != null){
            date = simpleDateFormat.parse(item.getBroadcast().substring(2, 12));
        }else {
            date = simpleDateFormat.parse(item.getBegin().substring(0, 10));
        }
        calendar.setTime(date);
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        switch (i) {
            case 1:
                return  "星期日";
            case 2:
                return  "星期一";
            case 3:
                return  "星期二";
            case 4:
                return  "星期三";
            case 5:
                return  "星期四";
            case 6:
                return  "星期五";
            case 7:
                return  "星期六";
        }
        return null;
    }


    public String getAnimeResultByMessage(String message) throws ParseException {
        List<Item> bangumiInfoList = getBangumiList();
        StringBuilder AnimeResult = new StringBuilder("");
        String week;

        for (Item item : bangumiInfoList) {
            week = getWeek(item);
            if (message.contains("昨日番剧")) {
                if (bangumiUtil.getWeek(-2).equals(week)&&item.getBroadcast()!= null) {
                    AnimeResult.append("中文译名: " + item.getTitleTranslate().getZhHans()).append('\n');
                    AnimeResult.append("番剧原名: " + item.getTitle()).append('\n');
                    AnimeResult.append("更新时间: " + week).append('\n');
                    AnimeResult.append("播出时间: " + item.getBroadcast().substring(2, 12)).append('\n').append('\n').append('\n');
                }else if(bangumiUtil.getWeek(-2).equals(week)) {
                    AnimeResult.append("中文译名: " + item.getTitleTranslate().getZhHans()).append('\n');
                    AnimeResult.append("番剧原名: " + item.getTitle()).append('\n');
                    AnimeResult.append("更新时间: " + week).append('\n');
                    AnimeResult.append("播出时间: " + item.getBegin().substring(0, 10)).append('\n').append('\n').append('\n');
                }
            } else if (message.contains("明日番剧")) {
                if (bangumiUtil.getWeek(0).equals(week)&&item.getBroadcast()!= null) {
                    AnimeResult.append("中文译名: " + item.getTitleTranslate().getZhHans()).append('\n');
                    AnimeResult.append("番剧原名: " + item.getTitle()).append('\n');
                    AnimeResult.append("更新时间: " + week).append('\n');
                    AnimeResult.append("播出时间: " + item.getBroadcast().substring(2, 12)).append('\n').append('\n').append('\n');
                }else if(bangumiUtil.getWeek(0).equals(week)) {
                    AnimeResult.append("中文译名: " + item.getTitleTranslate().getZhHans()).append('\n');
                    AnimeResult.append("番剧原名: " + item.getTitle()).append('\n');
                    AnimeResult.append("更新时间: " + week).append('\n');
                    AnimeResult.append("播出时间: " + item.getBegin().substring(0, 10)).append('\n').append('\n').append('\n');
                }
            }
        }
        return AnimeResult.toString();
    }

}
