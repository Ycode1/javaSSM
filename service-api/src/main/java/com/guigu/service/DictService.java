package com.guigu.service;

import com.guigu.entity.Dict;

import java.util.List;
import java.util.Map;

public interface DictService {
    //查询数据字典中的数据，通过zTree
    List<Map<String, Object>> findZnodes(Long id);
    //根据父id获取该节点下所有的子节点
    List<Dict> findListByParentId(Long parentId);
    //根据编码获取该节点下所有的子节点
    List<Dict> findListByDictCode(String dictCode);
    String getNameById(Long id );
}
