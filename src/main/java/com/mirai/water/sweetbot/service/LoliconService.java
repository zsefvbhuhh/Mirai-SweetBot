package com.mirai.water.sweetbot.service;

import com.alibaba.fastjson.JSON;
import com.mirai.water.sweetbot.entity.lolicon.Lolicon;
import com.mirai.water.sweetbot.entity.lolicon.LoliconInfo;
import com.mirai.water.sweetbot.util.FeignClentService;
import com.mirai.water.sweetbot.util.FeignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by MechellWater
 * @date : 2021-01-13 20:02
 */
@Service
public class LoliconService {
    @Autowired
    FeignUtil feignUtil;

    @Value("${lolicon.apikey}")
    String loliconKey;

    List<String> loliconList;
    List<String> loliconListR18;

    public String getLoliconListByKey(int r18,String keyword,int sfys) {
        FeignClentService feignClentService = feignUtil.getFeignClient("https://api.lolicon.app/setu/?apikey=" + loliconKey + "&r18=" + r18 + "&num=1"+"&keyword="+keyword);//+"&size1200=true"
        Lolicon lolicon = JSON.parseObject(feignClentService.getLolicon(), Lolicon.class);
        List<LoliconInfo> loliconList = lolicon.getData();
        for (LoliconInfo loliconInfo : loliconList) {
            System.out.println("成功获取url-----------" + loliconInfo.getUrl());
            if(sfys==1){
                return "https://api.fantasyzone.cc/compress?img="+loliconInfo.getUrl();//pixiv.me//"i.pixiv.re"
            }else {
                return loliconInfo.getUrl().replace("i.pixiv.cat", "i.pixiv.re");//pixiv.me
            }
        }
        return "false";
    }


    public List<String> getLoliconList(int r18,int sfys) {
        List<String> loliconInfoList = new ArrayList<>();
        FeignClentService feignClentService = feignUtil.getFeignClient("https://api.lolicon.app/setu/?apikey=" + loliconKey + "&r18=" + r18 + "&num=10");//+"&size1200=true"
        Lolicon lolicon = JSON.parseObject(feignClentService.getLolicon(), Lolicon.class);
        List<LoliconInfo> loliconList = lolicon.getData();
        System.out.println("成功获取数据--------" + lolicon.getData());
        for (LoliconInfo loliconInfo : loliconList) {
            System.out.println("成功获取url-----------" + loliconInfo.getUrl());
            if(sfys==1){
                loliconInfoList.add("https://api.fantasyzone.cc/compress?img="+loliconInfo.getUrl());//pixiv.me//"i.pixiv.re"
            }else {
                loliconInfoList.add(loliconInfo.getUrl().replace("i.pixiv.cat", "i.pixiv.re"));//pixiv.me
            }
        }
        return loliconInfoList;
    }

    public List<String> loliconListRf(int sfys) {
        return loliconList = getLoliconList(0,sfys);
    }

    public List<String> loliconR18ListRf(int sfys) {
        return loliconListR18 = getLoliconList(1,sfys);
    }

    public int getR18Count(int r18) {
        if (r18 == 1) {
            return loliconListR18.size();
        } else {
            return loliconList.size();
        }
    }


    public String getUrl(int r18, int num) {
        if (r18 == 1) {
            return loliconListR18.get(num);
        } else {
            return loliconList.get(num);
        }
    }


}
