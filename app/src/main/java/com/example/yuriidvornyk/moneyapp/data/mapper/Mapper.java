package com.example.yuriidvornyk.moneyapp.data.mapper;

/**
 * Created by yurii.dvornyk on 2017-11-28.
 */

interface Mapper<L, R> {

    R fromLocal(L local);

    L toLocal(R remote);
}
