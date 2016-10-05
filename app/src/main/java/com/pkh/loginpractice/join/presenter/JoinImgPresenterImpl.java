package com.pkh.loginpractice.join.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.pkh.loginpractice.application.ApplicationController;
import com.pkh.loginpractice.join.View.JoinImgView;
import com.pkh.loginpractice.join.model.RegisterResult;
import com.pkh.loginpractice.service.NetworkService;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kh on 2016. 9. 21..
 */
public class JoinImgPresenterImpl implements JoinImgPresenter  {

    JoinImgView view;
    NetworkService networkService;

    public JoinImgPresenterImpl(JoinImgView view) {
        this.view = view;
        networkService = ApplicationController.getInstance().getNetworkService();
    }


    @Override
    public void registerToServer(String id, String nickname, String pwd, String email, String ImgURL) {

        /**
         * 서버로 보낼 파일의 전체 url을 이용해 작업
         */

        File photo = new File(ImgURL);
        RequestBody photoBody = RequestBody.create(MediaType.parse("image/jpg"), photo);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData("picture", photo.getName(), photoBody);

        //Log.i("myTag","this file'name is "+ photo.getName());

        /**
         * 서버에 사진이외의 텍스트를 보낼 경우를 생각해서 일단 넣어둠
         */
        // add another part within the multipart request
//        String userid = id;
//        String userNick = nickname;
//        String userPwd = pwd;
//        String userEmail = email;

        RequestBody userid = RequestBody.create(MediaType.parse("multipart/form-data"), id);
        RequestBody userNick = RequestBody.create(MediaType.parse("multipart/form-data"), nickname);
        RequestBody userPwd = RequestBody.create(MediaType.parse("multipart/form-data"), pwd);
        RequestBody userEmail = RequestBody.create(MediaType.parse("multipart/form-data"), email);


        /**
         * 사진 업로드하는 부분 // POST방식 이용
         */

        Call<ResponseBody> call = networkService.registerUser(body, userid, userNick,userPwd,userEmail);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Gson gson = new Gson();
                if (response.isSuccessful()) {
                    try {
                        String getResult = response.body().string();

                        JsonParser parser = new JsonParser();
                        JsonElement rootObejct = parser.parse(getResult);

//                        Log.i("myTag",rootObejct.toString());

                        RegisterResult example = gson.fromJson(rootObejct, RegisterResult.class);

                        String result = example.result;

                        Log.i("myTag","get response value = "+result);

                        view.completeRegister(result);

                        Log.i("myTag","end");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.i("MyTag", "중복 확인 테스트 실패 코드 : " + response.errorBody());
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());

            }
        });
    }
}
