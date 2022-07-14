package com.mirai.water.sweetbot.entity.xiaoheihe;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GameInfo {
    public String current;
    public String deadline_date;
    public String initial;
    public String lowest_price;
}
