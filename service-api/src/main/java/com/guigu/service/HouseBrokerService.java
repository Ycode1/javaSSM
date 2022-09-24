package com.guigu.service;


import com.guigu.entity.HouseBroker;

import java.util.List;

public interface HouseBrokerService extends  BaseService<HouseBroker> {
    //根据房源的id查出经纪人来
    List<HouseBroker> getHouseBrokerByHouseId(Long houseId);
}
