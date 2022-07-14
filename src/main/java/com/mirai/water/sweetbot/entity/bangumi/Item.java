
package com.mirai.water.sweetbot.entity.bangumi;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Item {

    @SerializedName("begin")
    private String mBegin;
    @SerializedName("broadcast")
    private String mBroadcast;
    @SerializedName("end")
    private String mEnd;
    @SerializedName("id")
    private String mId;
    @SerializedName("lang")
    private String mLang;
    @SerializedName("officialSite")
    private String mOfficialSite;
    @SerializedName("pinyinTitles")
    private List<String> mPinyinTitles;
    @SerializedName("sites")
    private List<Site> mSites;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("titleTranslate")
    private TitleTranslate mTitleTranslate;
    @SerializedName("type")
    private String mType;

    public String getBegin() {
        return mBegin;
    }

    public void setBegin(String begin) {
        mBegin = begin;
    }

    public String getBroadcast() {
        return mBroadcast;
    }

    public void setBroadcast(String broadcast) {
        mBroadcast = broadcast;
    }

    public String getEnd() {
        return mEnd;
    }

    public void setEnd(String end) {
        mEnd = end;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getLang() {
        return mLang;
    }

    public void setLang(String lang) {
        mLang = lang;
    }

    public String getOfficialSite() {
        return mOfficialSite;
    }

    public void setOfficialSite(String officialSite) {
        mOfficialSite = officialSite;
    }

    public List<String> getPinyinTitles() {
        return mPinyinTitles;
    }

    public void setPinyinTitles(List<String> pinyinTitles) {
        mPinyinTitles = pinyinTitles;
    }

    public List<Site> getSites() {
        return mSites;
    }

    public void setSites(List<Site> sites) {
        mSites = sites;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public TitleTranslate getTitleTranslate() {
        return mTitleTranslate;
    }

    public void setTitleTranslate(TitleTranslate titleTranslate) {
        mTitleTranslate = titleTranslate;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

}
