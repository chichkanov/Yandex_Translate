package com.chichkanov.yandex_translate;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by chichkanov on 17.04.17.
 */

public class HistoryItem implements Comparable<HistoryItem> {

    @SerializedName("lang")
    @Expose
    private String lang;

    @SerializedName("textTo")
    @Expose
    private String to;

    @SerializedName("textFrom")
    @Expose
    private String from;

    @SerializedName("date")
    @Expose
    private long date;

    public HistoryItem(String lang, String to, String from, long date){
        this.lang = lang;
        this.to = to;
        this.from = from;
        this.date = date;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getTextFrom() {
        return from;
    }

    public String getTextTo() {
        return to;
    }

    public long getDate() {
        return date;
    }

    @Override
    public int compareTo(@NonNull HistoryItem o) {
        if (date > o.getDate()) {
            return -1;
        }
        else if (date <  o.getDate()) {
            return 1;
        }
        else {
            return 0;
        }
    }
}
