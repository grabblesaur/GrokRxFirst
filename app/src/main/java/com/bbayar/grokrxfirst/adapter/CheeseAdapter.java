package com.bbayar.grokrxfirst.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bbayar.grokrxfirst.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheeseAdapter extends RecyclerView.Adapter<CheeseAdapter.CheeseViewHolder> {

    private List<String> cheeses;

    public CheeseAdapter(List<String> cheeses) {
        this.cheeses = cheeses;
    }

    @Override
    public CheeseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cheese_list_item, parent, false);
        return new CheeseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CheeseViewHolder holder, int position) {
        holder.cheeseName.setText(cheeses.get(position));
    }

    @Override
    public int getItemCount() {
        return cheeses.size();
    }

    public class CheeseViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cheese_name)
        TextView cheeseName;

        public CheeseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
