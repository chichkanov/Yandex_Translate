package com.chichkanov.yandex_translate;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class FavFragment extends Fragment {

    private static final String ARG_TITLE = "Избранное";
    private String title;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    List<HistoryItem> dataset = new ArrayList<>();

    public FavFragment() {
    }

    public static FavFragment newInstance(String param1) {
        FavFragment fragment = new FavFragment();
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
        View view = inflater.inflate(R.layout.fragment_fav, container, false);
        initRecyclerView(view);
        return view;
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_fav);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        dataset = loadCachedResponse();
        adapter = new FavAdapter(dataset, new OnFavClickListener() {
            @Override
            public void onFavClick(int position) {

            }
        });
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

    }

    private void removeItemFromVar() {
    }

    private List<HistoryItem> loadCachedResponse() {
        Map<String, String> allEntries = (Map<String, String>) getContext().getSharedPreferences(ConstResources.PREFS_CACHE_NAME, Context.MODE_PRIVATE).getAll();
        List<HistoryItem> list = new ArrayList<>();
        Gson gson = new Gson();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String json = entry.getValue().toString();
            HistoryItem historyItem = gson.fromJson(json, HistoryItem.class);
            if(historyItem.isMarkedFav())list.add(new HistoryItem(historyItem.getLang(), historyItem.getTextTo(), historyItem.getTextFrom(), historyItem.getDate(), historyItem.isMarkedFav()));
        }
        Collections.sort(list);
        return list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(title);
    }
}
