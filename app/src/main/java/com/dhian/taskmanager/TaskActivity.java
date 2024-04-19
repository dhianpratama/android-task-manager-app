package com.dhian.taskmanager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TaskActivity extends AppCompatActivity {

    private Button btnSaveTask, btnCancel;
    private EditText etTitle, etDescription;
    private EditText etDueDate;
    private TaskController taskController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etDueDate = findViewById(R.id.etDueDate);
        btnSaveTask = findViewById(R.id.btnSaveTask);
        btnCancel = findViewById(R.id.btnCancel);
        taskController = new TaskController(TaskActivity.this);

        etDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        // +1 because January is zero
                        final String selectedDate = year + "-" + twoDigits(month + 1) + "-" + twoDigits(day);
                        etDueDate.setText(selectedDate);
                    }
                });

                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etTitle.setError(null);
                etDescription.setError(null);
                String title = etTitle.getText().toString(),
                        description = etDescription.getText().toString();
                if ("".equals(title)) {
                    etTitle.setError("Enter title");
                    etTitle.requestFocus();
                    return;
                }
                if ("".equals(description)) {
                    etDescription.setError("Enter description");
                    etDescription.requestFocus();
                    return;
                }
                String dueDate = etDueDate.getText().toString();
                if ("".equals(dueDate)) {
                    etDueDate.setFocusable(true);
                    etDueDate.setError("Enter due date");
                    etDueDate.requestFocus();
                    return;
                }
                Task newTask = new Task(title, description, dueDate );
                long id = taskController.newTask(newTask);
                if (id == -1) {
                    Toast.makeText(TaskActivity.this, "Error saving. Try again", Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

}
