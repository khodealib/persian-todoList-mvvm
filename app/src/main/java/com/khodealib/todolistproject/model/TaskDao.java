package com.khodealib.todolistproject.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    long add(Task task);

    @Delete
    int delete(Task task);

    @Update
    int update(Task task);

    @Query("SELECT * FROM tbl_tasks")
    LiveData<List<Task>> getAll();

    @Query("SELECT * FROM tbl_tasks WHERE title LIKE '%' || :query || '%'")
    LiveData<List<Task>> search(String query);

    @Query("DELETE FROM tbl_tasks")
    void deleteAll();
}
