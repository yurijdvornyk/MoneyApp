package com.example.yuriidvornyk.moneyapp.presentation.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;

/**
 * Created by yurii.dvornyk on 2017-12-11.
 */

public class ListenableEditText extends AppCompatEditText {

    private TextChangeListener listener;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (listener != null) {
                listener.onTextChanged(s);
            }
        }
    };

    public ListenableEditText(Context context) {
        super(context);
    }

    public ListenableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListenableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setListener(TextChangeListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        addTextChangedListener(textWatcher);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeTextChangedListener(textWatcher);
    }

    @Override
    public boolean dispatchKeyEvent(@NonNull KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP
                && event.getKeyCode() == KeyEvent.KEYCODE_DEL
                && listener != null) {
            listener.onTextChanged(getText());
        }
        return super.dispatchKeyEvent(event);
    }

    public interface TextChangeListener {

        void onTextChanged(Editable text);
    }
}
