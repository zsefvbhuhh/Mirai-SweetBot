package com.mirai.water.sweetbot.entity.moliapi;

import lombok.*;

import java.util.List;

/**
 * @author by Liudw
 * @date : 2021-12-21 10:36
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MoliApiReply {
    private String code;
    private String message;
    private String plugin;
    private List<MoliApiData> data;
}
