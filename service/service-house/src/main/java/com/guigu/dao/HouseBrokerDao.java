package com.guigu.dao;


import com.guigu.entity.HouseBroker;

import java.util.List;

public interface HouseBrokerDao extends BaseDao<HouseBroker> {
    //根据房源的id查出经纪人来
    List<HouseBroker> getHouseBrokerByHouseId(Long houseId);

}
