package com.example.checkapp.util;

import com.example.checkapp.data.ApplicationData;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;

public class ResponseData {
    String ip = ApplicationData.getIp();

    public String postResponse(String mapping, ArrayList<NameValuePair> postData) throws Exception{
        String url = "http://" + ip + ":8081/" + mapping;

        DefaultHttpClient http = new DefaultHttpClient();

        UrlEncodedFormEntity request = new UrlEncodedFormEntity(postData, "utf-8");
        HttpPost httpPost = new HttpPost(url);

        httpPost.setEntity(request);

        HttpResponse response = http.execute(httpPost);
        String body = EntityUtils.toString(response.getEntity());

        return body;
    }

//    public String getResponse(String mapping, ArrayList<NameValuePair> postData) throws Exception{
//        String url = "http://" + ip + ":8081/" + mapping;
//
//        DefaultHttpClient http = new DefaultHttpClient();
//
//        UrlEncodedFormEntity request = new UrlEncodedFormEntity(postData, "utf-8");
//        HttpGet httpGet = new HttpGet(url);
//
//        HttpResponse response = http.execute(httpGet);
//        String body = EntityUtils.toString(response.getEntity());
//
//        return body;
//    }
}
