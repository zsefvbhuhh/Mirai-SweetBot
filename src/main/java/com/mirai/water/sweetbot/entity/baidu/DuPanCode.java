package com.mirai.water.sweetbot.entity.baidu;

import lombok.*;

/**
 * @author Mehcell_Water
 * @date 2021/2/5 15:52
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DuPanCode {
    private Long code;
    private DuPanCodeData data;
    private String msg;
}
