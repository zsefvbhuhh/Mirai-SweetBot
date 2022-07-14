package com.mirai.water.sweetbot.util;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.stereotype.Component;

@Component
public class StringUtil {
    String str, newStr;
    Long memberId;

    /**
     * 是否为空
     * null或者空字符串都会判定为true
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public Long getLongByStr(String message) {
        str = message.replace("]", "").trim();
        newStr = str.substring(str.lastIndexOf(":") + 1, str.length());
        memberId = Long.parseLong(newStr);
        return memberId;
    }

    public String getResourceDelKeyWord(String keyWord, GroupMessageEvent event){
        return event.getMessage().contentToString().replace(keyWord,"").trim();
    }

}
