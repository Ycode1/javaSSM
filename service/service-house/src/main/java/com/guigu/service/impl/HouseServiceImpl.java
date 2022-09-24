package com.guigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.guigu.dao.BaseDao;
import com.guigu.dao.DictDao;
import com.guigu.dao.HouseDao;
import com.guigu.entity.House;
import com.guigu.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service(interfaceClass = HouseService.class)
@Transactional
public class HouseServiceImpl extends BaseServiceImpl<House>  implements HouseService {
    @Autowired
    private HouseDao houseDao;

    @Autowired
    private DictDao dictDao;

    @Override
    protected BaseDao<House> getEntityDao() {
        return houseDao;
    }

    @Override
    public void publish(Long houseId, Integer status) {
        //创建一个house对象
        House house = new House();
        //设置id和状态
        house.setId(houseId);
        house.setStatus(status);
        //调用HouseDao里面更新的方法
         houseDao.update(house);
    }
//重写该方法是为了展示房源中户型、楼层、朝向等信息
    @Override
    public House getById(Serializable id) {
        House house = houseDao.getById(id);
        //获取户型
        String houseTypeName = dictDao.getNameById(house.getHouseTypeId());
        //获取楼层
        String floorName = dictDao.getNameById(house.getFloorId());
        //建筑结构：
        String buildStructureName = dictDao.getNameById(house.getBuildStructureId());
        //获取朝向
        String directionName = dictDao.getNameById(house.getDirectionId());
        //装修情况：
        String decorationName = dictDao.getNameById(house.getDecorationId());
        //房屋用途：
        String houseUseName = dictDao.getNameById(house.getHouseUseId());
        //设置
        house.setHouseTypeName(houseTypeName);
        house.setFloorName(floorName);
        house.setBuildStructureName(buildStructureName);
        house.setDirectionName(directionName);
        house.setDecorationName(decorationName);
        house.setHouseUseName(houseUseName);
        return house;

    }
}
