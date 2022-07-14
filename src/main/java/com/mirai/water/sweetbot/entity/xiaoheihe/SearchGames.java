package com.mirai.water.sweetbot.entity.xiaoheihe;

import lombok.*;

import java.util.List;

/**
 * @author by MechellWater
 * @date : 2021-01-20 19:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SearchGames {
    public String name;
    public String online_player;
    public List<GameInfo> price;
}
