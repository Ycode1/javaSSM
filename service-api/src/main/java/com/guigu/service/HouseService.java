package com.guigu.service;

import com.github.pagehelper.PageInfo;
import com.guigu.entity.House;
import com.guigu.vo.HouseQueryVo;
import com.guigu.vo.HouseVo;

public interface HouseService extends BaseService<House> {
    //发布或取消发布
    void publish(Long houseId, Integer status);
        //前端分页及条件查询的方法
    PageInfo<HouseVo> findPageList(Integer pageNum, Integer pageSize , HouseQueryVo houseQueryVo);
}
