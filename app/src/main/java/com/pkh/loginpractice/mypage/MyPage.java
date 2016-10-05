package com.pkh.loginpractice.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.pkh.loginpractice.R;
import com.pkh.loginpractice.mypage.presenter.MyPagePresenter;
import com.pkh.loginpractice.mypage.presenter.MyPagePresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyPage extends AppCompatActivity implements MyPageView{

    @BindView(R.id.get_userid_textview)
    TextView useridTextview;
    @BindView(R.id.get_nickname_textview)
    TextView nickTextview;
    @BindView(R.id.get_imgurl_textview)
    TextView imgurlTextview;
    @BindView(R.id.logoutBtn)
    Button logoutBtn;
    @BindView(R.id.getsessionBtn)
    Button getSessionBtn;

    MyPagePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        ButterKnife.bind(this);

        presenter = new MyPagePresenterImpl(this);

        Intent intent = getIntent();

        useridTextview.setText(intent.getExtras().get("user_id").toString());
        nickTextview.setText(intent.getExtras().get("nickname").toString());
        imgurlTextview.setText(intent.getExtras().get("imgurl").toString());
    }

    @OnClick(R.id.logoutBtn)
    public void logout(){

    }

    @OnClick(R.id.getsessionBtn)
    public void getSession(){
        presenter.getSession();
    }

//    @Override
//    public String getCookie() {
//
//        //저장한 값 가져오기
//        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
//        String cookie = pref.getString("cookie", "");
//
//        return cookie;
//    }
}
