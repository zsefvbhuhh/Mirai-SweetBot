package com.mirai.water.sweetbot.entity.news;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewsInfo {
    public String title;
    public String link;
    public String other;
}
