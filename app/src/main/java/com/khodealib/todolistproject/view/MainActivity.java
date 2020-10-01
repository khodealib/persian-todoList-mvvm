package com.khodealib.todolistproject.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khodealib.todolistproject.model.AppDataBase;
import com.khodealib.todolistproject.R;
import com.khodealib.todolistproject.model.Task;
import com.khodealib.todolistproject.viewmodel.MainViewModel;
import com.khodealib.todolistproject.viewmodel.MainViewModelFactory;
import com.khodealib.todolistproject.model.TaskRepository;

public class MainActivity extends AppCompatActivity implements AddTaskDialog.AddNewTaskCallBack
        , TaskAdapter.TaskItemEventListener, EditTaskDialog.EditTaskCallBack {

    private TaskAdapter taskAdapter = new TaskAdapter(MainActivity.this);
    private MainViewModel mainViewModel;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel = new ViewModelProvider(this,new MainViewModelFactory(new TaskRepository(
                AppDataBase.getAppDataBase(getApplicationContext()).getTaskDao()
        ))).get(MainViewModel.class);




        EditText searchTaskEt = findViewById(R.id.et_main_searchTask);
        searchTaskEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() > 0) {
                    mainViewModel.searchTask(s.toString()).observe(MainActivity.this, tasks -> taskAdapter.setTasks(tasks));

                } else if (s.length() == 0) {
                    mainViewModel.getTasks().observe(MainActivity.this,tasks -> taskAdapter.setTasks(tasks));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        RecyclerView recyclerView = findViewById(R.id.rv_main_task);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(taskAdapter);

        mainViewModel.getTasks().observe(this,tasks -> {
            taskAdapter.setTasks(tasks);
        });


        View fabAddNewTask = findViewById(R.id.fab_main_addNewTask);

        fabAddNewTask.setOnClickListener(v -> {
            AddTaskDialog taskDialog = new AddTaskDialog();
            taskDialog.show(getSupportFragmentManager(), null);
        });


        ImageView clearAllTasksBtn = findViewById(R.id.iv_main_clearTasks);
        clearAllTasksBtn.setOnClickListener(v -> {
            mainViewModel.deleteAllTask();
            taskAdapter.clearAllItem();
        });
    }

    @Override
    public void onNewTask(Task task) {
        long taskId = mainViewModel.addTask(task);

        if (taskId != -1) {
            task.setId(taskId);
            taskAdapter.addItem(task);
        } else {
            Log.e(TAG, "onNewTask: item not inserted");
        }

    }

    @Override
    public void onDeleteButtonClick(Task task) {
        int result = mainViewModel.deleteTask(task);

        if (result > 0) {
            taskAdapter.deleteItem(task);
        }
    }

    @Override
    public void onItemLongPressed(Task task) {
        EditTaskDialog editTaskDialog = new EditTaskDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable("task", task);
        editTaskDialog.setArguments(bundle);
        editTaskDialog.show(getSupportFragmentManager(), null);
    }

    @Override
    public void onItemCheckedChange(Task task) {

        mainViewModel.updateTask(task);
    }

    @Override
    public void onEditTask(Task task) {
        int result = mainViewModel.updateTask(task);

        if (result > 0) {
            taskAdapter.updateItem(task);
        }
    }
}
