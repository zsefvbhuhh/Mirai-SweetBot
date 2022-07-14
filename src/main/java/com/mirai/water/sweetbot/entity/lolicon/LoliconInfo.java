package com.mirai.water.sweetbot.entity.lolicon;

import lombok.*;

/**
 * @author by MechellWater
 * @date : 2021-01-13 19:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoliconInfo {
    public	Integer	    pid		;     //作品 PID
    public	Integer	    p		;     //作品所在 P
    public	Integer	    uid		;     //作者 UID
    public	String	    title	;     //作品标题
    public	String	    author	;     //作者名（入库时，并过滤掉 @ 及其后内容）
    public	String	    url		;     //图片链接（可能存在有些作品因修改或删除而导致 404 的情况）
    public	boolean	    r18		;     //是否 R18（在色图库中的分类，并非作者标识的 R18）
    public	Integer	    width	;     //原图宽度 px
    public	Integer	    height	;     //原图高度 px
    public	String[]    tags	;     //作品标签，包含标签的中文翻译（有的话）
}
