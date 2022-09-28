package com.guigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.guigu.entity.Dict;
import com.guigu.result.Result;
import com.guigu.service.DictService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dict")
public class DictController {
    @Reference
    DictService dictService ;
    //根据编码获取所有子节点
    @RequestMapping("/findListByDictCode/{dictCode}")
    public Result findListByDictCode(@PathVariable("dictCode")String dictCode){
        //调用DictService中根据编码获取所有子节点的方法
        List<Dict> listByDictCode = dictService.findListByDictCode(dictCode);
        return Result.ok(listByDictCode);
    }
    //根据父id查询所有的子节点
    @RequestMapping("/findListByParentId/{areaId}")
    public Result findListByParentId(@PathVariable("areaId") Long areaId){
        //调用DictService中根据父Id获取所有子节点的方法
        List<Dict> listByParentId = dictService.findListByParentId(areaId);
        return Result.ok(listByParentId);
    }
}
