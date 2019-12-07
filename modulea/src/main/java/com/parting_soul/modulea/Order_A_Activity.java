package com.parting_soul.modulea;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.parting_soul.base.activity.BaseActivity;
import com.parting_soul.base.support.Constants;

/**
 * @author parting_soul
 * @date 2019-12-05
 */
@Route(path = Constants.ACTIVITY_URL_PATH_ORDER_A)
public class Order_A_Activity extends BaseActivity {

    @Override
    public String getMsg() {
        return "Order_A_Activity";
    }

}
