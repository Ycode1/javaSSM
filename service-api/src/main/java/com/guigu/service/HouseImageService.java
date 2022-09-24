package com.guigu.service;

import com.guigu.entity.HouseImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HouseImageService extends BaseService<HouseImage>{
    //根据房源id和类型查询房源和房产图片
    List<HouseImage> getHouseImagesByHouseIdAndType(Long houseId,Integer type);

}
