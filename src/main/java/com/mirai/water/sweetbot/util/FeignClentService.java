package com.mirai.water.sweetbot.util;

import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;


public interface FeignClentService {
    @RequestLine("GET")
    String getShaDiaoResult();

    @RequestLine("GET")
    String getShiZhiAiResult();

    @RequestLine("GET")
    String getFeiFeiAiResult();

    @RequestLine("GET")
    String getTianDogResult();

    @RequestLine("GET")
    String getAiMessages(String msg);

    @RequestLine("GET")
    String getLolicon();

    @RequestLine("GET")
    String getGameInfo();

    @RequestLine("GET")
    String getHotTop();

    @RequestLine("GET")
    String getHitokotoResult();

    @RequestLine("GET")
    String getBangumiResult();

    @RequestLine("GET")
    String getDuPanResult();

    @RequestLine("GET")
    String getVideoRealUrl();

    @RequestLine("GET")
    String getZaoBaoImage();

    @RequestLine("POST")
    @Headers(value={"Api-Key: yihp6egm67bghtam",
                    "Api-Secret: f10tp5tf",
                    "Content-Type: application/json;charset=UTF-8"
    })
    @Body("{body}")
    String getMoliReply(@Param("body") String body);

    @RequestLine("GET")
    String getText2Py();

    @RequestLine("GET")
    String getText2Speak();
}
