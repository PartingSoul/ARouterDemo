package com.parting_soul.arouterdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.parting_soul.arouterdemo.R;
import com.parting_soul.arouterdemo.entity.Cat;
import com.parting_soul.arouterdemo.entity.Dog;
import com.parting_soul.base.support.Constants;
import com.parting_soul.base.support.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route(path = Constants.ACTIVITY_URL_PATH_MAIN)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayList<Cat> lists = new ArrayList<>();
        lists.add(new Cat("cat1", 12));
        lists.add(new Cat("cat2", 17));


        Map<String, List<Cat>> maps = new HashMap<>();
        maps.put("data", lists);

        ARouter.getInstance()
                .build(Constants.ACTIVITY_URL_PATH_A)
                .withLong("id", 123456L)
                .withString("title", "标题")
                .withObject("dog", new Dog("tom", 12))
                .withObject("catObject", new Cat("tom", 12))
                .withParcelableArrayList("catLists", lists)
                .withParcelable("cat", new Cat("cat3", 33))
                .withObject("map", maps)
                .navigation();


        ARouter.getInstance()
                .build(Constants.ACTIVITY_URL_PATH_ORDER_A)
                .navigation();

    }

    public void onClick(View view) {
        Uri uri = null;
        switch (view.getId()) {
            case R.id.bt_jump1:
                uri = Uri.parse("arouter://parting_soul.com/order/Order_A_Activity");
                break;
            case R.id.bt_jump2:
                uri = Uri.parse("arouter://parting_soul.com/notfound");
                break;
            case R.id.bt_jump3:
                intercept();
                break;
            case R.id.bt_jump4:
                startForResult();
                break;
            default:
        }
        if (uri != null) {
            Intent intent = new Intent();
            intent.setData(uri);
            startActivity(intent);
        }
    }

    private void intercept() {
        ARouter.getInstance()
                .build(Constants.ACTIVITY_URL_PATH_ORDER_A)
                .navigation(this, new NavigationCallback() {
                    @Override
                    public void onFound(Postcard postcard) {
                        LogUtils.d("");
                    }

                    @Override
                    public void onLost(Postcard postcard) {
                        LogUtils.d("");
                    }

                    @Override
                    public void onArrival(Postcard postcard) {
                        Toast.makeText(getApplicationContext(), "进入订单界面", Toast.LENGTH_SHORT).show();
                        LogUtils.d("");
                    }

                    @Override
                    public void onInterrupt(Postcard postcard) {
                        //在子线程中回调
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "未登录", Toast.LENGTH_SHORT).show();
                            }
                        });
                        LogUtils.d("");
                    }
                });
    }


    private void startForResult() {
        ARouter.getInstance()
                .build(Constants.ACTIVITY_URL_PATH_B)
                .navigation(this, 0x12, new NavigationCallback() {
                    @Override
                    public void onFound(Postcard postcard) {

                    }

                    @Override
                    public void onLost(Postcard postcard) {

                    }

                    @Override
                    public void onArrival(Postcard postcard) {

                    }

                    @Override
                    public void onInterrupt(Postcard postcard) {

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Activity.RESULT_OK == resultCode && requestCode == 0x12) {
            LogUtils.d("");
        }
    }
}
