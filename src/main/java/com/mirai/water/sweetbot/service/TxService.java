package com.mirai.water.sweetbot.service;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.nlp.v20190408.NlpClient;
import com.tencentcloudapi.nlp.v20190408.models.ChatBotRequest;
import com.tencentcloudapi.nlp.v20190408.models.ChatBotResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class TxService {

    @Value("${txApi.SecretId}")
    String txApiSecretId;

    @Value("${txApi.SecretKey}")
    String txSecretKey;

    public String getTxApiReply(String msg){
        try{

            Credential cred = new Credential(txApiSecretId, txSecretKey);

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("nlp.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            NlpClient client = new NlpClient(cred, "ap-guangzhou", clientProfile);

            ChatBotRequest req = new ChatBotRequest();
            req.setQuery(msg);

            ChatBotResponse resp = client.ChatBot(req);
            return resp.getReply();
        } catch (TencentCloudSDKException e) {
            return e.toString();
        }
    }

}
