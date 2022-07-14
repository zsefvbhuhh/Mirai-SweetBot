package com.mirai.water.sweetbot.entity.xiaoheihe;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GameList {
    public String about_the_game;
    public String name;
    public String name_en;
    public List<GameInfo> price;
}
