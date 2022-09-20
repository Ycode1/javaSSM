package com.guigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.guigu.entity.Community;
import com.guigu.entity.Dict;
import com.guigu.service.CommunityService;
import com.guigu.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/community")
public class CommunityController extends BaseController{
    @Reference
    private CommunityService communityService;
    @Reference
    private DictService dictService;
    //分页带条件的查询
    @RequestMapping
    public String index(Map map , HttpServletRequest request){
        //获取请求参数
        Map<String,Object> filters = getFilters(request);
        //将filters放入request域中
        map.put("filters",filters);
        //调用CommunityService中分页的方法
        PageInfo<Community> pageInfo = communityService.findPage(filters);
        //将pageInfo放到request域中
        map.put("page",pageInfo);
        //根据编码获取北京所有的区
        List<Dict> areaList = dictService.findListByDictCode("beijing");
        //将北京所有的区域放入request域中
        map.put("areaList",areaList);
        return "community/index";
    }

}
