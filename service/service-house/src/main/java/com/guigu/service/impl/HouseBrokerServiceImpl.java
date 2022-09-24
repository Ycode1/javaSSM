package com.guigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.guigu.dao.BaseDao;
import com.guigu.dao.HouseBrokerDao;
import com.guigu.entity.HouseBroker;
import com.guigu.service.HouseBrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Service(interfaceClass = HouseBrokerService.class)
@Transactional
public class HouseBrokerServiceImpl extends BaseServiceImpl<HouseBroker> implements HouseBrokerService {
    @Autowired
    private HouseBrokerDao houseBrokerDao;
    @Override
    public List<HouseBroker> getHouseBrokerByHouseId(Long houseId) {
        return houseBrokerDao.getHouseBrokerByHouseId(houseId);
    }



    @Override
    protected BaseDao<HouseBroker> getEntityDao() {
        return houseBrokerDao;
    }
}
