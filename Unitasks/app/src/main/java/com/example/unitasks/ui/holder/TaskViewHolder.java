package com.example.unitasks.ui.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.unitasks.R;

public class TaskViewHolder extends RecyclerView.ViewHolder {
    private final TextView taskItemView;

    private TaskViewHolder(View itemView) {
        super(itemView);
        taskItemView = itemView.findViewById(R.id.textView19);
    }

    public void bind(String text) {
        taskItemView.setText(text);
    }

    public static TaskViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_item, parent, false);
        return new TaskViewHolder(view);
    }
}