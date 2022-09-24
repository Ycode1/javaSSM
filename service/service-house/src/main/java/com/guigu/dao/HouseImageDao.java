package com.guigu.dao;

import com.guigu.entity.HouseImage;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface HouseImageDao extends BaseDao<HouseImage> {
    //根据房源id和类型查询房源和房产图片
    List<HouseImage> getHouseImagesByHouseIdAndType(@Param("houseId") Long houseId, @Param("type") Integer type);

}
