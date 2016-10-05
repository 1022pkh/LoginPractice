package com.pkh.loginpractice.main.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pkh.loginpractice.R;
import com.pkh.loginpractice.join.View.JoinActivity;
import com.pkh.loginpractice.main.model.Authentication;
import com.pkh.loginpractice.main.presenter.MainPresenter;
import com.pkh.loginpractice.main.presenter.MainPresenterImpl;
import com.pkh.loginpractice.mypage.MyPage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView{
    @BindView(R.id.inputId)
    EditText inputIdArea;
    @BindView(R.id.inputPwd)
    EditText inputPwdArea;
    @BindView(R.id.loginBtn)
    Button loginBtn;
    @BindView(R.id.joinBtn)
    Button joinBtn;

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        presenter = new MainPresenterImpl(this);
    }

    @OnClick(R.id.loginBtn)
    public void login(){
        Boolean idValid = false;
        Boolean pwdValid = false;

        idValid = idValidShowResult();
        pwdValid = pwdValidShowResult();

        if(idValid && pwdValid){
            //requeset login

            String userid =inputIdArea.getText().toString();
            String userpwd = inputPwdArea.getText().toString();
            Authentication authentication = new Authentication(userid,userpwd);

            presenter.requestLogin(authentication);
        }
        else
            Toast.makeText(getApplicationContext(),"ID 또는 패스워드를 확인해주세요.",Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.joinBtn)
    public void MoveJoinActivity() {
        Intent intent = new Intent(getApplicationContext(),JoinActivity.class);
        startActivity(intent);
    }

    public Boolean idValidShowResult(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(inputIdArea.getWindowToken(), 0);
        final String user_id = inputIdArea.getText().toString();

        if (TextUtils.isEmpty(user_id))
            inputIdArea.setError(getString(R.string.error_field_required));
        else {
            if (!isIdValid(user_id))
                inputIdArea.setError(getString(R.string.error_invalid_id));
            else
                return true;
        }
        return false;
    }

    public Boolean pwdValidShowResult(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(inputPwdArea.getWindowToken(), 0);

        final String user_pwd = inputPwdArea.getText().toString();

        if (TextUtils.isEmpty(user_pwd))
            inputPwdArea.setError(getString(R.string.error_field_required));
        else {
            if (!isPasswordValid())
                inputPwdArea.setError(getString(R.string.error_invalid_password));
            else
                return true;
        }
        return false;
    }

    private boolean isIdValid(String user_id) {
        return user_id.length() >= 4;
    }
    private boolean isPasswordValid() {
        return inputPwdArea.getText().toString().length() >= 6;
    }

    @Override
    public void successLogin(String result, String id, String nickname, String imgurl) {
        if(result.equals("success")){
            Toast.makeText(getApplicationContext(),"로그인 성공!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MyPage.class);
            intent.putExtra("user_id",id);
            intent.putExtra("nickname",nickname);
            intent.putExtra("imgurl",imgurl);
            startActivity(intent);
        }
        else
            Toast.makeText(getApplicationContext(),"로그인 실패!",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void storeCookie(String cookie) {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("cookie", cookie);
        editor.commit();


        Log.i("myTag","getCookie = " + cookie);
    }
}
