package com.mirai.water.sweetbot.event;

import com.mirai.water.sweetbot.qqbot.QBot;
import com.mirai.water.sweetbot.service.*;
import com.mirai.water.sweetbot.util.KeyMatchUtil;
import com.mirai.water.sweetbot.util.StringUtil;
import com.mirai.water.sweetbot.util.TxAiUtil;
import com.mirai.water.sweetbot.util.UrlStreamUtil;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.*;
import org.glavo.rcon.Rcon;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Iterator;

/**
 * @author by MechellWater
 * @date : 2020-12-30 22:47
 */
@Component
public class GroupsMessageEvents extends SimpleListenerHost {
    @Value("${bot.qGroup}")
    public Long qGroup;

    @Value("${lolicon.group}")
    public String loliconGroup;

    @Value("${qGroup.whiteList}")
    public String whiteListStr;

    @Value("${txAi.whiteList}")
    public String txAiGroupList;

    @Value("${bot.name:梨绘}")
    public String botName;

    @Value("${mcRcon.ip}")
    public String mcRconIP;

    @Value("${mcRcon.port}")
    public Integer mcRconPort;

    @Value("${mcRcon.password}")
    public String mcRconPwd;

    @Autowired
    QBot qBot;

    @Autowired
    ShaDiaoService shaDiaoApi;

    @Autowired
    KeyMatchUtil keyMatchUtil;

    @Autowired
    AiService aiApi;

    @Autowired
    TianDogService tianDog;

    @Autowired
    UrlStreamUtil urlStreamUtil;

    @Autowired
    LoliconService loliconApi;

    @Autowired
    StringUtil stringUtil;

    @Autowired
    XiaoHeiHeService xiaoHeiHeApi;

    @Autowired
    HotNewsTopService hotNewsTop;

    @Autowired
    HitokotoService hitokotoApi;

    @Autowired
    BangumiService bangumiApi;

    @Autowired
    TxService txAPI;

    @Autowired
    BaiduPanGetCodeService baiduPanGetCodeAPI;

    @Autowired
    TxAiUtil txAiUtil;

    @Autowired
    ZaoBaoService zaoBaoService;

    @Autowired
    VideoDelWaterMarkService videoDelWaterMarkApi;

    @Autowired
    MoliBotService moliBotService;

    @Autowired
    TextSpeakService textSpeakService;

    @Autowired
    QAService qaService;

    @Autowired
    SaucenaoService saucenaoService;

    int loliconCount = -1;
    int loliconR18Count = -1;
    int sfzc = 0;
    int sfR18 = 0;
    int sfxl = 0;
    int sfys = 0;


