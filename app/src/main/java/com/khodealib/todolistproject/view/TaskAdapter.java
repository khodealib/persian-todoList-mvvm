package com.khodealib.todolistproject.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khodealib.todolistproject.R;
import com.khodealib.todolistproject.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TasKViewHolder> {

    private List<Task> tasks = new ArrayList<>();
    private TaskItemEventListener listener;

    public TaskAdapter(TaskItemEventListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public TasKViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TasKViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TasKViewHolder holder, int position) {
        holder.bindTask(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void addItem(Task task) {
        tasks.add(0,task);

        notifyItemInserted(0);
    }

    public void updateItem(Task task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId()==task.getId()) {
                tasks.set(i , task);
                notifyItemChanged(i);
                break;
            }
        }
    }

    public void addItems(List<Task> tasks) {

        this.tasks.addAll(tasks);
        notifyDataSetChanged();
    }

    public void deleteItem(Task task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId()==task.getId()) {
                tasks.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }

    public void clearAllItem() {
        this.tasks.clear();
        notifyDataSetChanged();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public class TasKViewHolder extends RecyclerView.ViewHolder{
        // define task view item
        private CheckBox titleAndCompeleted;
        private ImageView deleteTaskBtn;

        public TasKViewHolder(@NonNull View itemView) {
            super(itemView);
            titleAndCompeleted = itemView.findViewById(R.id.cb_task_title);
            deleteTaskBtn = itemView.findViewById(R.id.btn_task_delete);
        }

        public void bindTask(final Task task) {
            titleAndCompeleted.setOnCheckedChangeListener(null);
            titleAndCompeleted.setChecked(task.isCompleted());
            titleAndCompeleted.setText(task.getTitle());

            deleteTaskBtn.setOnClickListener(v -> listener.onDeleteButtonClick(task));

            itemView.setOnLongClickListener(v -> {
                listener.onItemLongPressed(task);
                return false;
            });

            titleAndCompeleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
                task.setCompleted(isChecked);
                listener.onItemCheckedChange(task);
            });
        }
    }

    public interface TaskItemEventListener{
        void onDeleteButtonClick(Task task);
        void onItemLongPressed(Task task);
        void onItemCheckedChange(Task task);
    }
}
