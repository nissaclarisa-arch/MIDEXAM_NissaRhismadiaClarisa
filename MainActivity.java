
package com.example.nissarhismadiaclarisa_midexam20261;

import android.os.Bundle;
import android.database.Cursor;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etName, etStudentId;
    Spinner spMajor;
    Button btnAdd, btnView, btnEdit, btnDelete;
    TextView tvResult;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etStudentId = findViewById(R.id.etStudentId);
        spMajor = findViewById(R.id.spMajor);
        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        tvResult = findViewById(R.id.tvResult);

        dbHelper = new DatabaseHelper(this);

        btnAdd.setOnClickListener(v -> addStudent());
        btnView.setOnClickListener(v -> viewStudents());
        btnEdit.setOnClickListener(v -> editStudent());
        btnDelete.setOnClickListener(v -> deleteStudent());
    }

    private boolean validateInput() {
        String name = etName.getText().toString().trim();
        String id = etStudentId.getText().toString().trim();

        if (name.isEmpty() || id.isEmpty()) {
            Toast.makeText(this,
                    "Please enter name and student ID",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void addStudent() {
        if (!validateInput()) return;

        String name = etName.getText().toString().trim();
        String id = etStudentId.getText().toString().trim();
        String major = spMajor.getSelectedItem().toString();

        if (dbHelper.addStudent(id, name, major)) {
            Toast.makeText(this, "Student added successfully",
                    Toast.LENGTH_SHORT).show();
            clearFields();
            viewStudents();
        } else {
            Toast.makeText(this, "Student ID already exists",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void viewStudents() {
        Cursor cursor = dbHelper.getAllStudents();
        StringBuilder result = new StringBuilder();

        while (cursor.moveToNext()) {
            result.append("Name: ")
                    .append(cursor.getString(1)).append("\n")
                    .append("Student ID: ")
                    .append(cursor.getString(0)).append("\n")
                    .append("Major: ")
                    .append(cursor.getString(2)).append("\n\n");
        }
        cursor.close();

        tvResult.setText(result.length() == 0
                ? "No student data available."
                : result.toString());
    }

    private void editStudent() {
        if (!validateInput()) return;

        String id = etStudentId.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String major = spMajor.getSelectedItem().toString();

        if (dbHelper.updateStudent(id, name, major)) {
            Toast.makeText(this, "Student updated",
                    Toast.LENGTH_SHORT).show();
            viewStudents();
        } else {
            Toast.makeText(this, "Student ID not found",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteStudent() {
        String id = etStudentId.getText().toString().trim();

        if (id.isEmpty()) {
            Toast.makeText(this, "Enter Student ID first",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (dbHelper.deleteStudent(id)) {
            Toast.makeText(this, "Student deleted",
                    Toast.LENGTH_SHORT).show();
            clearFields();
            viewStudents();
        } else {
            Toast.makeText(this, "Student ID not found",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        etName.setText("");
        etStudentId.setText("");
        spMajor.setSelection(0);
    }
}
