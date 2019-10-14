package com.example.checkapp.data;

import com.example.checkapp.data.model.Commute;

import java.util.List;

public class CalendarData {
    public static List<Commute> commuteList;
    public static String date;

    public static List<Commute> getCommuteList() {
        return commuteList;
    }

    public static void setCommuteList(List<Commute> commuteList) {
        CalendarData.commuteList = commuteList;
    }
}
