package com.parting_soul.base.activity;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.parting_soul.base.support.LogUtils;
import com.parting_soul.base.support.Constants;

/**
 * @author parting_soul
 * @date 2019-12-06
 */
@Route(path = Constants.ACTIVITY_URL_PATH_SCHEMA)
public class SchemaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri uri = getIntent().getData();
        if (uri != null) {
            ARouter.getInstance().build(uri)
                    .navigation(this, new NavigationCallback() {
                        @Override
                        public void onFound(Postcard postcard) {
                            //找到该路径
                            LogUtils.d("");
                            finish();
                        }

                        @Override
                        public void onLost(Postcard postcard) {
                            //没找到路径
                            LogUtils.d("");
                        }

                        @Override
                        public void onArrival(Postcard postcard) {
                            //跳转到达
                            LogUtils.d("");
                            finish();
                        }

                        @Override
                        public void onInterrupt(Postcard postcard) {
                            //跳转被拦截
                            LogUtils.d("");
                        }
                    });
        }
    }

}
