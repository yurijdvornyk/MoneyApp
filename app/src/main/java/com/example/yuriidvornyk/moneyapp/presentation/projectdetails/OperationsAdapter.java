package com.example.yuriidvornyk.moneyapp.presentation.projectdetails;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.yuriidvornyk.moneyapp.R;
import com.example.yuriidvornyk.moneyapp.data.model.Operation;
import com.example.yuriidvornyk.moneyapp.databinding.ItemOperationsBinding;
import com.example.yuriidvornyk.moneyapp.presentation.utils.OperationUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yurii.dvornyk on 2017-11-24.
 */

class OperationsAdapter extends RecyclerView.Adapter<OperationsAdapter.ViewHolder> {

    private List<Operation> operations;
    private Context context;

    OperationsAdapter(Context context) {
        this.context = context;
        operations = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(ItemOperationsBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(operations.get(position));
    }

    @Override
    public int getItemCount() {
        return operations == null ? 0 : operations.size();
    }

    void setOperations(List<Operation> operations) {
        this.operations = operations;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ItemOperationsBinding binding;

        ViewHolder(ItemOperationsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Operation operation) {
            binding.textOperation.setText(operation.getName());
            binding.textMoneyAmount.setText(OperationUtils.formatBalance(context,
                    operation.getAmount(), operation.getCurrency()));
            setOperationIcon(binding.imageOperationType, operation.getAmount());
        }

        private void setOperationIcon(ImageView imageView, double amount) {
            if (amount > 0) {
                final Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_trending_up_black_24px);
                drawable.setColorFilter(ContextCompat.getColor(context, R.color.green), android.graphics.PorterDuff.Mode.SRC_IN);
                imageView.setImageDrawable(drawable);
            } else if (amount < 0) {
                final Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_trending_down_black_24px);
                drawable.setColorFilter(ContextCompat.getColor(context, R.color.red), android.graphics.PorterDuff.Mode.SRC_IN);
                imageView.setImageDrawable(drawable);
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_trending_flat_black_24px));
            }
        }
    }
}
