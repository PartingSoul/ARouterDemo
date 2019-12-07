package com.parting_soul.modulea;

import com.parting_soul.base.support.IUserManager;

/**
 * @author parting_soul
 * @date 2019-12-06
 */
public class UserManager implements IUserManager {
    int count;

    @Override
    public boolean isLogin() {
        return count++ % 2 == 0;
    }

}
