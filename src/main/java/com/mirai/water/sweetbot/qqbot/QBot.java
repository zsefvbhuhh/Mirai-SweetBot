package com.mirai.water.sweetbot.qqbot;

import com.mirai.water.sweetbot.event.GroupsMessageEvents;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author Mehcell_Water
 * @date 2020/12/29 12:31
 */
@Component
public class QBot implements ApplicationRunner {
    @Value("${bot.account}")
    public Long botAccount;
    @Value("${bot.pwd}")
    public String botPwd;

    @Autowired
    GroupsMessageEvents groupsMessageEvents;

    private static Bot bot;

    public static Bot getBot() {
        return bot;
    }

    //设备认证信息文件
    private static final String deviceInfo = "device.json";


    /**
     * 启动BOT
     */
    public void startBot() {
        if (null == botAccount || null == botPwd) {
            System.err.println("*****未配置账号或密码*****");
        }

        bot = BotFactory.INSTANCE.newBot(botAccount, botPwd, new BotConfiguration() {
            {
                //保存设备信息到文件deviceInfo.json文件里相当于是个设备认证信息
                fileBasedDeviceInfo(deviceInfo);
                setProtocol(MiraiProtocol.ANDROID_PHONE); // 切换协议
            }
        });
        bot.login();
        GlobalEventChannel.INSTANCE.registerListenerHost(groupsMessageEvents);
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2,SSLv3");

        new Thread(() -> {
            bot.join();
        }).start();
    }
    public ContactList<Group> getBotGroups(){
        return bot.getGroups();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.startBot();
    }
}
