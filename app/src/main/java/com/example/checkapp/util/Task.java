package com.example.checkapp.util;

import android.os.AsyncTask;

import com.example.checkapp.data.model.Employee;
import com.example.checkapp.data.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Task extends AsyncTask<Map<String, String>, Integer, String> {

//    public static String ip = "192.168.0.4"; // 자신의 IP주소를 쓰시면 됩니다.(집)
    public static String ip = "192.168.0.132"; // 자신의 IP주소를 쓰시면 됩니다.(학원)
    public Employee employee;


    @Override
    protected String doInBackground(Map<String, String>... maps) { // 내가 전송하고 싶은 파라미터

        // Http 요청 준비 작업

        HttpClient.Builder http = new HttpClient.Builder
                ("POST", "http://" + ip + ":8081/login"); //포트번호,서블릿주소

        // Parameter 를 전송한다.
        http.addAllParameters(maps[0]);

        //Http 요청 전송
        HttpClient post = http.create();
        post.request();

        // 응답 상태코드 가져오기
        int statusCode = post.getHttpStatusCode();

        // 응답 본문 가져오기
        String body = post.getBody();

        return body;
    }

    @Override
    protected void onPostExecute(String s){ //서블릿으로부터 값을 받을 함수

        Gson gson = new Gson();
//        Map<String, User> data = new HashMap<String, User>();
//        data = gson.fromJson(s, data.getClass());


        try {
            JSONObject jsonObject = new JSONObject(s);
            String user = jsonObject.getString("user");
//            JSONArray arr = new JSONArray(user);
            jsonObject = new JSONObject(user);
            System.out.println(jsonObject);
            System.out.println(user);
            System.out.println("userName : " + jsonObject.getString("emp_nm"));

            employee = gson.fromJson(user, Employee.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        System.out.println(data.get("user"));
//
//        employee = gson.fromJson(data.get("user").toString(), Employee.class);
////
//        System.out.println("아이디 : "+employee.getEmp_id());
//        System.out.println("비번 : "+employee.getPass());
    }

    public Employee getUserInfo() {
        return this.employee;
    }
}