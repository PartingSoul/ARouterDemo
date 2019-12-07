package com.parting_soul.arouterdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.parting_soul.base.activity.BaseActivity;
import com.parting_soul.base.support.Constants;

/**
 * @author parting_soul
 * @date 2019-12-05
 */
@Route(path = Constants.ACTIVITY_URL_PATH_B)
public class B_Activity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        intent.putExtra("data", "hello ");
        setResult(Activity.RESULT_OK, intent);
    }

    @Override
    public String getMsg() {
        return "B_Activity";
    }

}
