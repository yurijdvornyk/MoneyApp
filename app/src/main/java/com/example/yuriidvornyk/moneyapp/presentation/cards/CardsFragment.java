package com.example.yuriidvornyk.moneyapp.presentation.cards;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.yuriidvornyk.moneyapp.databinding.FragmentCardsBinding;
import com.example.yuriidvornyk.moneyapp.presentation.base.BaseFragment;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

/**
 * Created by yurii.dvornyk on 2017-12-15.
 */

public class CardsFragment extends BaseFragment<CardsContract.Presenter> implements CardsContract.View {

    private static final int ADD_CARD_REQUEST_CODE = 555678;

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

    public void onAddCardClicked(View v) {
        Intent scanIntent = new Intent(getContext(), CardIOActivity.class);

        // customize these values to suit your needs.
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false

        // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
        startActivityForResult(scanIntent, ADD_CARD_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_CARD_REQUEST_CODE) {
            String resultDisplayStr;
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                final CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

                // TODO:

                // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
                resultDisplayStr = "Card Number: " + scanResult.getRedactedCardNumber() + "\n";

                // Do something with the raw number, e.g.:
                // myService.setCardNumber( scanResult.cardNumber );

                if (scanResult.isExpiryValid()) {
                    resultDisplayStr += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n";
                }

                if (scanResult.cvv != null) {
                    // Never log or display a CVV
                    resultDisplayStr += "CVV has " + scanResult.cvv.length() + " digits.\n";
                }

                if (scanResult.postalCode != null) {
                    resultDisplayStr += "Postal Code: " + scanResult.postalCode + "\n";
                }
            }
            else {
                resultDisplayStr = "Scan was canceled.";
            }
            // do something with resultDisplayStr, maybe display it in a textView
            // resultTextView.setText(resultDisplayStr);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
