package com.guigu.service;

import com.guigu.entity.House;

public interface HouseService extends BaseService<House> {
    void publish(Long houseId, Integer status);
}
