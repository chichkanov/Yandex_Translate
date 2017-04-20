package com.chichkanov.yandex_translate.fragments;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.chichkanov.yandex_translate.models.HistoryItem;
import com.chichkanov.yandex_translate.interfaces.OnFavClickListener;
import com.chichkanov.yandex_translate.R;
import com.chichkanov.yandex_translate.adapters.HistoryAdapter;
import com.chichkanov.yandex_translate.utils.ConstResources;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class HistoryFragment extends Fragment implements Toolbar.OnMenuItemClickListener {

    private static final String ARG_TITLE = "История";
    private String title;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    List<HistoryItem> dataset = new ArrayList<>();

    public HistoryFragment() {
    }

    public static HistoryFragment newInstance(String param1) {
        HistoryFragment fragment = new HistoryFragment();
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
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        initRecyclerView(view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_history, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(title);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(this);
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_history);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        dataset = loadCacheData();
        adapter = new HistoryAdapter(dataset, new OnFavClickListener() {
            @Override
            public void onFavClick(int position) {
                markAsFav(position);
            }
        });
        recyclerView.setAdapter(adapter);

        // Удаление по свайпу
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                removeItemFromHistory(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    // Удалить из истории
    private void removeItemFromHistory(int position) {
        HistoryItem item = dataset.get(position);
        dataset.remove(position);
        String name = item.getTextFrom() + item.getTextTo() + item.getLang();

        SharedPreferences prefs = getActivity().getSharedPreferences(ConstResources.PREFS_CACHE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(name);
        editor.apply();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            // Очищение истории путем очистки shared prefs
            case R.id.history_action_delete:
                if(dataset.size() > 0) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("История")
                            .setMessage("Вы действительно хотите очистить историю?")
                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences prefs = getActivity().getSharedPreferences(ConstResources.PREFS_CACHE_NAME, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.clear();
                                    editor.apply();
                                    dataset.clear();
                                    adapter.notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("Отмена", null).show();
                }
                return true;
        }
        return false;
    }

    private List<HistoryItem> loadCacheData() {
        Map<String, String> allEntries = (Map<String, String>) getContext().getSharedPreferences(ConstResources.PREFS_CACHE_NAME, Context.MODE_PRIVATE).getAll();
        List<HistoryItem> list = new ArrayList<>();
        Gson gson = new Gson();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String json = entry.getValue().toString();
            HistoryItem historyItem = gson.fromJson(json, HistoryItem.class);
            list.add(new HistoryItem(historyItem.getLang(), historyItem.getTextTo(), historyItem.getTextFrom(), historyItem.getDate(), historyItem.isMarkedFav()));
        }
        Collections.sort(list);
        return list;
    }

    private void markAsFav(int position) {
        HistoryItem item = dataset.get(position);
        item.setMarkedFav(!item.isMarkedFav());

        String name = item.getTextFrom() + item.getTextTo() + item.getLang();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(item);

        SharedPreferences prefs = getActivity().getSharedPreferences(ConstResources.PREFS_CACHE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(name, json);
        editor.apply();

    }
}
