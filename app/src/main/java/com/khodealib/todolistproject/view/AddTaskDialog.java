package com.khodealib.todolistproject.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.khodealib.todolistproject.R;
import com.khodealib.todolistproject.model.Task;

public class AddTaskDialog extends DialogFragment {

    private AddNewTaskCallBack callBack;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        callBack = (AddNewTaskCallBack) context;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder( getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_task, null, false);

        final TextInputEditText titleEditText = view.findViewById(R.id.et_add_dialog_title);
        final TextInputLayout titleInputLayout = view.findViewById(R.id.etl_add_dialog_title);
        View btnSave = view.findViewById(R.id.btn_add_dialog_save);
        btnSave.setOnClickListener(v -> {
            if (titleEditText.length()>0){
                Task task =new Task();
                task.setTitle(titleEditText.getText().toString());
                task.setCompleted(false);
                callBack.onNewTask(task);
                dismiss();
            }else {
                titleInputLayout.setError("عنوان نباید خالی باشد");
            }
        });

        builder.setView(view);
        return builder.create();
    }


    public interface AddNewTaskCallBack{
        void onNewTask(Task task);
    }
}
