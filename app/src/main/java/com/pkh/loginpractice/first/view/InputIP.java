package com.pkh.loginpractice.first.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pkh.loginpractice.R;
import com.pkh.loginpractice.application.ApplicationController;
import com.pkh.loginpractice.first.model.Result;
import com.pkh.loginpractice.main.view.MainActivity;
import com.pkh.loginpractice.service.NetworkService;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputIP extends AppCompatActivity {

    private EditText ipAddress;
    private EditText portNum;
    private Button connectBtn;

    String url = "";


    ApplicationController api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_ip);

        ipAddress = (EditText)findViewById(R.id.inputIPAddress);
        portNum = (EditText)findViewById(R.id.inputPortNum);
        connectBtn = (Button)findViewById(R.id.connectBtn);

        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String temp = ipAddress.getText().toString() + ":"+portNum.getText().toString();

                if(ipAddress.getText().toString().length() == 0 || portNum.getText().toString().length() == 0) {

                    Toast.makeText(getApplicationContext(),"입력하세요",Toast.LENGTH_SHORT).show();

                }
                else{
                    //IP주소 변경필요!
                    url = "http://" + temp;

                    api = ApplicationController.getInstance();
                    NetworkService service = api.getNetworkService();

                    Call<Result> call = service.connecting();
//                    Log.i("myTag","get");

                    call.enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {

                            if (response.isSuccessful()){
                                Gson gson = new Gson();
                                String jsonString = gson.toJson(response.body());
                                try{

                                    JSONObject jsonObject = new JSONObject(jsonString);

                                    String resultMsg = jsonObject.getString("result");
                                    if(resultMsg.equals("success")){
                                        Log.i("myTag","Go! upload test");
                                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                        intent.putExtra("url",url);
                                        startActivity(intent);
                                        finish();

                                        Toast.makeText(getApplicationContext(),url +" 연결 성공",Toast.LENGTH_SHORT).show();
                                    }

                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.i("MyTag", e.getMessage());
                                }

                            }
                            else{
                                Toast.makeText(getApplicationContext(),url +" 연결 실패 \n서버를 확인해주세요.",Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {
                            Log.i("myTag",t.toString());
                            Toast.makeText(getApplicationContext(),url +" 연결 실패 \n서버를 확인해주세요.",Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });
    }
}
