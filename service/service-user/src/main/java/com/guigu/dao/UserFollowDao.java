package com.guigu.dao;

import com.github.pagehelper.Page;
import com.guigu.entity.UserFollow;
import com.guigu.vo.UserFollowVo;
import org.apache.ibatis.annotations.Param;

public interface UserFollowDao extends BaseDao<UserFollow> {
    Integer getCountByUserIdAndHouseId(@Param("userId") Long userId,@Param("houseId") Long houseId);

    Page<UserFollowVo> findPageList(Long userId);
}
