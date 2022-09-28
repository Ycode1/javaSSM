package com.guigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guigu.dao.BaseDao;
import com.guigu.dao.DictDao;
import com.guigu.dao.HouseDao;
import com.guigu.entity.House;
import com.guigu.service.HouseService;
import com.guigu.vo.HouseQueryVo;
import com.guigu.vo.HouseVo;
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

    @Override
    public PageInfo<HouseVo> findPageList(Integer pageNum, Integer pageSize, HouseQueryVo houseQueryVo) {
       //开启分页
        PageHelper.startPage(pageNum,pageSize);
        //调用HouseDao中前端分页及带条件查询的方法
        Page<HouseVo> page = houseDao.findPageList(houseQueryVo);
        //遍历page
        for (HouseVo houseVo : page) {
            //获取房屋的类型
            String houseTypeName = dictDao.getNameById(houseVo.getHouseTypeId());
            //获取楼层
            String floorName = dictDao.getNameById(houseVo.getFloorId());
            //获取房屋的朝向
            String directionName = dictDao.getNameById(houseVo.getDirectionId());
            houseVo.setHouseTypeName(houseTypeName);
            houseVo.setFloorName(floorName);
            houseVo.setDirectionName(directionName);
        }
        return new PageInfo<>(page,5);
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
