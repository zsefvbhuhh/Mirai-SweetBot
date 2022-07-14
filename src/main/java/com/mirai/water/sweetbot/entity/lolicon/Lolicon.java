package com.mirai.water.sweetbot.entity.lolicon;

import lombok.*;

import java.util.List;

/**
 * @author by MechellWater
 * @date : 2021-01-13 19:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Lolicon {
    public	Integer		code		;	//返回码，可能值详见后续部分
    public	String	    msg			;	//错误信息之类的
    public	Integer		quota		;	//剩余调用额度
    public	Integer		quota_min_ttl;	//距离下一次调用额度恢复(+1)的秒数
    public	Integer		count		;	//结果数
    public List<LoliconInfo> data		;	//色图数组
}
