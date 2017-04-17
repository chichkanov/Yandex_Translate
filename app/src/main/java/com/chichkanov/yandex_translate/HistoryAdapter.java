package com.chichkanov.yandex_translate;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<HistoryItem> dataset;

    public HistoryAdapter(List<HistoryItem> dataset) {
        this.dataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.from.setText(dataset.get(position).getTextFrom());
        holder.to.setText(dataset.get(position).getTextTo());
        holder.lang.setText(dataset.get(position).getLang());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView from;
        public TextView to;
        public TextView lang;

        public ViewHolder(View view) {
            super(view);
            from = (TextView) view.findViewById(R.id.tv_history_from);
            to = (TextView) view.findViewById(R.id.tv_history_to);
            lang = (TextView) view.findViewById(R.id.tv_history_lang);
        }
    }
}
