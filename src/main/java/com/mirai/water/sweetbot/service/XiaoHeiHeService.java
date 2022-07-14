package com.mirai.water.sweetbot.service;

import com.alibaba.fastjson.JSON;
import com.mirai.water.sweetbot.entity.xiaoheihe.*;
import com.mirai.water.sweetbot.util.FeignClentService;
import com.mirai.water.sweetbot.util.FeignUtil;
import com.mirai.water.sweetbot.util.UrlStreamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XiaoHeiHeService {
    @Autowired
    FeignUtil feignUtil;
    @Autowired
    UrlStreamUtil urlUtil;

    public String getGamesInfo() {
        String result = "";
        FeignClentService feignClentService = feignUtil.getFeignClient("https://api.xiaoheihe.cn/game/web/all_recommend/games/?os_type=web&version=999.0.0&show_type=discount&limit=20");
        XiaoHeiHe xiaoHeiHe = JSON.parseObject(feignClentService.getGameInfo(), XiaoHeiHe.class);
        List<XiaoHeiHeResult> xiaoHeiHeResultList = xiaoHeiHe.getResult();
        for (XiaoHeiHeResult xiaoHeiHeResult : xiaoHeiHeResultList) {
            List<GameList> gameLists = xiaoHeiHeResult.getList();
            for (GameList gameList : gameLists) {
                List<GameInfo> gameInfos = gameList.getPrice();
                for (GameInfo gameInfo : gameInfos) {
                    result += "游戏名称:" + gameList.getName() + "  " + "\n" +
                            "英文名称:" + gameList.getName_en() + "\n" +
                            "当前游戏价格:" + gameInfo.getCurrent() + "元" + "\n" +
                            "历史最低价格:" + gameInfo.getLowest_price() + "元" + "\n" +
                            "原价:" + gameInfo.getInitial() + "元" + "  " + "优惠时间:" + gameInfo.getDeadline_date() + "\n" + "\n";
                }
            }
        }
        return result;
    }

    public String getGameByName(String gameName) {
        FeignClentService feignClentService = feignUtil.getFeignClient("https://api.xiaoheihe.cn/game/search/?os_type=web&version=999.0.0&q=" + urlUtil.getURLEncoderString(gameName));
        XiaoHeiHe xiaoHeiHe = JSON.parseObject(feignClentService.getGameInfo(), XiaoHeiHe.class);
        List<XiaoHeiHeResult> xiaoHeiHeResultList = xiaoHeiHe.getResult();
        for (XiaoHeiHeResult xiaoHeiHeResult : xiaoHeiHeResultList) {
            List<SearchGames> searchGamesList = xiaoHeiHeResult.getGames();
            for (SearchGames searchGames : searchGamesList) {
                List<GameInfo> gameInfos = searchGames.getPrice();
                for (GameInfo gameInfo : gameInfos) {
                    return "游戏名称:" + searchGames.getName() + "  " + "\n" +
                            "当前游戏价格:" + gameInfo.getCurrent() + "元" + "\n" +
                            "历史最低价格:" + gameInfo.getLowest_price() + "元" + "\n" +
                            "原价:" + gameInfo.getInitial() + "元";
                }
            }
        }
        return "未找到相关游戏,请检查名称是否正确!";
    }


}
