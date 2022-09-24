package com.guigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.guigu.dao.BaseDao;
import com.guigu.dao.HouseImageDao;
import com.guigu.entity.House;
import com.guigu.entity.HouseImage;
import com.guigu.service.HouseImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service(interfaceClass = HouseImageService.class)
@Transactional
public class HouseImageServiceImpl extends BaseServiceImpl<HouseImage> implements HouseImageService {
    @Autowired
    private HouseImageDao houseImageDao;

    @Override
    public List<HouseImage> getHouseImagesByHouseIdAndType(Long houseId, Integer type) {
        return  houseImageDao.getHouseImagesByHouseIdAndType(houseId,type);
    }

    @Override
    protected BaseDao<HouseImage> getEntityDao() {
        return houseImageDao;
    }
}
