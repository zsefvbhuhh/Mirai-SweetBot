package com.mirai.water.sweetbot.service;

import com.alibaba.fastjson.JSON;
import com.mirai.water.sweetbot.entity.ai.Ai;
import com.mirai.water.sweetbot.util.FeignClentService;
import com.mirai.water.sweetbot.util.FeignUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AiService {
    @Autowired
    private FeignUtil feignUtil;

    public String aiReply(@NotNull String userMsg) {
        FeignClentService feignClentService = feignUtil.getFeignClient("http://api.qingyunke.com/api.php?key=free&appid=0&msg=" + userMsg);
        Ai ai = JSON.parseObject(feignClentService.getAiMessages(userMsg), Ai.class);
        return ai.getContent().replace("{br}", "\n");
    }
}
