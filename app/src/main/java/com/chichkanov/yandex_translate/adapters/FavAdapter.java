package com.chichkanov.yandex_translate.adapters;

import com.chichkanov.yandex_translate.models.HistoryItem;
import com.chichkanov.yandex_translate.interfaces.OnFavClickListener;

import java.util.List;

/*
Наследуемся от адаптера для истории, так как эти экраны обладают одинаковым функционалом
*/

public class FavAdapter extends HistoryAdapter {

    public FavAdapter(List<HistoryItem> dataset, OnFavClickListener listener) {
        super(dataset, listener);
    }

}
