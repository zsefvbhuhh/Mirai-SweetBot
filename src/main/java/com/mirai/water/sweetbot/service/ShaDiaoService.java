package com.mirai.water.sweetbot.service;

import com.mirai.water.sweetbot.util.FeignClentService;
import com.mirai.water.sweetbot.util.FeignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ShaDiaoService {
    @Value("${shadiao.username}")
    private String username;

    @Autowired
    FeignUtil feignUtil;

    @Autowired
    JdbcTemplate jdbcTemplate;


    public String getCaiHongPi() {
        FeignClentService feignClentService = feignUtil.getFeignClient("https://chp.shadiao.app/api.php?from=" + username);
        return feignClentService.getShaDiaoResult();
    }

    public String getHuoLiQuanKai() {
        String sql = "select body from sweetbotdb.saying where type = 'maxZuAn' order by rand() LIMIT 1;";
        jdbcTemplate.queryForObject(sql,String.class);
        return jdbcTemplate.queryForObject(sql,String.class);
    }

    public String getKouTuLianHua() {
        String sql = "select body from sweetbotdb.saying where type = 'minZuAn' order by rand() LIMIT 1;";
        jdbcTemplate.queryForObject(sql,String.class);
        return jdbcTemplate.queryForObject(sql,String.class);
    }

    public String getPengYouQuanWenAn() {
        FeignClentService feignClentService = feignUtil.getFeignClient("https://pyq.shadiao.app/api.php?from=" + username);
        return feignClentService.getShaDiaoResult();
    }

    public String getDuJiTang() {
        FeignClentService feignClentService = feignUtil.getFeignClient("https://du.shadiao.app/api.php?from=" + username);
        return feignClentService.getShaDiaoResult();
    }
}
