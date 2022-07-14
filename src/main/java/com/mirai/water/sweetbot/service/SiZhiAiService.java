package com.mirai.water.sweetbot.service;

import com.alibaba.fastjson.JSON;
import com.mirai.water.sweetbot.entity.shizhiai.ShiZhiAi;
import com.mirai.water.sweetbot.util.FeignClentService;
import com.mirai.water.sweetbot.util.FeignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author by MechellWater
 * @date : 2021-01-08 19:41
 */
@Service
public class SiZhiAiService {
    @Value("${sizhi.apikey}")
    public String siZhiApikey;

    @Autowired
    FeignUtil feignUtil;

    public String aiMessage(String msg) {
        FeignClentService feignClentService = feignUtil.getFeignClient("https://api.ownthink.com/bot?appid=" + siZhiApikey + "&spoken=" + msg);
        ShiZhiAi shiZhiAi = JSON.parseObject(feignClentService.getShiZhiAiResult(), ShiZhiAi.class);
        return shiZhiAi.getData().getInfo().getText();
    }


}
