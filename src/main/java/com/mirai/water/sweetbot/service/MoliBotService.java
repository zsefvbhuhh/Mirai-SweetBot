package com.mirai.water.sweetbot.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mirai.water.sweetbot.entity.moliapi.MoliApiData;
import com.mirai.water.sweetbot.entity.moliapi.MoliApiEntity;
import com.mirai.water.sweetbot.entity.moliapi.MoliApiReply;
import com.mirai.water.sweetbot.util.FeignClentService;
import com.mirai.water.sweetbot.util.FeignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author by Liudw
 * @date : 2021-12-21 10:29
 */
@Service
public class MoliBotService {
    @Autowired
    FeignUtil feignUtil;

    /**
     *@描述 群聊回复
     *@参数
     *@返回值
     *@创建人 Liudw
     *@创建时间 2021/12/21
     */
    public String moLiReply(String content,Integer type,Long from,String fromName,Long to,String toName){
        MoliApiEntity moliApiEntity = new MoliApiEntity(content,type,from,fromName,to,toName);
        return getString(moliApiEntity);
    }

    /**
     *@描述 好友单独回复
     *@参数
     *@返回值
     *@创建人 Liudw
     *@创建时间 2021/12/21
     */
    public String moLiReply(String content,Integer type,Long from,String fromName){
        MoliApiEntity moliApiEntity = new MoliApiEntity(content,type,from,fromName,null,null);
        return getString(moliApiEntity);
    }

    private String getString(MoliApiEntity moliApiEntity) {
        String moliJson = JSONObject.toJSONString(moliApiEntity);
        FeignClentService feignClentService = feignUtil.getFeignClient("https://i.mly.app/reply");
        MoliApiReply moliApiReply = JSON.parseObject(feignClentService.getMoliReply(moliJson), MoliApiReply.class);
        List<MoliApiData> moliApiDataList = moliApiReply.getData();
        return moliApiDataList.get(moliApiDataList.size()-1).getContent();
    }
}
