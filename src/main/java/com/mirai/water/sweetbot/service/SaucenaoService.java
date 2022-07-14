package com.mirai.water.sweetbot.service;

import com.alibaba.fastjson.JSON;
import com.mirai.water.sweetbot.entity.saucenao.SaucenaoEntity;
import com.mirai.water.sweetbot.entity.saucenao.SaucenaoSearchInfoResult;
import com.mirai.water.sweetbot.util.HttpClientUtil;
import com.mirai.water.sweetbot.util.UrlStreamUtil;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @author by MechellWater
 * @date : 2022-05-17 22:10
 */
@Service
public class SaucenaoService {
    @Value("${saucenao.apiKey}")
    private String saucenaoApiKey;
    @Value("${saucenao.url}")
    private String saucenaoUrl;
    @Autowired
    UrlStreamUtil urlStreamUtil;

    @CrossOrigin
    public String getSendGetResult(String msg) throws IOException {
        HashMap<String, String> map = new HashMap<>();
        map.put("url", msg);
        map.put("api_key",saucenaoApiKey);
        map.put("output_type", "2");
        map.put("testmode","1");
        map.put("numres", "16");
        map.put("db","999");
/*        JSONObject map = new JSONObject();
        map.put("url", msg);
        map.put("api_key",saucenaoApiKey);
        map.put("output_type", "2");
        map.put("testmode","1");
        map.put("numres", "16");
        map.put("db","999");*/

        HttpClientUtil httpClientUtil = new HttpClientUtil();
        //System.out.println(HttpUtil.doGet(saucenaoUrl + HttpUtil.parseUrlEncode(map),HttpUtil.getProxy()).toString());
        return httpClientUtil.sendGet(saucenaoUrl , map);
    }

    public MessageChain seachResultStr(String msg, Group group) throws IOException {
        MessageChain messageChain = null;
        SaucenaoEntity saucenaoEntity = JSON.parseObject(getSendGetResult(msg),SaucenaoEntity.class);
        List<SaucenaoSearchInfoResult> data = saucenaoEntity.getResults();
        for (SaucenaoSearchInfoResult datum : data) {
            StringBuffer stringBuffer = new StringBuffer();
            Image image = group.uploadImage(urlStreamUtil.createImgExternalResource(datum.getHeader().getThumbnail()));

            stringBuffer.append("相似度 : "+ datum.getHeader().getSimilarity() +"%"+"\n");
            if (datum.getData().getTitle() !=null){
                stringBuffer.append("标题 : " +datum.getData().getTitle()+"\n");
            }else if (datum.getData().getEng_name()!=null || datum.getData().getJp_name() !=null){
                stringBuffer.append("标题 : " +datum.getData().getEng_name()+"   "+datum.getData().getJp_name()+"\n");
            }else if (datum.getData().getSource()!=null){
                stringBuffer.append("标题 : " +datum.getData().getSource()+"\n");
            }else {
                stringBuffer.append("标题 : 暂无记录"+"\n");
            }

            if (datum.getData().getMember_name() == null && datum.getData().getAuthor_name() != null){
                stringBuffer.append("作者 : " +datum.getData().getAuthor_name()+"\n");
            }else{
                stringBuffer.append("作者 : 暂无记录"+"\n");
            }
            if (datum.getData().getExt_urls()!= null){
                stringBuffer.append("图片地址 ： " + datum.getData().getExt_urls().get(0)+"\n");
            }
            return image.plus(stringBuffer);
        }
        return null;
    }

}
