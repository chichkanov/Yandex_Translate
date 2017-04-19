package com.chichkanov.yandex_translate.views;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chichkanov.yandex_translate.R;

public class TranslateFormView extends RelativeLayout{

    private EditText editText;
    private ImageButton imageButton;
    private TextChangingListener textChangingListener;
    private Handler handler;

    // Раннбл для перевода текста
    Runnable instantLoaderTask = new Runnable() {
        @Override
        public void run() {
            textChangingListener.initInstantTranslation();
        }
    };

    public TranslateFormView(Context context) {
        super(context);
        handler = new Handler();
        initViews();
    }

    public TranslateFormView(Context context, AttributeSet attrs) {
        super(context, attrs);
        handler = new Handler();
        initViews();
    }

    private void initViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_et_translate, this);
        editText = (EditText) findViewById(R.id.et_translate);
        imageButton = (ImageButton) findViewById(R.id.btn_translate_clear);

        // Очистка поля ввода текста
        imageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.getText().clear();
            }
        });

        editText.setHorizontallyScrolling(false);
        editText.setLines(4);

        // Добавляем литенер для моментального перевода при изменении текста
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() > 0 && s.toString().charAt(s.toString().length()-1) != ' ') {
                    imageButton.setVisibility(VISIBLE);
                    // Чтобы не делать запросы слишком часто, делаем небольшую задержку между переводами
                    if (textChangingListener != null) {
                        handler.removeCallbacks(instantLoaderTask);
                        handler.postDelayed(instantLoaderTask, 200);
                    }
                } else {
                    imageButton.setVisibility(INVISIBLE);
                    if (textChangingListener != null) textChangingListener.removeTranslation();
                }
            }
        });

        // Листенер для перевода при нажатии Done на клавиатуре
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    textChangingListener.initNormalTranslation();
                }
                return false;
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
        void initInstantTranslation();
        void initNormalTranslation();
        void removeTranslation();
    }
}
