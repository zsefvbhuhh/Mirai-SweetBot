package com.mirai.water.sweetbot.util;

import com.mirai.water.sweetbot.service.TxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TxAiUtil {
    @Autowired
    TxService txService;

    @Value("${bot.name}")
    public String botName;

    public String replaceMessage(String message){
        String srcReply = txService.getTxApiReply(message);
        if(!srcReply.contains("小龙女")){
            String reply2 = srcReply.replace(botName,"");
            String reply = reply2.replace("小龙女",botName);
            String bReply = reply.replace("腾讯","");
            String fReply = bReply.replace("姑姑",botName);
            return fReply;
        }
        return "";
    }
}
