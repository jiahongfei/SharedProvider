package com.andrjhf.sharedprovider.demo;

import java.io.Serializable;

/**
 * Created by jiahongfei on 2017/7/19.
 */

public class ArticleK implements Serializable {

    private String title;
    private String desc;
    private String img;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
