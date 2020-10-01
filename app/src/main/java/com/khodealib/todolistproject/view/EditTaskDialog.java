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

public class EditTaskDialog extends DialogFragment {

    private EditTaskCallBack callBack;
    private Task task;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        callBack = (EditTaskCallBack) context;
        this.task = getArguments().getParcelable("task");
        if (task==null) {
            dismiss();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_task, null, false);

        final TextInputEditText titleEditText = view.findViewById(R.id.et_edit_dialog_title);
        titleEditText.setText(task.getTitle());
        final TextInputLayout titleInputLayout = view.findViewById(R.id.etl_edit_dialog_title);
        View btnSave = view.findViewById(R.id.btn_edit_dialog_save);
        btnSave.setOnClickListener(v -> {
            if (titleEditText.length()>0){
                task.setTitle(titleEditText.getText().toString());
                task.setCompleted(false);
                callBack.onEditTask(task);
                dismiss();
            }else {
                titleInputLayout.setError("عنوان نباید خالی باشد");
            }
        });

        builder.setView(view);
        return builder.create();
    }


    public interface EditTaskCallBack{
        void onEditTask(Task task);
    }
}
