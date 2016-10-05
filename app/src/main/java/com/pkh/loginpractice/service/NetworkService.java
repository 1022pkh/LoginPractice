package com.pkh.loginpractice.service;

import com.pkh.loginpractice.first.model.Result;
import com.pkh.loginpractice.main.model.Authentication;
import com.pkh.loginpractice.main.model.LoginResult;
import com.pkh.loginpractice.mypage.Model.Key;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by kh on 2016. 8. 25..
 */
public interface NetworkService {

    //연결확인
    @GET("/connect")
    Call<Result> connecting();

    //로그인
    @POST("/sign/in")
    Call<LoginResult> login(@Body Authentication authentication);

    //세션체크
    @POST("/sign/get")
    Call<String> checkSession(@Body Key key);


    //id 중복체크
    @GET("/membership/{user_id}")
    Call<String> duplicationIdTest(@Path("user_id") String user_id);

    //넥네임 중복체크
    @GET("/membership/nickname/{nick}")
    Call<String> duplicationNickTest(@Path("nick") String nick);

    //회원가입
    @Multipart
    @POST("/membership")
    Call<ResponseBody> registerUser(@Part MultipartBody.Part file, @Part("id") RequestBody userid,
                                    @Part("name") RequestBody nickname, @Part("pwd") RequestBody userpwd,
                                    @Part("email") RequestBody email);

}
