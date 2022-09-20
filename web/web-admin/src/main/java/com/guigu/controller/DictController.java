package com.guigu.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.guigu.entity.Dict;
import com.guigu.result.Result;
import com.guigu.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dict")
public class DictController {

    @Reference
    private DictService dictService;

    //去展示数据字典的页面
    @RequestMapping
    public String index(){
        return "dict/index";
    }

    @ResponseBody
    //获取数据字典中的数据
    @RequestMapping("/findZnodes")
    public Result findZnodes(@RequestParam(value = "id",defaultValue = "0") Long id){
        //调用DictService中数据字典中的数据
        List<Map<String,Object>> zNodes = dictService.findZnodes(id);
        return Result.ok(zNodes);
    }
    //根据父id获取所有子节点
    @ResponseBody
    @RequestMapping("/findListByParentId/{areaId}")
    public Result findListByParentId(@PathVariable("areaId") Long id){
        //调用DictService中根据父id查询所有子节点中的方法
        List<Dict> listByParentId = dictService.findListByParentId(id);
        return Result.ok(listByParentId);
    }
}
