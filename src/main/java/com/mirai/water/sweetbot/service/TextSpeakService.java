package com.mirai.water.sweetbot.service;

import com.alibaba.fastjson.JSON;
import com.mirai.water.sweetbot.entity.text2speak.Zw2Py;
import com.mirai.water.sweetbot.util.FeignClentService;
import com.mirai.water.sweetbot.util.FeignUtil;
import com.mirai.water.sweetbot.util.UrlStreamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author by Liudw
 * @date : 2021-12-23 11:36
 */

@Service
public class TextSpeakService {
    @Autowired
    FeignUtil feignUtil;
    @Autowired
    UrlStreamUtil urlUtil;

    private static final String zw2Py = "http://api.k780.com/?app=code.hanzi_pinyin&typeid=1&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=text&wd=";
    private static final String py2Speak = "https://cloud.ai-j.jp/demo/aitalk2webapi_nop.php?api-version=v5&ext=mp3&volume=1.0&speed=1.15&pitch=1.0&callback=callback&speaker_id=551&range=1&anger=0&sadness=0&joy=0&text=";


    private String getZw2PyResult(String speakText){
        FeignClentService feignClient = feignUtil.getFeignClient(zw2Py + speakText);
        Zw2Py zw2Py = JSON.parseObject(feignClient.getText2Py(), Zw2Py.class);
        return zw2Py.getResult().getRet().replace(" ","");
    }

    public String getText2Speak(String speakText){
        FeignClentService feignClient = feignUtil.getFeignClient(py2Speak+getZw2PyResult(speakText));
        String voiceUrl = feignClient.getText2Speak();
        int start = voiceUrl.lastIndexOf("/")+1;
        int end = voiceUrl.length();
        String newVoiceUrl = voiceUrl.substring(start,end-3);
        return "https://cloud.ai-j.jp/demo/tmp/"+newVoiceUrl;
    }




}
