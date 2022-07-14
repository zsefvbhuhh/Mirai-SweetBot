
package com.mirai.water.sweetbot.entity.bangumi;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class TitleTranslate {

    @SerializedName("zh-Hans")
    private List<String> mZhHans;

    public List<String> getZhHans() {
        return mZhHans;
    }

    public void setZhHans(List<String> zhHans) {
        mZhHans = zhHans;
    }

}
