package com.mirai.water.sweetbot.service;

import com.alibaba.fastjson.JSON;
import com.mirai.water.sweetbot.entity.feifeiai.FeiFeiAi;
import com.mirai.water.sweetbot.util.FeignClentService;
import com.mirai.water.sweetbot.util.FeignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author by MechellWater
 * @date : 2021-01-09 00:28
 */
@Service
public class FeiFeiAiService {
    @Autowired
    FeignUtil feignUtil;

    public String getResult(String msg) {
        FeignClentService feignClentService = feignUtil.getFeignClient("http://api.qingyunke.com/api.php?key=free&appid=0&msg=" + msg);
        FeiFeiAi ai = JSON.parseObject(feignClentService.getFeiFeiAiResult(), FeiFeiAi.class);
        return ai.getContent().replace("{br}", "\n");
    }
}
