package com.parting_soul.arouterdemo.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.parting_soul.arouterdemo.entity.Cat;
import com.parting_soul.arouterdemo.entity.Dog;
import com.parting_soul.base.activity.BaseActivity;
import com.parting_soul.base.support.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author parting_soul
 * @date 2019-12-05
 */
@Route(path = Constants.ACTIVITY_URL_PATH_A)
public class A_Activity extends BaseActivity {

    private static final String TAG = "tag";

    @Autowired
    long id;

    @Autowired(name = "title")
    String title;

    @Autowired
    Dog dog;


    /**
     * withParcelableArrayList 这里有点坑，接收类型需为具体类型
     */
    @Autowired(name = "catLists")
    ArrayList<Cat> mCatLists;

    @Autowired(name = "cat")
    Cat mCat;

    @Autowired(name = "catObject")
    Cat catObject;

    @Autowired(name = "map")
    Map<String, List<Cat>> mMap;

    @Override
    public String getMsg() {
        return "A_Activity";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ARouter.getInstance().inject(this);

        Log.e(TAG, "onCreate: id = " + id);
        Log.e(TAG, "onCreate: title = " + title);
        Log.e(TAG, "onCreate: dog = " + dog);
        Log.e(TAG, "onCreate: catLists = " + mCatLists);
        Log.e(TAG, "onCreate: mCat = " + mCat);
        Log.e(TAG, "onCreate: mMap = " + mMap);
        Log.e(TAG, "onCreate: catObject = " + catObject);
    }

}
