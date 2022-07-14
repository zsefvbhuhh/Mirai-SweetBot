package com.mirai.water.sweetbot.service;

import com.alibaba.fastjson.JSON;
import com.mirai.water.sweetbot.entity.zaobao.ZaoBao;
import com.mirai.water.sweetbot.util.FeignClentService;
import com.mirai.water.sweetbot.util.FeignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author by MechellWater
 * @date : 2021-06-02 16:35
 */
@Service
public class ZaoBaoService {
    @Autowired
    FeignUtil feignUtil;

    @Value("${alapi.token}")
    String alApiToken;

    public String zaoBaoUrl(){
        FeignClentService feignClentService = feignUtil.getFeignClient("https://v2.alapi.cn/api/zaobao"+"?token="+alApiToken+"&format=json");
        ZaoBao zaoBao = JSON.parseObject(feignClentService.getZaoBaoImage(),ZaoBao.class);
        return zaoBao.getData().getImage().replace("!/format/webp", "");
    }
}
