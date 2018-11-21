package com.erly.simplemodule.simple;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.erly.simplemodule.R;
import com.erly.simplemodule.pojo.JokeBean;

import java.util.ArrayList;
import java.util.List;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.SimpleHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<JokeBean.Joke> jokeList;

    public SimpleAdapter(Context context) {
        this.context = context;
        jokeList = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
    }

    public void setItems(List<JokeBean.Joke> jokeList){
        this.jokeList.clear();
        this.jokeList.addAll(jokeList);
        notifyDataSetChanged();
    }

    public void loadMore(List<JokeBean.Joke> jokeList){
        int startPosition = this.jokeList.size();
        this.jokeList.addAll(jokeList);
        notifyItemRangeInserted(startPosition,jokeList.size());
    }

    @NonNull
    @Override
    public SimpleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SimpleHolder(inflater.inflate(R.layout.layout_simple_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleHolder simpleHolder, int i) {
        JokeBean.Joke joke = jokeList.get(i);
        simpleHolder.textView.setText(joke.getContent());
    }

    @Override
    public int getItemCount() {
        return jokeList == null ? 0 : jokeList.size();
    }

    class SimpleHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public SimpleHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_content);
        }
    }
}
