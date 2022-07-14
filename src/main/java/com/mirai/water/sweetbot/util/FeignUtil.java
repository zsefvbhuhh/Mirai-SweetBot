package com.mirai.water.sweetbot.util;

import feign.Feign;
import org.springframework.stereotype.Component;

/**
 * @author by MechellWater
 * @date : 2021-01-09 00:56
 */
@Component
public class FeignUtil {
    public FeignClentService getFeignClient(String url) {
        FeignClentService feignClentService = Feign.builder().target(FeignClentService.class, url);
        return feignClentService;
    }
}