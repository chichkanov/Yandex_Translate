package com.chichkanov.yandex_translate;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
    private TranslateFormView translateForm;
    private TranslatedFormView translatedForm;
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
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(title);
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
    }

    private void hideTranslatedForm() {
        translatedForm.setVisibility(View.GONE);
    }

    private void showTranslatedForm() {
        translatedForm.setVisibility(View.VISIBLE);
    }


    private void loadTranslate() {
        Map<String, String> keys = new HashMap<>();
        keys.put("key", "trnsl.1.1.20170415T124611Z.ad6b17355364d141.64ba3777be0638c0334939d343b1920945530598");
        keys.put("text", translateForm.getText());
        keys.put("lang", "en-ru");

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
