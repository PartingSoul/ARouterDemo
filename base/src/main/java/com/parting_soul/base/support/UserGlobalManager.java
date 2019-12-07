package com.parting_soul.base.support;

/**
 * @author parting_soul
 * @date 2019-12-06
 */
public class UserGlobalManager implements IUserManager {
    private IUserManager mLoginManager;

    private static UserGlobalManager sUserGlobalManager;

    public static UserGlobalManager getInstance() {
        if (sUserGlobalManager == null) {
            synchronized (UserGlobalManager.class) {
                if (sUserGlobalManager == null) {
                    sUserGlobalManager = new UserGlobalManager();
                }
            }
        }
        return sUserGlobalManager;
    }

    @Override
    public boolean isLogin() {
        return mLoginManager != null && mLoginManager.isLogin();
    }


    public void setUserManager(IUserManager manager) {
        this.mLoginManager = manager;
    }
}
