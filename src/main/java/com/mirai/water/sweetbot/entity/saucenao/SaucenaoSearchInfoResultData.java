
package com.mirai.water.sweetbot.entity.saucenao;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

public class SaucenaoSearchInfoResultData {

    /**
     * ext_urls : ["https://www.pixiv.net/member_illust.php?mode=medium&illust_id=55527150"]
     * title : いろんなの
     * pixiv_id : 55527150
     * member_name : 湯木間
     * member_id : 3251963
     */

    //P站图片标题
    private String title;
    //P站图片id
    private int pixiv_id;
    //作者名称
    private String member_name;
    //作者id
    private int member_id;
    private List<String> ext_urls;

    /**
     * danbooru_id : 2288681
     * sankaku_id : 5156484
     * creator : yushika
     * material : touhou
     * characters : reisen udongein inaba
     * source : http://i3.pixiv.net/img-original/img/2016/02/28/12/17/08/55527150
     */

    //danbooru图片id
    private Long danbooru_id;
    //danbooru图片标签
    private String characters;

    //其他来源，没用过
    private Long sankaku_id;
    private String creator;
    private String material;
    private String source;
    private String author_name;
    private String eng_name;
    private String jp_name;

    public String getEng_name() {
        return eng_name;
    }

    public void setEng_name(String eng_name) {
        this.eng_name = eng_name;
    }

    public String getJp_name() {
        return jp_name;
    }

    public void setJp_name(String jp_name) {
        this.jp_name = jp_name;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPixiv_id() {
        return pixiv_id;
    }

    public void setPixiv_id(int pixiv_id) {
        this.pixiv_id = pixiv_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public List<String> getExt_urls() {
        return ext_urls;
    }

    public void setExt_urls(List<String> ext_urls) {
        this.ext_urls = ext_urls;
    }

    public Long getDanbooru_id() {
        return danbooru_id;
    }

    public void setDanbooru_id(Long danbooru_id) {
        this.danbooru_id = danbooru_id;
    }

    public String getCharacters() {
        return characters;
    }

    public void setCharacters(String characters) {
        this.characters = characters;
    }

    public Long getSankaku_id() {
        return sankaku_id;
    }

    public void setSankaku_id(Long sankaku_id) {
        this.sankaku_id = sankaku_id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
