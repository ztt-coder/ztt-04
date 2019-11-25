package com.example.jxxy.z.activity;

import android.widget.TextView;

import com.example.jxxy.z.R;
import com.example.jxxy.z.common.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MyAddressActivity extends BaseActivity{

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    public int getContentViewId(){ return R.layout.activity_my_address;}

    @Override
    protected void initView(){
        super.initView();
        tvTitle.setText("我的地址");
    }

    @OnClick(R.id.iv_back)
    void close(){finish();}
}
