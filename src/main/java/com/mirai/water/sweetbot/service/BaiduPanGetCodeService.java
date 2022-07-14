package com.mirai.water.sweetbot.service;

import com.alibaba.fastjson.JSON;
import com.mirai.water.sweetbot.entity.baidu.DuPanCode;
import com.mirai.water.sweetbot.util.FeignClentService;
import com.mirai.water.sweetbot.util.FeignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author by MechellWater
 * @date : 2021-02-05 15:47
 */
@Service
public class BaiduPanGetCodeService {
    @Autowired
    FeignUtil feignUtil;

    public String getDuPanCode(String url){
        FeignClentService feignClient = feignUtil.getFeignClient("https://v1.alapi.cn/api/bdpwd?url="+url);
        DuPanCode duPanCode = JSON.parseObject(feignClient.getDuPanResult(), DuPanCode.class);
        return "提取码为: "+duPanCode.getData().getPassword();
    }

}
