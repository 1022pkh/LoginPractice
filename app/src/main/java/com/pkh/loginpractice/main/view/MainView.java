package com.pkh.loginpractice.main.view;

/**
 * Created by kh on 2016. 9. 22..
 */
public interface MainView {
    void successLogin(String result,String id,String nickname, String imgurl);
    void storeCookie(String cookie);
}
