package com.pkh.loginpractice.application;

import android.app.Application;

import com.pkh.loginpractice.service.NetworkService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApplicationController extends Application {

    private static ApplicationController instance;
    private static String baseUrl = "http://192.168.123.104:3000";
    private NetworkService networkService;
    public static String sessionKey;

    public static ApplicationController getInstance() {
        return instance;
    }

    public NetworkService getNetworkService() {
        return networkService;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationController.instance = this;
        this.buildService();
    }

    public void buildService() {


        Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
                .build();

        networkService = retrofit.create(NetworkService.class);
    }
}
