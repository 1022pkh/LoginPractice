package com.pkh.loginpractice.join.presenter;

import android.util.Log;

import com.pkh.loginpractice.application.ApplicationController;
import com.pkh.loginpractice.join.View.JoinView;
import com.pkh.loginpractice.service.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kh on 2016. 9. 21..
 */
public class JoinPresenterImpl implements JoinPresenter{

    JoinView view;
    NetworkService networkService;

    public JoinPresenterImpl(JoinView view) {
        this.view = view;
        networkService = ApplicationController.getInstance().getNetworkService();
    }

    @Override
    public void checkIdDuplication(String user_id) {

        Call<String> duplicationCall = networkService.duplicationIdTest(user_id);
        duplicationCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    view.isIdDuplicated(response.body());
                } else {
                    Log.i("MyTag", "중복 확인 테스트 실패 코드 : " + response.errorBody());
                }

            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }

        });
    }

    @Override
    public void checkNameDuplication(String name) {
        Call<String> duplicationCall = networkService.duplicationNickTest(name);
        duplicationCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    view.isNameDuplicated(response.body());
                } else {
                    Log.i("MyTag", "중복 확인 테스트 실패 코드 : " + response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
