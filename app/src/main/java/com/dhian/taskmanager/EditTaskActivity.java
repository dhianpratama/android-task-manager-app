package com.dhian.taskmanager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditTaskActivity extends AppCompatActivity {
    private EditText etEditTitle, etEditDescription;
    private EditText etEditDueDate;
    private Button btnSaveChanges, btnCancelChanges;
    private Task task;
    private TaskController taskController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
            return;
        }
        taskController = new TaskController(EditTaskActivity.this);

        long id = extras.getLong("id");
        String title = extras.getString("title");
        String description = extras.getString("description");
        String dueDate = extras.getString("dueDate");
        task = new Task(title, description, id, dueDate);


        etEditDescription = findViewById(R.id.etEditDescription);
        etEditTitle = findViewById(R.id.etEditTitle);
        etEditDueDate = findViewById(R.id.etDueDate);
        btnCancelChanges = findViewById(R.id.btnCancelEdit);
        btnSaveChanges = findViewById(R.id.btnSaveEditTask);


        etEditDescription.setText(String.valueOf(task.getDescription()));
        etEditTitle.setText(task.getTitle());
        etEditDueDate.setText(task.getDueDate());

        etEditDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        // +1 because January is zero
                        final String selectedDate = year + "-" + twoDigits(month + 1) + "-" + twoDigits(day);
                        etEditDueDate.setText(selectedDate);
                    }
                });

                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        btnCancelChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etEditTitle.setError(null);
                etEditDescription.setError(null);
                String newTitle = etEditTitle.getText().toString();
                String newDescription = etEditDescription.getText().toString();
                if (newTitle.isEmpty()) {
                    etEditTitle.setError("Enter title");
                    etEditTitle.requestFocus();
                    return;
                }
                if (newDescription.isEmpty()) {
                    etEditDescription.setError("Enter description");
                    etEditDescription.requestFocus();
                    return;
                }

                String dueDate = etEditDueDate.getText().toString();

                Task taskWithNewChanges = new Task(newTitle, newDescription, task.getId(), dueDate);
                int rowsUpdate = taskController.saveChanges(taskWithNewChanges);
                if (rowsUpdate != 1) {
                    Toast.makeText(EditTaskActivity.this, "Error saving changes. Try again.", Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                }
            }
        });
    }

    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }
}
