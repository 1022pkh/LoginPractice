package com.pkh.loginpractice.join.View;

import android.net.Uri;

/**
 * Created by kh on 2016. 9. 21..
 */
public interface JoinImgView {
    String getImageNameToUri(Uri data);
    void completeRegister(String result);
}
