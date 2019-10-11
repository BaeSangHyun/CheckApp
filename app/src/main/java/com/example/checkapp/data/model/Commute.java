package com.example.checkapp.data.model;

public class Commute {
    private String emp_id;
    private String dt;
    private String gtw_tm;
    private String ow_tm;
    private String res;
    private String reas;

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getGtw_tm() {
        return gtw_tm;
    }

    public void setGtw_tm(String gtw_tm) {
        this.gtw_tm = gtw_tm;
    }

    public String getOw_tm() {
        return ow_tm;
    }

    public void setOw_tm(String ow_tm) {
        this.ow_tm = ow_tm;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getReas() {
        return reas;
    }

    public void setReas(String reas) {
        this.reas = reas;
    }

    @Override
    public String toString() {
        return "Commute{" +
                "emp_id='" + emp_id + '\'' +
                ", dt='" + dt + '\'' +
                ", gtw_tm='" + gtw_tm + '\'' +
                ", ow_tm='" + ow_tm + '\'' +
                ", res='" + res + '\'' +
                ", reas='" + reas + '\'' +
                '}';
    }
}
