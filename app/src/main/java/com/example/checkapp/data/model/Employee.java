package com.example.checkapp.data.model;

import java.util.Date;

public class Employee {
    private String emp_id;
    private String pass;
    private String emp_nm;
    private String rank;
    private String email;
    private String tel;
    private Date join_dt;
    private String depart_id;
    private String posi_id;
    private String emp_pic;
    private String sign;
    private String able;
    private String email_pass;

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmp_nm() {
        return emp_nm;
    }

    public void setEmp_nm(String emp_nm) {
        this.emp_nm = emp_nm;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Date getJoin_dt() {
        return join_dt;
    }

    public void setJoin_dt(Date join_dt) {
        this.join_dt = join_dt;
    }

    public String getDepart_id() {
        return depart_id;
    }

    public void setDepart_id(String depart_id) {
        this.depart_id = depart_id;
    }

    public String getPosi_id() {
        return posi_id;
    }

    public void setPosi_id(String posi_id) {
        this.posi_id = posi_id;
    }

    public String getEmp_pic() {
        return emp_pic;
    }

    public void setEmp_pic(String emp_pic) {
        this.emp_pic = emp_pic;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAble() {
        return able;
    }

    public void setAble(String able) {
        this.able = able;
    }

    public String getEmail_pass() {
        return email_pass;
    }

    public void setEmail_pass(String email_pass) {
        this.email_pass = email_pass;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "emp_id='" + emp_id + '\'' +
                ", pass='" + pass + '\'' +
                ", emp_nm='" + emp_nm + '\'' +
                ", rank='" + rank + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                ", join_dt=" + join_dt +
                ", depart_id='" + depart_id + '\'' +
                ", posi_id='" + posi_id + '\'' +
                ", emp_pic='" + emp_pic + '\'' +
                ", sign='" + sign + '\'' +
                ", able='" + able + '\'' +
                ", email_pass='" + email_pass + '\'' +
                '}';
    }
}
