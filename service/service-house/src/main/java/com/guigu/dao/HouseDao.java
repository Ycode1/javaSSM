package com.guigu.dao;

import com.github.pagehelper.Page;
import com.guigu.entity.House;
import com.guigu.vo.HouseQueryVo;
import com.guigu.vo.HouseVo;

public interface HouseDao extends BaseDao<House>{
    Page<HouseVo> findPageList(HouseQueryVo houseQueryVo);
}
