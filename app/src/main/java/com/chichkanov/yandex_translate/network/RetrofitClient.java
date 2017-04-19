package com.chichkanov.yandex_translate.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chichkanov on 16.04.17.
 */

public class RetrofitClient {

    private final static String URL = "https://translate.yandex.net";

    private static Retrofit newRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static YandexTranslateApi getYandexTranslateApi() {
        return newRetrofitInstance().create(YandexTranslateApi.class);
    }

}
