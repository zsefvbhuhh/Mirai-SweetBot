package com.mirai.water.sweetbot.service;

import com.alibaba.fastjson.JSON;
import com.mirai.water.sweetbot.entity.hitokoto.Hitokoto;
import com.mirai.water.sweetbot.util.FeignClentService;
import com.mirai.water.sweetbot.util.FeignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HitokotoService {
    @Autowired
    FeignUtil feignUtil;

    public String getHitokotoByKW(String keyWord){
        switch (keyWord) {
            case "动画":
                keyWord = "a";
                return keyWord;
            case "漫画":
                keyWord = "b";
                return keyWord;
            case "游戏":
                keyWord = "c";
                return keyWord;
            case "文学":
                keyWord = "d";
                return keyWord;
            case "原创":
                keyWord = "e";
                return keyWord;
            case "来自网络":
                keyWord = "f";
                return keyWord;
            case "其他":
                keyWord = "g";
                return keyWord;
            case "影视":
                keyWord = "h";
                return keyWord;
            case "诗词":
                keyWord = "i";
                return keyWord;
            case "网易云":
                keyWord = "j";
                return keyWord;
            case "哲学":
                keyWord = "k";
                return keyWord;
            case "抖机灵":
                keyWord = "l";
                return keyWord;

        }
        return null;
    }

    public String getHitokotoResult(String msg){
        String type = getHitokotoByKW(msg);
        FeignClentService feignClentService = feignUtil.getFeignClient("https://international.v1.hitokoto.cn?encode=json&charset=utf-8&c="+type);
        Hitokoto hitokoto = JSON.parseObject(feignClentService.getHitokotoResult(),Hitokoto.class);
        return hitokoto.getHitokoto()+"-----"+hitokoto.getFrom();
    }

}
