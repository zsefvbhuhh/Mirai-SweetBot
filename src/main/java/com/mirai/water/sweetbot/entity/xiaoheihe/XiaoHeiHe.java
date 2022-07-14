package com.mirai.water.sweetbot.entity.xiaoheihe;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class XiaoHeiHe {
    public String msg;
    public List<XiaoHeiHeResult> result;
    public String status;
    public String version;
}
