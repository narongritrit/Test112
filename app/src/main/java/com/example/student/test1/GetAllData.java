package com.example.student.test1;

import android.content.Context;
import android.os.AsyncTask;
import android.speech.tts.Voice;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * Created by Student on 6/24/2017.
 */

public class GetAllData extends AsyncTask<String,Void,String> {

    private Context context;//ท่อส่งข้อมูล

    public GetAllData(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(params[0]).build();
            Response response = okHttpClient.newCall(request).execute();
            return  response.body().string();


        }catch (Exception e){
            Log.d("SiamV1","e doIn  ==>" + e.toString());
            return null;
        }

    }
}  //main class

