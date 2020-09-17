package com.zongyuheng.pojo;

import org.thymeleaf.util.StringUtils;

public class BlotQuery {
    private String  title;
    private Long typeId;
    private Boolean recommend;

    public BlotQuery() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }

    public BlotQuery(String title, Long typeId, Boolean recommend) {
        this.title = title;
        this.typeId = typeId;
        this.recommend = recommend;
    }
}
