package com.parting_soul.base.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parting_soul.base.R;

/**
 * @author parting_soul
 * @date 2019-12-05
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static final String KEY_MSG = "key_msg";
    private String msg;

    private TextView mTvMsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_msg);
        mTvMsg = findViewById(R.id.tv_msg);
        msg = getIntent().getStringExtra(KEY_MSG);
        mTvMsg.setText(getMsg());
    }

    public static void start(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }


    public abstract String getMsg();
}
