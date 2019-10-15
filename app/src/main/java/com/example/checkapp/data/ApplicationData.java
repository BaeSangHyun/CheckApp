package com.example.checkapp.data;

import android.app.Application;

import com.example.checkapp.data.model.Employee;

public class ApplicationData extends Application {
    public static Employee employee;
    public final static String ip = "3.132.124.81";

    public static Employee getEmployee() {
        return employee;
    }

    public static void setEmployee(Employee employee) {
        ApplicationData.employee = employee;
    }

    public static String getIp() {
        return ip;
    }
}
