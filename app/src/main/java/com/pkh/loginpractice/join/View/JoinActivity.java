package com.pkh.loginpractice.join.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pkh.loginpractice.R;
import com.pkh.loginpractice.join.presenter.JoinPresenter;
import com.pkh.loginpractice.join.presenter.JoinPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JoinActivity extends AppCompatActivity implements JoinView{

    @BindView(R.id.join_edit_id)
    EditText inputId;
    @BindView(R.id.join_edit_name)
    EditText inputNickName;
    @BindView(R.id.join_edit_email)
    EditText inputEmail;
    @BindView(R.id.join_edit_passwd)
    EditText inputPwd;
    @BindView(R.id.join_edit_passwd_conf)
    EditText inputPwd_Conf;

    @BindView(R.id.join_btn_idDupl)
    Button idDuplBtn;
    @BindView(R.id.join_btn_nameDupl)
    Button nickNameDuplBtn;

    @BindView(R.id.join_btn_next)
    Button competeBtn;



    private Boolean nameValid = false;
    private Boolean pwValid = false;
    private Boolean emailValid = false;
    private Boolean idValid = false;


    private JoinPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        ButterKnife.bind(this);

        presenter = new JoinPresenterImpl(this);
    }


    @OnClick(R.id.join_btn_idDupl)
    public void setBtnIdDuplication() {
//        Log.i("mytag","test");

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(inputId.getWindowToken(), 0);
        final String user_id = inputId.getText().toString();


        if (TextUtils.isEmpty(user_id)) inputId.setError(getString(R.string.error_field_required));
        else {
            if (!isIdValid(user_id))
                inputId.setError(getString(R.string.error_invalid_id));
            else presenter.checkIdDuplication(user_id);
        }
    }

    @OnClick(R.id.join_btn_nameDupl)
    public void setBtnNameDuplication(){

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(inputNickName.getWindowToken(), 0);
        final String name = inputNickName.getText().toString();

        if (TextUtils.isEmpty(name))
            inputNickName.setError(getString(R.string.error_field_required));
        else {
            if (!isNameValid(name))
                inputNickName.setError(getString(R.string.error_invalid_name));
            else presenter.checkNameDuplication(name);
        }

    }

    @OnClick(R.id.join_btn_next)
    public void setNextBtn(){
        String id = inputId.getText().toString();
        String name = inputNickName.getText().toString();
        String pw = inputPwd.getText().toString();
        String email = inputEmail.getText().toString();

        checkEditText();

        if (nameValid && emailValid && pwValid) {
            Intent intent = new Intent(getApplicationContext(), JoinImgActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("name", name);
            intent.putExtra("pw", pw);
            intent.putExtra("email", email);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void isIdDuplicated(String result) {
        if (result.equals("Duplicated")) {
            Toast.makeText(getApplicationContext(), getString(R.string.register_id_duplicated), Toast.LENGTH_SHORT).show();
            idValid = false;
        } else if (result.equals("Unduplicated")) {
            Toast.makeText(getApplicationContext(), getString(R.string.register_id_unduplicated), Toast.LENGTH_SHORT).show();
            idValid = true;
        }
    }

    @Override
    public void isNameDuplicated(String result) {
        if (result.equals("Duplicated")) {
            Toast.makeText(getApplicationContext(), getString(R.string.register_name_duplicated), Toast.LENGTH_SHORT).show();
            nameValid = false;
        } else if (result.equals("Unduplicated")) {
            Toast.makeText(getApplicationContext(), getString(R.string.register_name_unduplicated), Toast.LENGTH_SHORT).show();
            nameValid = true;
        }
    }

    @Override
    public void checkEditText() {
        String pw = inputPwd.getText().toString();
        String email = inputEmail.getText().toString();

        if (TextUtils.isEmpty(email)) inputEmail.setError(getString(R.string.error_field_required));
        else {
            if (!isEmailValid(email)) inputEmail.setError(getString(R.string.error_invalid_email));
            else emailValid = true;
        }

        if (TextUtils.isEmpty(pw)) inputPwd.setError(getString(R.string.error_field_required));
        else {
            if (!isPasswordValid()) inputPwd.setError(getString(R.string.error_invalid_password));
            else {
                if (!isPasswordCheck())
                    inputPwd_Conf.setError(getString(R.string.error_incorrect_password));
                else pwValid = true;
            }
        }

        if (!nameValid)
            Toast.makeText(getApplicationContext(), getString(R.string.register_check_name), Toast.LENGTH_SHORT).show();
        else if (!emailValid)
            Toast.makeText(getApplicationContext(), getString(R.string.register_check_email), Toast.LENGTH_SHORT).show();
        else if (!pwValid)
            Toast.makeText(getApplicationContext(), getString(R.string.register_check_pw), Toast.LENGTH_SHORT).show();

    }


    private boolean isIdValid(String user_id) {
        return user_id.length() >= 4;
    }

    private boolean isNameValid(String name) {
        return name.length() >= 2;
    }
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid() {
        return inputPwd.getText().toString().length() >= 6;
    }

    private boolean isPasswordCheck() {
        return inputPwd.getText().toString().equals(inputPwd_Conf.getText().toString());
    }
}
