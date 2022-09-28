package com.guigu.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guigu.dao.BaseDao;
import com.guigu.dao.UserFollowDao;
import com.guigu.entity.UserFollow;
import com.guigu.service.DictService;
import com.guigu.service.UserFollowService;
import com.guigu.vo.UserFollowVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = UserFollowService.class)
@Transactional
public class UserFollowServiceImpl extends BaseServiceImpl<UserFollow> implements UserFollowService {
    @Autowired
    private UserFollowDao userFollowDao ;
    @Reference
    private DictService dictService ;
    @Override
    protected BaseDao<UserFollow> getEntityDao() {
        return userFollowDao;
    }

    @Override
    public void follow(Long id, Long houseId) {
        //创建UserFollow对象
        UserFollow userFollow = new UserFollow();
        userFollow.setUserId(id);
        userFollow.setHouseId(houseId);
        //调用UserFollowDao中的insert方法
        userFollowDao.insert(userFollow);
    }

    @Override
    public Boolean isFollowed(Long userId, Long houseId) {
        //调用UserFollowDao中查询是否关注该房源的方法
        Integer count = userFollowDao.getCountByUserIdAndHouseId(userId,houseId);
        if(count > 0) {
            //说明已关注了
            return true ;
        }else{
            return false;
        }


    }

    @Override
    public PageInfo<UserFollowVo> findPageList(Integer pageNum, Integer pageSize, Long userId) {
       //开启分页
         PageHelper.startPage(pageNum, pageSize);
        //调用UserFollowDao中分页的方法
        Page<UserFollowVo> page = userFollowDao.findPageList(userId);
        //遍历Page
        for (UserFollowVo followDao : page) {
                //获取房屋的信息
            String houseTypeName = dictService.getNameById(followDao.getHouseTypeId());
            //获取楼层
            String floorName = dictService.getNameById(followDao.getFloorId());
            //获取朝向
            String directionName = dictService.getNameById(followDao.getDirectionId());
            followDao.setHouseTypeName(houseTypeName);
            followDao.setFloorName(floorName);
            followDao.setDirectionName(directionName);
        }
        return new PageInfo<>(page,5);
    }

    @Override
    public void cancelFollowed(Long id) {
        //调用UserFollowDao中删除的方法
        userFollowDao.delete(id);
    }
}
