package com.pkh.loginpractice.main.presenter;

import com.pkh.loginpractice.main.model.Authentication;

/**
 * Created by kh on 2016. 9. 22..
 */
public interface MainPresenter {
    void requestLogin(Authentication auth);
    void requestLogout(Authentication auth);
}
