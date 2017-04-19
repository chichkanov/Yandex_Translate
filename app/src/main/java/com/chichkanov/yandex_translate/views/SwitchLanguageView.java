package com.chichkanov.yandex_translate.views;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.chichkanov.yandex_translate.R;
import com.chichkanov.yandex_translate.utils.ConstResources;

public class SwitchLanguageView extends RelativeLayout {

    private Spinner spinnerFrom, spinnerTo;
    private ImageButton switchLanguage;
    private ArrayAdapter<CharSequence> adapter;
    private SpinnerChangeListener spinnerChangeListener;
    private int prevSpinnerFromPos;
    private int prevSpinnerToPos;

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
        adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.languages_array, R.layout.spinner_item_main);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        switchLanguage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                savePrevSpinnerPos();
                if (spinnerChangeListener != null) spinnerChangeListener.swapTranslateResults();
                int index = spinnerFrom.getSelectedItemPosition();
                spinnerFrom.setSelection(spinnerTo.getSelectedItemPosition());
                spinnerTo.setSelection(index);
            }
        });

        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (spinnerFrom.getSelectedItemPosition() == spinnerTo.getSelectedItemPosition()) {
                    spinnerFrom.setSelection(spinnerTo.getSelectedItemPosition());
                    spinnerTo.setSelection(prevSpinnerFromPos);
                    savePrevSpinnerPos();
                }
                if (spinnerChangeListener != null) spinnerChangeListener.initTranslation();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinnerTo.getSelectedItemPosition() == spinnerFrom.getSelectedItemPosition()) {
                    spinnerTo.setSelection(spinnerFrom.getSelectedItemPosition());
                    spinnerFrom.setSelection(prevSpinnerToPos);
                    savePrevSpinnerPos();
                }
                if (spinnerChangeListener != null) spinnerChangeListener.initTranslation();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void savePrevSpinnerPos() {
        prevSpinnerFromPos = spinnerFrom.getSelectedItemPosition();
        prevSpinnerToPos = spinnerTo.getSelectedItemPosition();
    }


    public void setSpinnerFromPos(int pos) {
        spinnerFrom.setSelection(pos);
    }

    public void setSpinnerToPos(int pos) {
        spinnerTo.setSelection(pos);
    }

    public int getSpinnerFromPos() {
        return spinnerFrom.getSelectedItemPosition();
    }

    public int getSpinnerToPos() {
        return spinnerTo.getSelectedItemPosition();
    }

    public String getSpinnerFromText() {
        return spinnerFrom.getSelectedItem().toString();
    }

    public String getSpinnerToText() {
        return spinnerTo.getSelectedItem().toString();
    }

    public void setSpinnerTextFrom(String text) {
        String shortLangName = ConstResources.getKeyByValue(text);
        spinnerFrom.setSelection(adapter.getPosition(shortLangName));
    }

    public void setSpinnerChangeListener(SpinnerChangeListener spinnerChangeListener) {
        this.spinnerChangeListener = spinnerChangeListener;
    }

    public void setPrevSpinnerFromPos(int prevSpinnerFromPos) {
        this.prevSpinnerFromPos = prevSpinnerFromPos;
    }

    public void setPrevSpinnerToPos(int prevSpinnerToPos) {
        this.prevSpinnerToPos = prevSpinnerToPos;
    }

    public interface SpinnerChangeListener {
        void initTranslation();

        void swapTranslateResults();
    }
}
