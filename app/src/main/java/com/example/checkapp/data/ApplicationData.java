package com.example.checkapp.data;

import android.app.Application;

import com.example.checkapp.data.model.Employee;

public class ApplicationData extends Application {
    public static Employee employee;
    public final static String ip = "192.168.0.132";

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
