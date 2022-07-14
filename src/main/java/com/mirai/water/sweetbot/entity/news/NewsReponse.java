package com.mirai.water.sweetbot.entity.news;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewsReponse {
    public int code;
    public String msg;
    public TopList data;
}
