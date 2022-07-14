package com.mirai.water.sweetbot.entity.saucenao;

/**
 * @author by MechellWater
 * @date : 2022-05-18 00:27
 */
public class SaucenaoSearchInfoResult {
    private SaucenaoSearchInfoResultHeader header;
    private SaucenaoSearchInfoResultData data;

    public SaucenaoSearchInfoResultHeader getHeader() {
        return header;
    }

    public void setHeader(SaucenaoSearchInfoResultHeader header) {
        this.header = header;
    }

    public SaucenaoSearchInfoResultData getData() {
        return data;
    }

    public void setData(SaucenaoSearchInfoResultData data) {
        this.data = data;
    }
}
