package com.example.thi_dawd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Employee> employees;
    SQLiteDatabaseHandler db;
    Button btnSave;
    Button btnUpdate;
    Button btnDelete;
    private EditText editName;
    private EditText editDesignation;
    private EditText editSalary;
    CustomEmployeeList customEmployeeList;
    Employee employeeId;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new SQLiteDatabaseHandler(this);
        listView = (ListView) findViewById(R.id.list);
        btnSave = (Button) findViewById(R.id.add);
        btnUpdate = (Button) findViewById(R.id.update);
        btnDelete = (Button) findViewById(R.id.delete);
        editName = (EditText) findViewById(R.id.name);
        editDesignation = (EditText) findViewById(R.id.designation);
        editSalary = (EditText) findViewById(R.id.salary);
        employees = db.getAll();
        customEmployeeList = new CustomEmployeeList(this, (ArrayList) employees, db);
        listView.setAdapter(customEmployeeList);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString();
                String designation = editDesignation.getText().toString();
                Double salary = Double. parseDouble(editSalary.getText().toString());
                Employee employee = new Employee(name, designation, salary);
                db.add(employee);
                employees.add(employee);
                customEmployeeList.notifyDataSetChanged();
                resetForm();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                String designation = editDesignation.getText().toString();
                Double salary = Double.parseDouble(editSalary.getText().toString());
                Employee employee = new Employee(employeeId.getId(), name, designation, salary);
                db.update(employee);
                employees = db.getAll();
                customEmployeeList.updateData(employees);
                resetForm();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.delete(employeeId);
                employees.remove(employeeId);
                customEmployeeList.notifyDataSetChanged();
                resetForm();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Employee employee = employees.get(position);
                editName.setText(employee.getName());
                editDesignation.setText(employee.getDesignation());
                editSalary.setText(String.valueOf(employee.getSalary()));
                employee.setId(id);
                employeeId = employee;
                btnUpdate.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.VISIBLE);
                btnSave.setVisibility(View.GONE);
            }
        });
    }

    public boolean isValidForm() {
        String name = editName.getText().toString();
        String designation = editDesignation.getText().toString();
        Double salary = editSalary.getText().toString().length() > 0 ? Double.parseDouble(editSalary.getText().toString()) : 0;
        boolean isValid = true;
        if (name.length() == 0) {
            isValid = false;
        }
        if (designation.length() == 0) {
            isValid = false;
        }
        if (salary == 0) {
            isValid = false;
        }
        return isValid;
    }

    public void resetForm() {
        editName.setText("");
        editDesignation.setText("");
        editSalary.setText("");
    }
}