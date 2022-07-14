
package com.mirai.water.sweetbot.entity.zaobao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Data {

    @Expose
    private String date;
    @SerializedName("head_image")
    private String headImage;
    @Expose
    private String image;
    @Expose
    private List<String> news;
    @Expose
    private String weiyu;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getNews() {
        return news;
    }

    public void setNews(List<String> news) {
        this.news = news;
    }

    public String getWeiyu() {
        return weiyu;
    }

    public void setWeiyu(String weiyu) {
        this.weiyu = weiyu;
    }

}
