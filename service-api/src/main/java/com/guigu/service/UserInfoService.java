package com.guigu.service;

import com.guigu.entity.UserInfo;

public interface UserInfoService extends BaseService<UserInfo>{
    UserInfo getUserInfoByPhone(String phone);
}
