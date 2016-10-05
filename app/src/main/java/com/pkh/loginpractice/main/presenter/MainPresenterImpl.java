package com.pkh.loginpractice.main.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.pkh.loginpractice.application.ApplicationController;
import com.pkh.loginpractice.main.model.Authentication;
import com.pkh.loginpractice.main.model.LoginResult;
import com.pkh.loginpractice.main.view.MainView;
import com.pkh.loginpractice.service.NetworkService;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kh on 2016. 9. 22..
 */
public class MainPresenterImpl implements MainPresenter {

    MainView view;
    NetworkService networkService;

    public MainPresenterImpl(MainView view) {
        this.view = view;
        networkService = ApplicationController.getInstance().getNetworkService();
    }


    @Override
    public void requestLogin(Authentication auth) {
        Call<LoginResult> loginCall = networkService.login(auth);
        loginCall.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {

                Log.i("myTag","Header " + response.headers() );

                ApplicationController.sessionKey = response.headers().get("makeKH");
                Log.i("myTag","makeKH = " + ApplicationController.sessionKey);

//                //자동로그인 설정되어있으면...저장해둔다
//                view.storeCookie(ApplicationController.sessionKey);

                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String jsonString = gson.toJson(response.body());

//                    Log.i("myTag",jsonString);
                    try {
                        JSONObject jsonObject = new JSONObject(jsonString);

                        String result = jsonObject.getString("result");
                        String id = jsonObject.getString("user_id");
                        String nickname = jsonObject.getString("nickname");
                        String imgurl = jsonObject.getString("imgurl");
//                        Log.i("myTag",id);

                        view.successLogin(result,id,nickname,imgurl);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.i("MyTag", "중복 확인 테스트 실패 코드 : " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }

    @Override
    public void requestLogout(Authentication auth) {

    }
}
