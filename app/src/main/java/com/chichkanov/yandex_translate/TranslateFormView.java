package com.chichkanov.yandex_translate;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

/**
 * Created by chichkanov on 15.04.17.
 */

public class TranslateFormView extends RelativeLayout{

    private EditText editText;
    private ImageButton imageButton;
    private TextChangingListener textChangingListener;

    public TranslateFormView(Context context) {
        super(context);
        initViews();
    }

    public TranslateFormView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_et_translate, this);
        editText = (EditText) findViewById(R.id.et_translate);
        imageButton = (ImageButton) findViewById(R.id.btn_translate_clear);
        imageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.getText().clear();
            }
        });

        editText.setHorizontallyScrolling(false);
        editText.setLines(4);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() > 0) {
                    imageButton.setVisibility(VISIBLE);
                    if (textChangingListener != null) {
                        textChangingListener.initTranslation();
                    }
                } else {
                    imageButton.setVisibility(INVISIBLE);
                    if (textChangingListener != null) textChangingListener.removeTranslation();
                }
            }
        });
    }

    public String getText() {
        return editText.getText().toString();
    }

    public void setTextChangingListener(TextChangingListener textChangingListener) {
        this.textChangingListener = textChangingListener;
    }

    public void setText(String text) {
        editText.setText(text);
    }

    // Интерфейс для загрузки перевода
    // Вызывается при изменении текста для моментального перевода
    public interface TextChangingListener {
        void initTranslation();
        void removeTranslation();
    }
}
