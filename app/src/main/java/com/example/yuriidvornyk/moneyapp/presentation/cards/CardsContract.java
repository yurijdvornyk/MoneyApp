package com.example.yuriidvornyk.moneyapp.presentation.cards;

import com.example.yuriidvornyk.moneyapp.presentation.base.BaseContract;

/**
 * Created by yurii.dvornyk on 2017-12-15.
 */

interface CardsContract {

    interface Presenter extends BaseContract.Presenter<View> {}

    interface View extends BaseContract.View<Presenter> {}
}
