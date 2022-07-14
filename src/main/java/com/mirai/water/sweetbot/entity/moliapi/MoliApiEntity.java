package com.mirai.water.sweetbot.entity.moliapi;

import lombok.*;

/**
 * @author by Liudw
 * @date : 2021-12-21 12:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MoliApiEntity {
    private String content;
    private Integer type;
    private Long from;
    private String fromName;
    private Long to;
    private String toName;
}
