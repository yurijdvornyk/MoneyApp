package com.example.yuriidvornyk.moneyapp.data.model;

import org.parceler.Parcel;

/**
 * Created by yurii.dvornyk on 2017-11-28.
 */
@Parcel(Parcel.Serialization.BEAN)
public class Currency {

    private String name;
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
