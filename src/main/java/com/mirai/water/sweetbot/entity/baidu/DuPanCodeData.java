package com.mirai.water.sweetbot.entity.baidu;

import lombok.*;

/**
 * @author Mehcell_Water
 * @date 2021/2/5 15:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DuPanCodeData {
    private String password;
    private String share_id;
    private String share_url;
}
