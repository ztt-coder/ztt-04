package com.example.jxxy.z.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;

import android.text.TextUtils;

import android.widget.EditText;


import com.example.jxxy.z.R;
import com.example.jxxy.z.common.BaseActivity;
import com.example.jxxy.z.http.ProgressDialogSubscriber;
import com.example.jxxy.z.http.entity.MemberEntity;
import com.example.jxxy.z.http.presenter.MemberPresenter;
import com.example.jxxy.z.utils.SystemCofig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    public int getContentViewId(){
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_back)
    void close(){
        finish();
    }
    @OnClick(R.id.bt_login)
    void login(){
        String userName = etUsername.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();
        if (TextUtils.isEmpty(userName)){
            toastShort("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(pwd)){
            toastShort("请输入密码");
            return;
        }
        MemberPresenter.login(new ProgressDialogSubscriber<MemberEntity>(this){
            public void onNext(MemberEntity memberEntity){
                SystemCofig.setLogin(true);
                toastShort("登录成功");
                SystemCofig.setLoginUserName(memberEntity.uname);
                SystemCofig.setLoginUserEmail(memberEntity.email);
                SystemCofig.setLoginUserHead(memberEntity.image);
                setResult(RESULT_OK);
                finish();
            }
        }, userName, pwd);
    }
}