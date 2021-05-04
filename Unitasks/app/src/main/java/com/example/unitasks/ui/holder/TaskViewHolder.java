package com.example.unitasks.ui.holder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.unitasks.MainActivity;
import com.example.unitasks.R;
import com.example.unitasks.TaskActivity;

public class TaskViewHolder extends RecyclerView.ViewHolder {
    private final TextView taskItemView;
    private final Context context;

    private TaskViewHolder(View itemView) {
        super(itemView);
        taskItemView = itemView.findViewById(R.id.textView19);
        context = itemView.getContext();

        taskItemView.setOnClickListener(v -> {
            Intent myIntent = new Intent(context, TaskActivity.class);
            myIntent.putExtra("task", taskItemView.getText());
            context.startActivity(myIntent);
        });
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