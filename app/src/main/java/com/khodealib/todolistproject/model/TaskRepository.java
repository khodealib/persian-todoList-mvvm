package com.khodealib.todolistproject.model;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskRepository {


    private TaskDao taskDao;

    public TaskRepository(TaskDao taskDao) {

        this.taskDao = taskDao;
    }

    public LiveData<List<Task>> getTasks() {
        return taskDao.getAll();
    }

    public LiveData<List<Task>> searchTask(String query) {
        return taskDao.search(query);
    }

    public long addTask(Task task) {
        return taskDao.add(task);
    }

    public int deleteTask(Task task) {
        return taskDao.delete(task);
    }

    public void deleteAllTask() {
        taskDao.deleteAll();
    }

    public int updateTask(Task task) {
        return taskDao.update(task);
    }
}
