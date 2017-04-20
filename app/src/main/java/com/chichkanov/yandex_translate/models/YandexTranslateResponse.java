package com.chichkanov.yandex_translate.models;


// Модель ответа с сервера яндекс переводчика для получения перевода
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YandexTranslateResponse {

    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("text")
    @Expose
    private List<String> text = null;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }
}



