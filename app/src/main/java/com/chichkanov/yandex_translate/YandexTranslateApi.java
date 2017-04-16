package com.chichkanov.yandex_translate;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Retrofit интерфейс для работы с апи переводчика
 */

public interface YandexTranslateApi {
    @FormUrlEncoded
    @POST("/api/v1.5/tr.json/translate")
    Call<YandexTranslateResponse> translate(@FieldMap Map<String, String> keys);
}
