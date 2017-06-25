package com.example.student.test1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private EditText userEditText,passwordEditText;
    private TextView textView;
    private Button button;
    private String userString, passwordString;
    private MyAlert myAlert;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initail View
        initialView();
        textviewController();
        //TextView Controlle
        //BUTTON Controlle
        buttonController();
    }

    private void buttonController() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userString = userEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString();
                //check Space
                if (userString.length() == 0 || passwordString.length() == 0) {
                    //have space
                    myAlert.myDialog(getResources().getString(R.string.titleHaveSpace),
                            getResources().getString(R.string.messageHaveSpace));
                } else {
                    //No Space

                    checkUserAndPass();
                }


            }
        });
    }

    private void checkUserAndPass() {
        try{
            String urlPHP = "http://androidthai.in.th/siam/getAllDatarit.php";
            GetAllData getAllData = new GetAllData(MainActivity.this);
            getAllData.execute(urlPHP);
            String strJSON = getAllData.get();
            Log.d("SiamV1","JSON ==>" + strJSON);

            JSONArray jsonArray = new JSONArray(strJSON);
            boolean b= true;
            String[] strings = new String[]{"id","name","User","Password"};
            String[] loginStrings1 = new String[strings.length];

            for (int i=0;i<jsonArray.length(); i+=1){
                JSONObject jsonObject =jsonArray.getJSONObject(i);//ตัวชีี้เป้า
                if(userString.equals(jsonObject.getString("User"))){

                    b = false;
                    for (int i1=0;i1<strings.length;i1++){
                        loginStrings1[i1] = jsonObject.getString((strings[i1]));
                        Log.d("SiamV1", "loginString["+ i1 + "] ===>" + loginStrings1[i1]);
                    }
                }

            }
            if(b){
                myAlert.myDialog(getResources().getString(R.string.titleUserFalse),
                        getResources().getString(R.string.messageUserFalse));
            } else if (passwordString.equals(loginStrings1[3])) {
                Toast.makeText(MainActivity.this,"Welcome"+loginStrings1[1],
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this,MapsActivity.class);
                intent.putExtra("Login",loginStrings1);
                startActivity(intent);
                finish();


            } else {
                myAlert.myDialog(getResources().getString(R.string.titlePasswordFalse),
                        getResources().getString(R.string.messageUserFalse));
            }

        }catch (Exception e){
            e.printStackTrace();

        }
    }

    private void textviewController() {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent to NewRegisterActivity
                Intent intent = new Intent(MainActivity.this,NewRegisterActivity.class);
                startActivity(intent);//เปิดหน้าสองทับหน้าแรก

            }
        });
    }

    private void initialView() {
        userEditText = (EditText) findViewById(R.id.edtUser);
        passwordEditText = (EditText) findViewById(R.id.edtPassword);
        textView = (TextView) findViewById(R.id.txtNewRegister);
        button = (Button) findViewById(R.id.btnLogin);

        myAlert = new MyAlert(MainActivity.this);

    }
}
