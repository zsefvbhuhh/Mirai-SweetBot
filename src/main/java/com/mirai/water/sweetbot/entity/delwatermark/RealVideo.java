package com.mirai.water.sweetbot.entity.delwatermark;

import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * @author by MechellWater
 * @date : 2021-02-05 17:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RealVideo {
    private Integer code;
    @SerializedName("msg")
    private String mMsg;
    private RealVideoData data;
}
