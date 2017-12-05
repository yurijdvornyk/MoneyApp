package com.example.yuriidvornyk.moneyapp.data.model;

import org.parceler.Parcel;
import org.threeten.bp.LocalDateTime;

/**
 * Created by yurii.dvornyk on 2017-11-22.
 */
@Parcel(Parcel.Serialization.BEAN)
public class Project {
    private Integer id;
    private String name;
    private String defaultCurrency;
    private LocalDateTime createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(String defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
