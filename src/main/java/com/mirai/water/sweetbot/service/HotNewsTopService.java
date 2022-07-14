package com.mirai.water.sweetbot.service;

import com.alibaba.fastjson.JSON;
import com.mirai.water.sweetbot.entity.news.NewsInfo;
import com.mirai.water.sweetbot.entity.news.NewsReponse;
import com.mirai.water.sweetbot.entity.news.TopList;
import com.mirai.water.sweetbot.util.FeignClentService;
import com.mirai.water.sweetbot.util.FeignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotNewsTopService {
    @Autowired
    FeignUtil feignUtil;

    public String getNewsByKW(String keyWord){
        switch (keyWord) {
            case "知乎":
                keyWord = "zhihu";
                return keyWord;
            case "微博":
                keyWord = "weibo";
                return keyWord;
            case "百度":
                keyWord = "baidu";
                return keyWord;
            case "今日头条":
                keyWord = "toutiao";
                return keyWord;
            case "网易新闻":
                keyWord = "163";
                return keyWord;
            case "热词排行榜":
                keyWord = "xl";
                return keyWord;
            case "历史上的今天":
                keyWord = "hitory";
                return keyWord;
        }
        return null;
    }


    public String getHotNewsTop(String net) {
        int count = 0;
        String keyWord = getNewsByKW(net);
        String a = "";
        FeignClentService feignClentService = feignUtil.getFeignClient("https://v1.alapi.cn/api/tophub/get?type=" + keyWord);
        NewsReponse newsReponse = JSON.parseObject(feignClentService.getHotTop(), NewsReponse.class);
        TopList topListList = newsReponse.getData();
        a += topListList.getName() + "\n";
        List<NewsInfo> newsInfo = topListList.getList();
        for (NewsInfo result : newsInfo) {
            if(count<=10){
                count ++;
                 a += count+"."+"\n"+result.title + "\n"+ "\n";
            }else{
                return a;
            }
        }
        return a;
    }
}
