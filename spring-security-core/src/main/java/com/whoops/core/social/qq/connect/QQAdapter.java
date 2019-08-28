package com.whoops.core.social.qq.connect;

import com.whoops.core.social.qq.api.QQ;
import com.whoops.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

public class QQAdapter implements ApiAdapter<QQ> {
    @Override
    public boolean test(QQ api) {
        return true;
    }

    @Override
    public void setConnectionValues(QQ api, ConnectionValues values){
        QQUserInfo userInfo = api.getUserInfo();
        //显示的名字
        values.setDisplayName(userInfo.getNickname());
        //头像路径
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        //个人主页路径
        values.setProfileUrl(null);
        //服务商的用户id
        values.setProviderUserId(userInfo.getOpenId());
    }


    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }

    @Override
    public void updateStatus(QQ api, String message) {
        //do nothing
    }
}
