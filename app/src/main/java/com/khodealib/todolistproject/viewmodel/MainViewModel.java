package com.khodealib.todolistproject.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.khodealib.todolistproject.model.Task;
import com.khodealib.todolistproject.model.TaskRepository;

import java.util.List;

public class MainViewModel extends ViewModel {

    private final TaskRepository taskRepository;
    private MutableLiveData<List<Task>> tasksList=new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();


    public MainViewModel(TaskRepository taskRepository) {

        this.taskRepository = taskRepository;
    }

    public LiveData<List<Task>> getTasks() {
        return taskRepository.getTasks();
    }

    public int deleteTask(Task task) {
        return taskRepository.deleteTask(task);
    }

    public void deleteAllTask() {
        taskRepository.deleteAllTask();
    }

    public long addTask(Task task) {
        return taskRepository.addTask(task);
    }

    public int updateTask(Task task) {
        return taskRepository.updateTask(task);
    }

    public LiveData<List<Task>> searchTask(String query) {
        return taskRepository.searchTask(query);
    }

}
