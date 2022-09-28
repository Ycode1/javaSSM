package com.guigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.guigu.dao.DictDao;
import com.guigu.entity.Dict;
import com.guigu.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = DictService.class)
@Transactional
public class DictServiceImpl implements DictService {
    @Autowired
    private DictDao dictDao;
    @Override
    public List<Map<String, Object>> findZnodes(Long id) {
        //根据父Id查询节点下的所有子节点
        List<Dict> dictList = dictDao.findListByParentId(id);
        //创建返回的List
        List<Map<String, Object>> zNodes = new ArrayList<>();
        //遍历dicList
        for (Dict dict : dictList) {
            //返回数据[{ id:2, isParent:true, name:"随意勾选 2"}]
            //创建一个Map
            Map<String,Object> map = new HashMap<>();
            map.put("id",dict.getId());
            map.put("name",dict.getName());
            //调用DictDao中判断该节点是否是父节点的方法
            Integer count = dictDao.isParentNode(dict.getId());
            map.put("isParent",count > 0 ? true : false);
            zNodes.add(map);
        }

        return zNodes;
    }

    @Override
    public List<Dict> findListByParentId(Long parentId) {
        return dictDao.findListByParentId(parentId);
    }

    @Override
    public List<Dict> findListByDictCode(String dictCode) {
        //调用DictDao中根据编码得到Dict对象的方法
        Dict dict = dictDao.getDictByDictCode (dictCode);
        if(null == dict) return null;
        //调用根据父id查询所有子节点的方法
        return this.findListByParentId(dict.getId());

    }

    @Override
    public String getNameById(Long id) {
        return dictDao.getNameById(id);
    }
}
