package com.mirai.water.sweetbot.entity.news;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TopList {
    public String last_update;
    public String name;
    public List<NewsInfo> list;
}
