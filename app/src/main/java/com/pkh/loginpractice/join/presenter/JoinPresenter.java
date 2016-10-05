package com.pkh.loginpractice.join.presenter;

/**
 * Created by kh on 2016. 9. 21..
 */
public interface JoinPresenter {
    void checkIdDuplication(String user_id);
    void checkNameDuplication(String name);
}
