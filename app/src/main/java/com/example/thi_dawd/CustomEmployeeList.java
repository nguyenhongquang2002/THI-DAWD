package com.example.thi_dawd;


import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomEmployeeList extends BaseAdapter {
    private Activity context;
    List<Employee> employees;
    SQLiteDatabaseHandler db;

    public CustomEmployeeList(Activity context, List<Employee> employees, SQLiteDatabaseHandler db) {
        this.context = context;
        this.employees= employees;
        this.db=db;
    }

    //    public static class ViewHolder
//    {
//        TextView textViewId;
//        TextView textViewName;
//        TextView textViewDesignation;
//        TextView textViewSalary;
//        Button saveButton;
//        Button editButton;
//        Button deleteButton;
//    }
    @Override
    public View getView(int position, View convertView,
                        ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(R.layout.row_item, parent, false);
        Employee employee = employees.get(position);
        TextView tvItemName = convertView.findViewById(R.id.textViewName);
        TextView tvItemDesignation = convertView.findViewById(R.id.textViewDesignation);
        TextView tvItemSalary = convertView.findViewById(R.id.textViewSalary);
        tvItemName.setText(employee.getName());
        tvItemDesignation.setText(employee.getDesignation());
        tvItemSalary.setText(String.valueOf(employee.getSalary()));
        return convertView;
    }

    public long getItemId(int position) {
        return employees.get(position).getId();
    }

    public Object getItem(int position) {
        return employees.get(position);
    }

    public int getCount() {
        return employees.size();
    }

    public void updateData(List<Employee> employeeList) {
        this.employees = employeeList;
        this.notifyDataSetChanged();
    }
}