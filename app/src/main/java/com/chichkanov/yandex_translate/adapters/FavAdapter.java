package com.chichkanov.yandex_translate.adapters;

import com.chichkanov.yandex_translate.models.HistoryItem;
import com.chichkanov.yandex_translate.interfaces.OnFavClickListener;

import java.util.List;


public class FavAdapter extends HistoryAdapter {

    public FavAdapter(List<HistoryItem> dataset, OnFavClickListener listener) {
        super(dataset, listener);
    }

}
