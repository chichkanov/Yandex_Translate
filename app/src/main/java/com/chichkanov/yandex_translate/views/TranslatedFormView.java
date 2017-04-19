package com.chichkanov.yandex_translate.views;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chichkanov.yandex_translate.R;

import static android.content.Context.CLIPBOARD_SERVICE;

public class TranslatedFormView extends RelativeLayout {

    private TextView textView;
    private ImageButton favButton, copyButton;

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
        favButton = (ImageButton) findViewById(R.id.btn_translated_fav);
        copyButton = (ImageButton) findViewById(R.id.btn_translated_copy);

        favButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                favButton.setSelected(!favButton.isSelected());
            }
        });

        copyButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Перевод", getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), "Перевод скопирован", Toast.LENGTH_SHORT).show();
            }
        });

        TextView tvCopyright = (TextView) findViewById(R.id.tv_yandex_copyright);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            tvCopyright.setText( Html.fromHtml("<a href=\"https://translate.yandex.ru\">Переведено сервисом «Яндекс.Переводчик»</a>", Html.FROM_HTML_MODE_LEGACY));
        } else {
            tvCopyright.setText( Html.fromHtml("<a href=\"https://translate.yandex.ru\">Переведено сервисом «Яндекс.Переводчик»</a>"));
        }
        tvCopyright. setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void setText(String text) {
        textView.setText(text);
    }

    public void clearText(){
        textView.setText("");
    }

    public String getText() {
        return textView.getText().toString();
    }
}
