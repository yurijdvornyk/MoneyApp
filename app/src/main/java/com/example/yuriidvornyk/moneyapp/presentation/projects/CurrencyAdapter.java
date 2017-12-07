package com.example.yuriidvornyk.moneyapp.presentation.projects;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.yuriidvornyk.moneyapp.data.model.Currency;
import com.example.yuriidvornyk.moneyapp.databinding.ItemCurrencySpinnerBinding;

import java.util.List;

/**
 * Created by yurii.dvornyk on 2017-11-28.
 */

public class CurrencyAdapter extends ArrayAdapter<Currency> {

    private static final String CURRENCY_FORMAT = "%s (%s)";

    public CurrencyAdapter(@NonNull Context context, int resource, @NonNull List<Currency> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        final Currency currency = getItem(position);
        if (view == null) {
            final ItemCurrencySpinnerBinding binding = ItemCurrencySpinnerBinding
                    .inflate(LayoutInflater.from(getContext()), parent, false);
            view = binding.getRoot();
            holder = new ViewHolder();
            holder.titleTextView = binding.text;
            view.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.titleTextView.setText(formatItemContent(currency));
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    protected String formatItemContent(Currency currency) {
        return String.format(CURRENCY_FORMAT, currency.getCode(), currency.getName());
    }

    class ViewHolder {
        TextView titleTextView;
    }
}
