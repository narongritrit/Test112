package com.example.student.test1;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * Created by Student on 6/24/2017.
 */

public class PostDataToServer extends AsyncTask<String,Void,String>{
    //ตัวแปรที่ 1
    //ตัวแปรที่ 2 สภาวะการทำงานมี หมุนไหม
    //ตัวแปร 3 ถ้าทำงานสำเร็จ


    private Context context;

    public PostDataToServer(Context context) {
        this.context = context;
    }

    @Override
    //
    //ทำงานเบื้องหลัง ตัวแปรที่ 3
    //Thred Post พยายามโพสให้ได้
    protected String doInBackground(String... params) {
        try {
            OkHttpClient okHttpClient =new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("isAdd","true")
                    .add("name",params[0])
                    .add("user",params[1])
                    .add("password",params[2])
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(params[3]).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();

            return response.body().string();


        } catch (Exception e) {
            Log.d("SiamV1", "e doin ==>" + e.toString());
            return null;
        }

    }
}
