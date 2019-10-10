package com.example.checkapp.data.model;

import java.util.Date;

public class User {

    private String userId;
    private String pass;
    private String userNm;
    private String alias;
    private String addr1;
    private String addr2;
    private String zipCode;
    private String fileName;
    private String realFileName;       // 물리파일명
    private String realFileName2;

    private Date reg_dt;

    private int age;

    public User() {
    }

    public User(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Date getReg_dt() {
        return reg_dt;
    }

    public void setReg_dt(Date reg_dt) {
        this.reg_dt = reg_dt;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRealFileName() {
        return realFileName;
    }

    public void setRealFileName(String realFileName) {
        this.realFileName = realFileName;
    }

    public String getRealFileName2() {
        return realFileName2;
    }

    public void setRealFileName2(String realFileName2) {
        this.realFileName2 = realFileName2;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", pass='" + pass + '\'' +
                ", userNm='" + userNm + '\'' +
                ", alias='" + alias + '\'' +
                ", addr1='" + addr1 + '\'' +
                ", addr2='" + addr2 + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", fileName='" + fileName + '\'' +
                ", realFileName='" + realFileName + '\'' +
                ", realFileName2='" + realFileName2 + '\'' +
                ", reg_dt=" + reg_dt +
                ", age=" + age +
                '}';
    }
}
