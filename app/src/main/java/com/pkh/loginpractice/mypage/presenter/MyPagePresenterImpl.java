package com.pkh.loginpractice.mypage.presenter;

import android.util.Log;

import com.pkh.loginpractice.application.ApplicationController;
import com.pkh.loginpractice.mypage.Model.Key;
import com.pkh.loginpractice.mypage.MyPage;
import com.pkh.loginpractice.service.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kh on 2016. 9. 23..
 */
public class MyPagePresenterImpl implements MyPagePresenter{

    MyPage view;
    NetworkService networkService;

    public MyPagePresenterImpl(MyPage view) {
        this.view = view;
        networkService = ApplicationController.getInstance().getNetworkService();
    }

    @Override
    public void getSession() {
        Log.i("myTag","get Session");

        String sessionKey = ApplicationController.sessionKey;

        Key loginkey = new Key(sessionKey);

        Call<String> sessionCheck =  networkService.checkSession(loginkey);
        sessionCheck.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.i("myTag","in Session");

                if(response.isSuccessful()){
                    Log.i("myTag",response.body());
                }
                else{
                    Log.i("MyTag", "중복 확인 테스트 실패 코드 : " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("myTag Session error:", t.getMessage());
            }
        });
    }

    @Override
    public void requestLogout() {

    }
}
