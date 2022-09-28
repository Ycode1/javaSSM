package com.guigu.dao;

import com.guigu.entity.UserInfo;

public interface UserInfoDao extends BaseDao<UserInfo>{


    UserInfo getUserInfoByPhone(String phone);
}
