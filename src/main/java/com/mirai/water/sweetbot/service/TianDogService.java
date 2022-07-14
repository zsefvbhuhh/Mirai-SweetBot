package com.mirai.water.sweetbot.service;

import com.mirai.water.sweetbot.util.FeignClentService;
import com.mirai.water.sweetbot.util.FeignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author by MechellWater
 * @date : 2021-01-09 00:54
 */
@Service
public class TianDogService {
    @Value("${alapi.token}")
    String alApiToken;
    @Autowired
    FeignUtil feignUtil;

    public String getTianDogResult(){
        FeignClentService feignClentService = feignUtil.getFeignClient("https://v2.alapi.cn/api/dog?token="+alApiToken+"&format=text");
        return feignClentService.getTianDogResult();
    }


/*
    public String getTianDogResult() {
        FeignClentService feignClentService = feignUtil.getFeignClient("http://api.ixiaowai.cn/tgrj/index.php");
        return feignClentService.getTianDogResult();
    }

    public String getTianDogResult2() {
        FeignClentService feignClentService = feignUtil.getFeignClient("http://api.oick.cn/dog/api.php");
        return feignClentService.getTianDogResult();
    }

    public String getTianDogResult3() {
        FeignClentService feignClentService = feignUtil.getFeignClient("http://api.oick.cn/yulu/api.php");
        return feignClentService.getTianDogResult();
    }
*/

}
