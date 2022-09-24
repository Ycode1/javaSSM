package com.guigu.dao;

import com.guigu.entity.Dict;

import java.util.List;

public interface DictDao extends BaseDao<Dict>{
//根据父id获取该节点下所有的子节点
    List<Dict> findListByParentId(Long id);
//根据父id判断该节点是否是父节点
    Integer isParentNode(Long id);
    //根据编码获取Dict对象
    Dict getDictByDictCode(String dictCode);
    //根据id获取name
    String getNameById(Long id);
}
