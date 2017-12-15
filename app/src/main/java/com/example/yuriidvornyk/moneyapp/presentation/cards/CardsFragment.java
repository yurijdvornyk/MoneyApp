package com.example.yuriidvornyk.moneyapp.presentation.cards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yuriidvornyk.moneyapp.databinding.FragmentCardsBinding;
import com.example.yuriidvornyk.moneyapp.presentation.base.BaseFragment;

/**
 * Created by yurii.dvornyk on 2017-12-15.
 */

public class CardsFragment extends BaseFragment<CardsContract.Presenter> implements CardsContract.View {

    private FragmentCardsBinding binding;

    public static CardsFragment newInstance() {
        return new CardsFragment();
    }

    public CardsFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCardsBinding.inflate(inflater, container, false);
        presenter = new CardsPresenter();
        return binding.getRoot();
    }
}
