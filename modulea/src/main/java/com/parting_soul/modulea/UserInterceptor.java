package com.parting_soul.modulea;

import android.content.Context;
import android.os.Handler;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.parting_soul.base.support.UserGlobalManager;

/**
 * @author parting_soul
 * @date 2019-12-06
 */
@Interceptor(priority = 1)
public class UserInterceptor implements IInterceptor {
    private Handler mHandler;

    @Override
    public void process(final Postcard postcard, final InterceptorCallback callback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                boolean isLogin = UserGlobalManager.getInstance().isLogin();
                if (isLogin) {
                    //已经登录，继续执行
                    callback.onContinue(postcard);
                } else {
                    //未登录，进行拦截
                    callback.onInterrupt(null);
                }
            }
        });


    }


    @Override
    public void init(Context context) {
        mHandler = new Handler(context.getMainLooper());
    }

}
