package com.chichkanov.yandex_translate;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by chichkanov on 15.04.17.
 */

public class TranslatedFormView extends RelativeLayout {

    private TextView textView;
    private ImageButton imageButton;

    public TranslatedFormView(Context context) {
        super(context);
        initViews();
    }

    public TranslatedFormView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_form_translated, this);
        textView = (TextView) findViewById(R.id.tv_translated);
        imageButton = (ImageButton) findViewById(R.id.btn_translated_fav);

        imageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton.setSelected(!imageButton.isSelected());
            }
        });
    }

    public void setText(String text) {
        textView.setText(text);
    }
}
