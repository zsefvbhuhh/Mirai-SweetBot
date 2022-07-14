
package com.mirai.water.sweetbot.entity.saucenao;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

public class SaucenaoSearchInfoResultHeader {
    /**
     * similarity : 89.60
     * thumbnail : https://img1.saucenao.com/res/pixiv/5552/manga/55527150_p16.jpg?auth=e1zHkxDtXQhHHV96oN7lxA&exp=1582083465
     * index_id : 5
     * index_name : Index #5: Pixiv Images - 55527150_p16.jpg
     */

    //查询结果相似度
    private String similarity;
    //saucenao查询结果缩略图
    private String thumbnail;
    //应该是在saucenao里的索引类型，5应该表示来源于p站 9代表Danbooru
    private int index_id;
    //索引文本
    private String index_name;

    public String getSimilarity() {
        return similarity;
    }

    public void setSimilarity(String similarity) {
        this.similarity = similarity;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getIndex_id() {
        return index_id;
    }

    public void setIndex_id(int index_id) {
        this.index_id = index_id;
    }

    public String getIndex_name() {
        return index_name;
    }

    public void setIndex_name(String index_name) {
        this.index_name = index_name;
    }
}
