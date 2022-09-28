package com.guigu.service;

import com.github.pagehelper.PageInfo;
import com.guigu.entity.UserFollow;
import com.guigu.vo.UserFollowVo;

public interface UserFollowService extends BaseService<UserFollow> {
    //关注房源
    void follow(Long id, Long houseId);
    //是否关注该房源
    Boolean isFollowed(Long id, Long id1);
//分页查询关注的房源
    PageInfo<UserFollowVo> findPageList(Integer pageNum, Integer pageSize, Long id);
    //取消关注房源
    void cancelFollowed(Long id);
}
