package com.mirai.water.sweetbot.entity.hitokoto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Hitokoto {
    public int id;
    public String hitokoto;
    public String type;
    public String from;
}
