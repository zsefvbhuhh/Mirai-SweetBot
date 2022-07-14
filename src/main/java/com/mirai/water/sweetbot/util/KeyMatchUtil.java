package com.mirai.water.sweetbot.util;

import org.springframework.stereotype.Component;

/**
 * @author by MechellWater
 * @date : 2021-01-07 18:41
 */

@Component
public class KeyMatchUtil {
    public boolean keyMatchByKey(String msg, String[] keys) {
        for (String key : keys) {
            if (msg.contains(key)){
                return true;
            }
        }
        return false;
    }
}
