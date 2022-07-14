
package com.mirai.water.sweetbot.entity.saucenao;

import java.util.List;


public class SaucenaoEntity {

    private SaucenaoSearchInfoHeader header;
    //搜索结果
    private List<SaucenaoSearchInfoResult> results;

    public SaucenaoSearchInfoHeader getHeader() {
        return header;
    }

    public void setHeader(SaucenaoSearchInfoHeader header) {
        this.header = header;
    }

    public List<SaucenaoSearchInfoResult> getResults() {
        return results;
    }

    public void setResults(List<SaucenaoSearchInfoResult> results) {
        this.results = results;
    }
}
