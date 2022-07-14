package com.mirai.water.sweetbot.entity.moliapi;

import lombok.*;

/**
 * @author by Liudw
 * @date : 2021-12-21 10:40
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MoliApiData {
    private String content;
    private String typed;
    private String remark;
}
