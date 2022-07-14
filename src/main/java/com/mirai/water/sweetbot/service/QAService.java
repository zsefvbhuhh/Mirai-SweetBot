package com.mirai.water.sweetbot.service;

import com.mirai.water.sweetbot.entity.zuan.SayingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author by Liudw
 * @date : 2022-01-19 15:12
 */
@Service
public class QAService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    String sql;

    public String save(String message, Long sender) {
        int wenIndex = message.indexOf("教");
        int daIndex = message.indexOf("答");
        String question = message.substring(wenIndex + 1, daIndex - 1).trim();
        String answer = message.substring(daIndex + 1, message.length()).trim();
        if (question != "" && answer != "") {
            if (saveResult(question, answer, sender) >= 1) {
                return "成功!";
            }
        }
        return "失败!";
    }

    public String delete(String message) {
        int wenIndex = message.indexOf("删词");
        int id = Integer.parseInt(message.substring(wenIndex + 2, message.length()).trim());
        if (deleteResultById(id) >= 1) {
            return "成功!";
        }
        return "失败!";
    }

    public String getResultByContent(String message) {
        sql = "select body from sweetbotdb.saying where type = 'QA' and question ='" + message + "'order by rand() LIMIT 1;";
        return jdbcTemplate.queryForObject(sql, String.class);
    }

    public Integer getCountByContent(String message) {
        sql = "select count(1) from sweetbotdb.saying where type = 'QA' and question ='" + message + "'order by rand() LIMIT 1;";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int saveResult(String question, String answer, Long saveQQ) {
        sql = "INSERT INTO sweetbotdb.saying (question, body, type, author) VALUES ('" + question + "', '" + answer + "', 'QA', '" + saveQQ + "');";
        return jdbcTemplate.update(sql);
    }

    public String getResultByContenctAndId() {
        sql = "select id,question,body from sweetbotdb.saying where type = 'QA' order by id desc LIMIT 5;";
        List<SayingEntity> sayingEntityArraryList = jdbcTemplate.query(sql, new RowMapper() {
            @Override
            public SayingEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                SayingEntity sayingEntity = new SayingEntity();
                sayingEntity.setId(rs.getInt("id"));
                sayingEntity.setQuestion(rs.getString("question"));
                sayingEntity.setBody(rs.getString("body"));
                return sayingEntity;
            }
        });
        StringBuilder s = new StringBuilder("");
        for (SayingEntity se : sayingEntityArraryList) {
            s.append("id: " + se.getId() + "  question: " + se.getQuestion() + "  answer: " + se.getBody() + "\n");
        }
        return s.toString();
    }

    public int deleteResultById(int id) {
        sql = "delete from sweetbotdb.saying where id = " + id + "";
        return jdbcTemplate.update(sql);
    }

}
