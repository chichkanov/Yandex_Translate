package com.chichkanov.yandex_translate;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.SharedPreferencesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TranslateFragment extends Fragment {

    private static final String ARG_TITLE = "Переводчик";
    private static final String CURRENT_TRANSLATION = "Translation";
    private static final String TRANSLATION_SPINNER_FROM = "Translation_spinner_from";
    private static final String TRANSLATION_SPINNER_TO = "Translation_spinner_to";

    private TranslateFormView translateForm;
    private TranslatedFormView translatedForm;
    private SwitchLanguageView switchLanguageForm;

    private String title;

    YandexTranslateApi api = RetrofitClient.getYandexTranslateApi();

    public TranslateFragment() {
    }

    public static TranslateFragment newInstance(String param1) {
        TranslateFragment fragment = new TranslateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_translate, container, false);
        translateForm = (TranslateFormView) view.findViewById(R.id.view_translate_form);
        translatedForm = (TranslatedFormView) view.findViewById(R.id.view_translated_form);
        switchLanguageForm = (SwitchLanguageView) view.findViewById(R.id.view_switch_form);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(title);
        restoreSwitchLanguageState();

        // Моментальынй перевод текста при его изменении в форме для ввода
        translateForm.setTextChangingListener(new TranslateFormView.TextChangingListener() {
            @Override
            public void initTranslation() {
                showTranslatedForm();
                loadTranslate();
            }

            @Override
            public void removeTranslation() {
                hideTranslatedForm();
            }
        });

        // Перевод текста при изменении выбранного языка
        switchLanguageForm.setSpinnerChangeListener(new SwitchLanguageView.SpinnerChangeListener() {
            @Override
            public void initTranslation() {
                loadTranslate();
            }

            @Override
            public void swapTranslateResults() {
                translateForm.setText(translatedForm.getText());
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CURRENT_TRANSLATION, translatedForm.getText());
    }

    @Override
    public void onStop() {
        super.onStop();
        saveSwitchLanguageState();
    }

    private void saveSwitchLanguageState() {
        SharedPreferences prefs = getActivity().getSharedPreferences(ConstResources.PREFS_SPINNERS_STATE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(TRANSLATION_SPINNER_FROM, switchLanguageForm.getSpinnerFromPos());
        editor.putInt(TRANSLATION_SPINNER_TO, switchLanguageForm.getSpinnerToPos());
        editor.apply();
    }

    private void restoreSwitchLanguageState() {
        SharedPreferences prefs = getActivity().getSharedPreferences(ConstResources.PREFS_SPINNERS_STATE, Context.MODE_PRIVATE);
        int pos1 = prefs.getInt(TRANSLATION_SPINNER_FROM, 0);
        int pos2 = prefs.getInt(TRANSLATION_SPINNER_TO, 1);
        switchLanguageForm.setSpinnerFromPos(pos1);
        switchLanguageForm.setSpinnerToPos(pos2);
        switchLanguageForm.setPrevSpinnerFromPos(pos1);
        switchLanguageForm.setPrevSpinnerToPos(pos2);
    }

    private void hideTranslatedForm() {
        translatedForm.clearText();
        translatedForm.setVisibility(View.GONE);
    }

    private void showTranslatedForm() {
        translatedForm.setVisibility(View.VISIBLE);
    }


    private void loadTranslate() {
        if (translateForm.getText().length() > 0) {
            detectLanguage();
            String translation = ConstResources.LANGUAGES.get(switchLanguageForm.getSpinnerFromText()) + "-"
                    + ConstResources.LANGUAGES.get(switchLanguageForm.getSpinnerToText());
            Map<String, String> keys = new HashMap<>();
            keys.put("key", ConstResources.KEY);
            keys.put("text", translateForm.getText());
            keys.put("lang", translation);

            Call<YandexTranslateResponse> call = api.translate(keys);
            call.enqueue(new Callback<YandexTranslateResponse>() {
                @Override
                public void onResponse(Call<YandexTranslateResponse> call, Response<YandexTranslateResponse> response) {
                    if (response.isSuccessful()) {
                        translatedForm.setText(response.body().getText().get(0));
                    } else {
                        Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<YandexTranslateResponse> call, Throwable t) {

                }
            });
        }
    }

    private void detectLanguage() {
        Map<String, String> keys = new HashMap<>();
        keys.put("key", ConstResources.KEY);
        keys.put("hint", "en,ru");
        keys.put("text", translateForm.getText());

        Call<YandexDetectResponse> call = api.detectLang(keys);

        call.enqueue(new Callback<YandexDetectResponse>() {
            @Override
            public void onResponse(Call<YandexDetectResponse> call, Response<YandexDetectResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200 && response.body().getLang().length() > 0) {
                        switchLanguageForm.setPrevSpinnerFromPos(switchLanguageForm.getSpinnerFromPos());
                        switchLanguageForm.setSpinnerTextFrom(response.body().getLang());
                    }
                }
            }

            @Override
            public void onFailure(Call<YandexDetectResponse> call, Throwable t) {

            }
        });
    }
}
