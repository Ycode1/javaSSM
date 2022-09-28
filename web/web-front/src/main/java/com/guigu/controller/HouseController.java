package com.guigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.guigu.entity.*;
import com.guigu.result.Result;
import com.guigu.service.*;
import com.guigu.vo.HouseQueryVo;
import com.guigu.vo.HouseVo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/house")
public class HouseController {
    @Reference
    private HouseService houseService ;
    @Reference
    private CommunityService communityService ;
    @Reference
    private HouseImageService houseImageService ;
    @Reference
    private HouseBrokerService houseBrokerService;
    @Reference
    private UserFollowService userFollowService;
    //分页及带条件查询的方法
    @RequestMapping("/list/{pageNum}/{pageSize}")
    public Result findPageList(@PathVariable("pageNum")Integer pageNum,@PathVariable("pageSize") Integer pageSize,
                               @RequestBody HouseQueryVo houseQueryVo){
        //调用HouseService中前端页面分页及带条件查询的方法
        PageInfo<HouseVo> pageInfo = houseService.findPageList(pageNum,pageSize,houseQueryVo);
        return Result.ok(pageInfo);
    }
    //查询
    @RequestMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id , HttpSession session){
        //调用HouseService中查询房源的方法
        House house = houseService.getById(id);
        //获取小区信息
        Community community = communityService.getById(house.getCommunityId());
        //获取房源经纪人信息
        List<HouseBroker> houseBrokerList = houseBrokerService.getHouseBrokerByHouseId(id);
        //获取房产图片信息
        List<HouseImage> houseImageList = houseImageService.getHouseImagesByHouseIdAndType(id, 1);
        //创建一个Map
        Map  map = new HashMap<>();
        //将这些加入map中
        map.put("house",house);
        map.put("community",community);
        map.put("houseBrokerList",houseBrokerList);
        map.put("houseImageList",houseImageList);
        //设置默认没有关注该房源
       // map.put("isFollow",false);
        //设置一个变量
        Boolean isFollowed = false ;
        //从Session域中获取UserInfo对象
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        if(userInfo != null){
            //证明已经登录
            //调用UserFollowService中查询是否关注该房源
           isFollowed = userFollowService.isFollowed(userInfo.getId(),id);
        }
        //将isFollowed放入Map中
        map.put("isFollow",isFollowed);
        return Result.ok(map);
    }
}
