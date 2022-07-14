package com.mirai.water.sweetbot.service;

import com.alibaba.fastjson.JSON;
import com.mirai.water.sweetbot.entity.delwatermark.RealVideo;
import com.mirai.water.sweetbot.util.FeignClentService;
import com.mirai.water.sweetbot.util.FeignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author by MechellWater
 * @date : 2021-02-05 17:28
 */
@Service
public class VideoDelWaterMarkService {
    @Autowired
    FeignUtil feignUtil;

    public String getDYRealUrl(String message){
        FeignClentService feignClient = feignUtil.getFeignClient("https://v1.alapi.cn/api/video/dy?url="+message);
        RealVideo realVideo = JSON.parseObject(feignClient.getVideoRealUrl(), RealVideo.class);
        return "无水印视频地址: "+realVideo.getData().getVideo_url();
    }

    public String getPPXRealUrl(String message){
        FeignClentService feignClient = feignUtil.getFeignClient("https://v1.alapi.cn/api/video/ppx?url="+message);
        RealVideo realVideo = JSON.parseObject(feignClient.getVideoRealUrl(), RealVideo.class);
        return "无水印视频地址: "+realVideo.getData().getVideo_url();
    }
}
