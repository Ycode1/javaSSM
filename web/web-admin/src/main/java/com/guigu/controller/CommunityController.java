package com.guigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.guigu.entity.Community;
import com.guigu.entity.Dict;
import com.guigu.service.CommunityService;
import com.guigu.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
    //添加小区的页面
    @RequestMapping("/create")
    public String goAddPage(Map map){
        //根据编码获取北京所有的区
        List<Dict> areaList = dictService.findListByDictCode("beijing");
        //将北京所有的区域放入request域中
        map.put("areaList",areaList);
        return  "community/create";
    }
    //添加小区
    @RequestMapping("/save")
    public String save(Community community){
        //调用CommunityService中添加的方法
        communityService.insert(community);
        //去成功页面
        return "common/successPage";
    }
    //去修改小区的页面
    @RequestMapping("/edit/{id}")
    public String goEdit(@PathVariable("id")Long id , Map map){
        //根据编码获取北京所有的区
        List<Dict> areaList = dictService.findListByDictCode("beijing");
        //将北京所有的区域放入request域中
        map.put("areaList",areaList);
        //调用CommunityService中查询的方法
        Community community  = communityService.getById(id);
        //将小区放到request域中
        map.put("community",community);
        return "community/edit";
    }

    @RequestMapping("/update")
    public String update(Community community){
        //调用CommunityService中更新的方法
        communityService.update(community);
        return "common/successPage";
    }

    @RequestMapping("delete/{id}")
    public String delete(@PathVariable("id") Long id){
        //调用CommunityService中删除的方法
        communityService.delete(id);
        return "redirect:/community";
    }
}
