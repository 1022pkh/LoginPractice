package com.pkh.loginpractice.main.model;

public class Authentication {

    public String user_id;
    public String user_pwd;

    public Authentication(String user_id, String user_pwd) {
        this.user_id = user_id;
        this.user_pwd = user_pwd;
    }
}
