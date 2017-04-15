package com.chichkanov.yandex_translate;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;

public class SwitchLanguageView extends RelativeLayout{

    private Spinner spinnerFrom, spinnerTo;
    private ImageButton switchLanguage;

    public SwitchLanguageView(Context context) {
        super(context);
        initViews();
    }

    public SwitchLanguageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_language_switch, this);
        spinnerFrom = (Spinner) findViewById(R.id.spinner_translate_from);
        spinnerTo = (Spinner) findViewById(R.id.spinner_translate_to);
        switchLanguage = (ImageButton) findViewById(R.id.ib_translate_switch);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.languages_array, R.layout.spinner_item_main);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);
        spinnerFrom.setSelection(0);
        spinnerTo.setSelection(1);
    }
}
