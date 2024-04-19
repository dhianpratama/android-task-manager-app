package com.dhian.taskmanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    private List<Task> taskList;
    Context context;
    private TaskController taskController;

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public TaskAdapter(List<Task> tasks, Context context) {
        this.taskList = tasks;
        this.context = context;
        taskController = new TaskController(this.context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemTask = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task, viewGroup, false);
        return new MyViewHolder(itemTask);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Task task = taskList.get(i);

        String title = task.getTitle();
        String description = task.getDescription();
        String dueDate = task.getDueDate();
        myViewHolder.title.setText(title);
        myViewHolder.description.setText(String.valueOf(description));
        myViewHolder.dueDate.setText("Due date " + dueDate);
        myViewHolder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task taskSelected = taskList.get(i);
                Intent intent = new Intent(context, EditTaskActivity.class);
                intent.putExtra("id", taskSelected.getId());
                intent.putExtra("title", taskSelected.getTitle());
                intent.putExtra("description", taskSelected.getDescription());
                intent.putExtra("dueDate", taskSelected.getDueDate());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        myViewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Task taskToDelete = taskList.get(i);
                taskList.remove(i);
                taskController.deleteTask(taskToDelete);
                updateView();

            }
        });

    }

    private void updateView() {
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, dueDate;
        ImageView ivEdit;
        ImageView ivDelete;

        MyViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.tvTitle);
            this.description = itemView.findViewById(R.id.tvDescription);
            this.dueDate = itemView.findViewById(R.id.tvDueDate);
            this.ivEdit = itemView.findViewById(R.id.ivEdit);
            this.ivDelete = itemView.findViewById(R.id.ivDelete);
        }
    }
}