    /**
     * 定时发送早报
     * @throws Exception
     */
    @Scheduled(cron = "0 0 7 * * ?")
    public void zaoBao(){
            Group group = null;
            Image image = null;
            ContactList<Group> contactList = qBot.getBotGroups();
            Iterator<Group> iterator = contactList.iterator();
            while (iterator.hasNext()) {
                group = iterator.next();
                group.sendMessage("操!都几点了,起床了!都给我精神点,先看个早报");
                try {
                    image = group.uploadImage(urlStreamUtil.createImgExternalResource(zaoBaoService.zaoBaoUrl()));
                } catch (IOException e) {
                    e.printStackTrace();
                    group.sendMessage("发送失败,重新发送!");
                    try {
                        image = group.uploadImage(urlStreamUtil.createImgExternalResource(zaoBaoService.zaoBaoUrl()));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    group.sendMessage(image);
                    System.out.println(group.getId() + "早报发送成功!");
                }
                group.sendMessage(image);
                System.out.println(group.getId() + "早报发送成功!");
            }
    }

    /*Q群监听回复*/
    @NotNull
    @EventHandler
    public ListeningStatus onGroupMessage(@NotNull GroupMessageEvent event) throws Exception {
        String message = event.getMessage().toString();
        String centerMessage = event.getMessage().contentToString();
        Member member = event.getSender();
        Long memberId = member.getId();
        Group group = event.getGroup();
        Long groupId = group.getId();
        Bot bot = event.getBot();
        Long botId = bot.getId();
        String replaceMessageCenter = centerMessage.replace("@" + botId.toString(), "").trim();

        Image image = null;
        String loliconKeyword = "";
        OfflineAudio voice = null;


        /**
         * 管理员指令
         */
        if (whiteListStr.contains(memberId.toString())) {
            if (message.contains("#MC指令")) {
                String commandStr = centerMessage.substring(centerMessage.lastIndexOf("MC指令"), centerMessage.length());
                String command = commandStr.replace("MC指令", "").trim();
                Rcon rcon = new Rcon(mcRconIP, mcRconPort, mcRconPwd);
                String list = rcon.command(command);
                group.sendMessage("已成功发送指令: " + commandStr + "\n" + "返回结果:" + list);
                rcon.close();
                return ListeningStatus.LISTENING;
            }

            if (message.contains("#开启嘴臭模式") && sfzc == 0) {
                sfzc = 1;
                group.sendMessage("好家伙,开始嘴臭了熬");
                return ListeningStatus.LISTENING;
            } else if (message.contains("#关闭嘴臭模式") && sfzc == 1) {
                sfzc = 0;
                group.sendMessage("你们的马保住了");
                return ListeningStatus.LISTENING;
            }


            if (message.contains("#开启R18模式") && sfR18 == 0) {
                sfR18 = 1;
                group.sendMessage("已开启,你懂得");
                return ListeningStatus.LISTENING;
            } else if (message.contains("#关闭R18模式") && sfR18 == 1) {
                sfR18 = 0;
                group.sendMessage("已关闭,不给看咯");
                return ListeningStatus.LISTENING;
            }

            if (message.contains("#开启闲聊模式") && sfxl == 0) {
                sfxl = 1;
                group.sendMessage("开始BB");
                return ListeningStatus.LISTENING;
            } else if (message.contains("#关闭闲聊模式") && sfxl == 1) {
                sfxl = 0;
                group.sendMessage("闭嘴了");
                return ListeningStatus.LISTENING;
            }

            if (message.contains("#开启压缩模式") && sfys == 0) {
                sfys = 1;
                group.sendMessage("已开启压缩模式");
                return ListeningStatus.LISTENING;
            } else if (message.contains("#关闭压缩模式") && sfys == 1) {
                sfys = 0;
                group.sendMessage("已关闭压缩模式");
                return ListeningStatus.LISTENING;
            }

            //删词库
            if (message.contains("at:" + event.getBot().getId()) &&message.contains("删词")){
                group.sendMessage(qaService.delete(centerMessage));
                return ListeningStatus.LISTENING;
            }
        }


        //问答功能
        if (message.contains("at:" + event.getBot().getId()) &&message.contains("教")&&message.contains("答")){
            group.sendMessage(qaService.save(centerMessage, memberId));
            return ListeningStatus.LISTENING;
        }
        //查字库
        if (message.contains("at:" + event.getBot().getId()) &&message.contains("查询最近五条词库")){
            group.sendMessage(qaService.getResultByContenctAndId());
            return ListeningStatus.LISTENING;
        }

        /**
         * 发送功能菜单
         */
        if (message.contains("#help") || message.contains("#帮助")) {
            group.sendMessage("目前功能有:" + "\n" +
                    "辱骂@    " + "夸人@" + "\n" +
                    "买家秀图片  " + "二次元图片" + "\n" +
                    "舔狗日记   " + "涩图!" + "\n" +
                    "热搜     " + "一言" + "\n" +
                    "MC服务器查询     " + "MC服务器指令" + "\n" +
                    "游戏特惠情况     " + "游戏价格查询" + "\n" +
                    "抖音去水印     " + "皮皮虾去水印" + "\n" +
                    "取码(百度网盘)   " + "今日/明日/昨日番剧");
            return ListeningStatus.LISTENING;
        }





        /**
         *  说xxxxx 语音合成
         * */
        try {
            if(centerMessage.contains(("@" + botId + " 说"))){
                voice = group.uploadAudio(urlStreamUtil.createVoiceExternalResource(textSpeakService.getText2Speak(centerMessage.replaceFirst(botName, "").replace("@" + botId, "").replaceFirst("说", ""))));
                group.sendMessage(voice);
                return ListeningStatus.LISTENING;
            }
        }catch (Exception e){
            group.sendMessage("音频发送失败 :"+e);
        }







        if (keyMatchUtil.keyMatchByKey(message, new String[]{"傻逼", "垃圾", "废物", "爬", "爪巴",
                "弱智", "狗", "猪", "妈", "马", "蠢", "SB", "sb", "Sb", "几把"}) && message.contains("at:" + event.getBot().getId())) {
            group.sendMessage(new At(member.getId()).plus(shaDiaoApi.getHuoLiQuanKai()));
            return ListeningStatus.LISTENING;
        } else if (message.contains("鸡汤") || message.contains("励志")) {
            group.sendMessage(shaDiaoApi.getDuJiTang());
            return ListeningStatus.LISTENING;
        }

        if (message.contains("查看早报")) {
            zaoBao();
        }


        /**
         *  来点妹子图
         */

            if (message.contains("来点妹子 *")) {
                String replace = centerMessage.replace(" ", "");
                int i = replace.indexOf("*") + 1;
                int length = replace.length();
                int num = Integer.parseInt(replace.substring(i, length));
                if (num > 30) {
                    group.sendMessage("这么多你看的过来吗");
                    return ListeningStatus.LISTENING;
                } else if (num <= 0) {
                    group.sendMessage("正常一点,要看多少张,这" + num + "发个鸡巴啊");
                    return ListeningStatus.LISTENING;
                } else {
                    for (int j = 0; j < num; j++) {
                        try {
                            image = group.uploadImage(urlStreamUtil.createImgExternalResource("https://imgapi.cn/cos.php?return=img"));
                            group.sendMessage(image);
                        } catch (Exception e) {
                            System.out.println("抛出异常");
                        }
                    }
                    return ListeningStatus.LISTENING;
                }
            }

            if (message.contains("来点妹子")) {
                try {
                    image = group.uploadImage(urlStreamUtil.createImgExternalResource("https://imgapi.cn/cos.php?return=img"));
                    group.sendMessage(image);
                }catch (Exception e) {
                    System.out.println("抛出异常");
                }
                return ListeningStatus.LISTENING;
            }





        /**
         *  舔狗语录
         */
        if (keyMatchUtil.keyMatchByKey(message, new String[]{"舔狗日记", "舔狗语录"})) {
            group.sendMessage(tianDog.getTianDogResult());
            return ListeningStatus.LISTENING;
        }

        /**
         * 舔人夸人
         */
        if (keyMatchUtil.keyMatchByKey(message, new String[]{"#舔她", "#舔他", "#夸她", "#夸他"})) {
            group.sendMessage(new At(stringUtil.getLongByStr(message)).plus(shaDiaoApi.getCaiHongPi()));
            return ListeningStatus.LISTENING;
        }

        
        /**
         * 搜索涩图,发涩图
         */
        try {
            if (message.contains("#搜索涩图")) {
                loliconKeyword = centerMessage.replace("#搜索涩图", "").trim();
                image = group.uploadImage(urlStreamUtil.createImgExternalResource(loliconApi.getLoliconListByKey(sfR18, loliconKeyword,sfys)));
                group.sendMessage(image);
                return ListeningStatus.LISTENING;
            } else if (keyMatchUtil.keyMatchByKey(message, new String[]{"多来点涩图", "涩图摩多", "多张涩图"})) {
                if (loliconCount == -1) {
                    loliconApi.loliconListRf(sfys);
                    loliconCount = 0;
                }

                if (loliconR18Count == -1) {
                    loliconApi.loliconR18ListRf(sfys);
                    loliconR18Count = 0;
                }

                if (loliconCount == loliconApi.getR18Count(0) - 1) {
                    loliconApi.loliconListRf(sfys);
                    loliconCount = 0;
                }

                if (loliconR18Count == loliconApi.getR18Count(1) - 1) {
                    loliconApi.loliconR18ListRf(sfys);
                    loliconR18Count = 0;
                }


                if (sfR18 == 0) {
                    for (int i = 0; i < 10; i++) {
                        loliconCount++;
                        if (loliconCount == loliconApi.getR18Count(0) - 1) {
                            loliconApi.loliconListRf(sfys);
                            loliconCount = 0;
                        }

                        if (loliconR18Count == loliconApi.getR18Count(1) - 1) {
                            loliconApi.loliconR18ListRf(sfys);
                            loliconR18Count = 0;
                        }
                        image = group.uploadImage(urlStreamUtil.createImgExternalResource(loliconApi.getUrl(0, loliconCount)));
                        group.sendMessage(image);
                    }
                    return ListeningStatus.LISTENING;
                } else {
                    if (loliconGroup.contains(groupId.toString())) {
                        for (int i = 0; i < 10; i++) {
                            loliconCount++;
                            if (loliconCount == loliconApi.getR18Count(0) - 1) {
                                loliconApi.loliconListRf(sfys);
                                loliconCount = 0;
                            }

                            if (loliconR18Count == loliconApi.getR18Count(1) - 1) {
                                loliconApi.loliconR18ListRf(sfys);
                                loliconR18Count = 0;
                            }
                            image = group.uploadImage(urlStreamUtil.createImgExternalResource(loliconApi.getUrl(0, loliconCount)));
                            group.sendMessage(image);
                            return ListeningStatus.LISTENING;
                        }
                    } else {
                        for (int i = 0; i < 10; i++) {
                            loliconR18Count++;
                            if (loliconCount == loliconApi.getR18Count(0) - 1) {
                                loliconApi.loliconListRf(sfys);
                                loliconCount = 0;
                            }

                            if (loliconR18Count == loliconApi.getR18Count(1) - 1) {
                                loliconApi.loliconR18ListRf(sfys);
                                loliconR18Count = 0;
                            }
                            image = group.uploadImage(urlStreamUtil.createImgExternalResource(loliconApi.getUrl(sfR18, loliconR18Count)));
                            group.sendMessage(image);
                        }
                        return ListeningStatus.LISTENING;
                    }
                }
            } else {
                if (keyMatchUtil.keyMatchByKey(message, new String[]{"涩图", "色图", "图来", "setu"})) {
                    if (loliconCount == -1) {
                        loliconApi.loliconListRf(sfys);
                        loliconCount = 0;
                    }

                    if (loliconR18Count == -1) {
                        loliconApi.loliconR18ListRf(sfys);
                        loliconR18Count = 0;
                    }

                    if (loliconCount == loliconApi.getR18Count(0) - 1) {
                        loliconApi.loliconListRf(sfys);
                        loliconCount = 0;
                    }

                    if (loliconR18Count == loliconApi.getR18Count(1) - 1) {
                        loliconApi.loliconR18ListRf(sfys);
                        loliconR18Count = 0;
                    }


                    if (sfR18 == 0) {
                        loliconCount++;
                        image = group.uploadImage(urlStreamUtil.createImgExternalResource(loliconApi.getUrl(0, loliconCount)));
                        group.sendMessage(image);
                        return ListeningStatus.LISTENING;
                    } else {
                        if (loliconGroup.contains(groupId.toString())) {
                            loliconCount++;
                            image = group.uploadImage(urlStreamUtil.createImgExternalResource(loliconApi.getUrl(0, loliconCount)));
                            group.sendMessage(image);
                            return ListeningStatus.LISTENING;
                        } else {
                            loliconR18Count++;
                            image = group.uploadImage(urlStreamUtil.createImgExternalResource(loliconApi.getUrl(sfR18, loliconR18Count)));
                            group.sendMessage(image);
                            return ListeningStatus.LISTENING;
                        }
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            group.sendMessage("呜呼,涩图看太多了," + botName + "卡死了,不过没关系已为你自动修复,再发一口令吧!");
            //异常处理
            loliconApi.loliconListRf(sfys);
            loliconCount = 0;
            loliconApi.loliconR18ListRf(sfys);
            loliconR18Count = 0;
            System.out.println("异常跳出" + e);
        } catch (Exception e) {
            System.out.println("异常跳出" + e);
        }



        /**
         * 辱骂
         */
        if (message.contains("辱骂[mirai:at:")) {
            if (whiteListStr.contains(stringUtil.getLongByStr(message).toString())) {
                group.sendMessage("不可以骂!");
                return ListeningStatus.LISTENING;
            } else if (message.contains("辱骂[mirai:at:" + event.getBot().getId())) {
                group.sendMessage(new At(member.getId()).plus(shaDiaoApi.getHuoLiQuanKai()));
                return ListeningStatus.LISTENING;
            } else if (sfzc == 0) {
                group.sendMessage(new At(stringUtil.getLongByStr(message)).plus(shaDiaoApi.getKouTuLianHua()));
                return ListeningStatus.LISTENING;
            } else {
                group.sendMessage(new At(stringUtil.getLongByStr(message)).plus(shaDiaoApi.getHuoLiQuanKai()));
                return ListeningStatus.LISTENING;
            }
        }

        /**
         * 热搜
         */
        if (message.contains("#热搜")) {
            if (stringUtil.getResourceDelKeyWord("#热搜", event) != null) {
                group.sendMessage(hotNewsTop.getHotNewsTop(stringUtil.getResourceDelKeyWord("#热搜", event)));
                return ListeningStatus.LISTENING;
            }
        }

        /**
         * 查询mc服务器当前状态
         */
        if (message.contains("#MC服务器状态")) {
            String mcStatusUrl = "https://api.imlazy.ink/mcapi/?name=Fucking%20Party&host=124.223.170.57:25565&type=image";
            image = group.uploadImage(urlStreamUtil.createImgExternalResource(mcStatusUrl));
            group.sendMessage(image);
            return ListeningStatus.LISTENING;
        }

        /**
         *@描述
         *@参数 [event]
         *@返回值 net.mamoe.mirai.event.ListeningStatus
         *@创建人 Liudw
         *@创建时间 2022/5/17
         */
         if (message.contains("#识图")){
             MessageChain messages = event.getMessage();
             for (SingleMessage singleMessage : messages) {
                 if(singleMessage instanceof Image){
                     group.sendMessage(saucenaoService.seachResultStr(Image.queryUrl((Image) singleMessage),group));
                 }
             }
         }



        /**
         * 发送一言
         */
        if (message.contains("#一言")) {
            if (stringUtil.getResourceDelKeyWord("#一言", event) != null) {
                if (message.contains("#一言 help")) {
                    group.sendMessage("使用方法 #一言 + 类型,类型可为空 \n" +
                            "动画\n" +
                            "漫画\n" +
                            "游戏\n" +
                            "文学\n" +
                            "原创\n" +
                            "来自网络\n" +
                            "其他\n" +
                            "影视\n" +
                            "诗词\n" +
                            "网易云\n" +
                            "哲学\n");

                } else {
                    group.sendMessage(hitokotoApi.getHitokotoResult(stringUtil.getResourceDelKeyWord("#一言", event)));
                }
                return ListeningStatus.LISTENING;
            }
        }

        /**
         * 发送番剧情况
         */
        if (keyMatchUtil.keyMatchByKey(message, new String[]{"#今日动漫", "今天动漫更新情况", "#番剧更新", "#今日番剧"})) {
            group.sendMessage(bangumiApi.getAnimeResult());
            return ListeningStatus.LISTENING;
        } else if (keyMatchUtil.keyMatchByKey(message, new String[]{"#昨日番剧", "#明日番剧"})) {
            group.sendMessage(bangumiApi.getAnimeResultByMessage(centerMessage));
            return ListeningStatus.LISTENING;
        }

        /**
         * 发送游戏特惠情况
         */
        if (message.contains("#游戏特惠情况")) {
            group.sendMessage(xiaoHeiHeApi.getGamesInfo());
            return ListeningStatus.LISTENING;
        } else if (message.contains("#游戏价格查询")) {
            group.sendMessage(xiaoHeiHeApi.getGameByName(message));
            return ListeningStatus.LISTENING;
        }

        /**
         * 百度网盘取码
         */
        if (message.contains("#取码")) {
            if (message.contains("pan.baidu.com")) {
                String replace = centerMessage.replace("#取码", "").trim();
                group.sendMessage(baiduPanGetCodeAPI.getDuPanCode(replace));
            } else {
                group.sendMessage("请发送正确的度盘链接");
            }
            return ListeningStatus.LISTENING;
        }

        /**
         * 皮皮虾,抖音去水印
         */
        if (message.contains("#抖音去水印")) {
            String replace = centerMessage.replace("#抖音去水印", "").trim();
            group.sendMessage(videoDelWaterMarkApi.getDYRealUrl(replace));
            return ListeningStatus.LISTENING;
        } else if (message.contains("#皮皮虾去水印")) {
            String replace = centerMessage.replace("#皮皮虾去水印", "").trim();
            group.sendMessage(videoDelWaterMarkApi.getPPXRealUrl(replace));
            return ListeningStatus.LISTENING;
        }


        //获取问答答案
        if(qaService.getCountByContent(replaceMessageCenter)>=1){
            group.sendMessage(qaService.getResultByContent(replaceMessageCenter));
            return ListeningStatus.LISTENING;
        }


        /**
         * 替换BOT name
         */
        if(sfxl==1&& (int)(Math.random()*100)<10){
            group.sendMessage(moliBotService.moLiReply(centerMessage,2,memberId, member.getNick(),memberId,member.getNick()));
            return ListeningStatus.LISTENING;
        }else if (!message.contains("小龙女")) {
            if (message.contains("at:" + event.getBot().getId()) && !message.contains("辱骂[mirai:at:")) {
                String msg = centerMessage.replace(botName, "小龙女");
                group.sendMessage(txAiUtil.replaceMessage(msg.replace("@" + botId.toString(), "")));
                return ListeningStatus.LISTENING;
            } else if (txAiGroupList.contains(groupId.toString())) {
                String msg = centerMessage.replace(botName, "小龙女");
                if (msg.contains("小龙女")) {
                    String fReply = txAiUtil.replaceMessage(msg);
                    if (fReply.replace("[图片]", "").trim() == "" || fReply == "") {

                    } else {
                        group.sendMessage(fReply);
                        return ListeningStatus.LISTENING;
                    }
                }
            }
        }





        return ListeningStatus.LISTENING;
    }

/*
    Listener listener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent.class, event -> {

    });
*/
}
