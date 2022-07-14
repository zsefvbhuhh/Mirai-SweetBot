package com.mirai.water.sweetbot.entity.xiaoheihe;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class XiaoHeiHeResult {
    public List<GameList> list;
    public List<SearchGames> games;
}
