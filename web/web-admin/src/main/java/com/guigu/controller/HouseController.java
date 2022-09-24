package com.guigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.guigu.entity.*;
import com.guigu.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/house")
public class HouseController extends BaseController{
    @Reference
    private HouseService houseService;
    @Reference
    private CommunityService communityService;
    @Reference
    private DictService dictService ;
    @Reference
    private HouseBrokerService houseBrokerService ;
    @Reference
    private HouseImageService houseImageService ;
    @Reference
    private HouseUserService houseUserService;
    //分页及带条件查询的方法
    @RequestMapping
    public String index(Map map , HttpServletRequest request){
        //获取请求参数
        Map<String,Object> filters = getFilters(request);
        //将请求参数加入到request域中
        map.put("filters",filters);
        //调用HouseService中的分页方法
        PageInfo<House> page = houseService.findPage(filters);
        map.put("page",page);
         setRequestAttribute(map);
        return "house/index";
    }

    //去添加房源的页面
    @RequestMapping("/create")
    public String goAddPage(Map map){
         setRequestAttribute(map);
        return "house/create";
    }
    //添加房源
    @RequestMapping("/save")
    public String save(House house){
        //调用HouseService中添加的方法
        houseService.insert(house);
        return "common/successPage";
    }
    //去修改的页面
    @RequestMapping("/edit/{id}")
    public String goEditPage(@PathVariable("id") Long id ,Map map){
        //调用HouseService中查询的方法
        House house = houseService.getById(id);
        //将byId放入request域中
        map.put("house",house);
        //将小区和数据字典中的数据放到requset域中
        setRequestAttribute(map);
        return "house/edit";
    }
    //更新
    @RequestMapping("/update")
    public String update(House house){
        houseService.update(house);
        return "common/successPage";
    }
    //删除
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        houseService.delete(id);
        return "redirect:/house";
    }

    //将所有小区及数据字典中数据放到request域中的方法
    public void setRequestAttribute(Map map){
        //获取所有的小区
        List<Community> communityList= communityService.findAll();
        //获取所有户型
        List<Dict> houseTypeList = dictService.findListByDictCode("houseType");
        //获取所有楼层
        List<Dict> floorList = dictService.findListByDictCode("floor");
        //获取建筑结构
        List<Dict> buildStructureList = dictService.findListByDictCode("buildStructure");
        //获取朝向
        List<Dict> directionList = dictService.findListByDictCode("direction");
        //获取装饰情况
        List<Dict> decorationList = dictService.findListByDictCode("decoration");
        //获取房屋用途
        List<Dict> houseUseList = dictService.findListByDictCode("houseUse");
        //放入request域中
        map.put("communityList",communityList);
        map.put("houseTypeList",houseTypeList);
        map.put("floorList",floorList);
        map.put("buildStructureList",buildStructureList);
        map.put("directionList",directionList);
        map.put("decorationList",decorationList);
        map.put("houseUseList",houseUseList);
    }
    //发布和取消发布
    @RequestMapping("/publish/{houseId}/{status}")
    public String publish(@PathVariable("houseId") Long houseId ,@PathVariable("status") Integer status){
        //调用HouseService中发布或取消发布的方法
        houseService.publish(houseId, status);
        return "redirect:/house";//删除什么的重定向 弹框的话successPage
    }
    //查询房源详情
    @RequestMapping("/{houseId}")
    public String show(@PathVariable("houseId") Long houseId,Map map){
        //调用HouseService中根据id查询房源信息
        // 由于有部分房源信息户型、小区区域板块啥的都没有完全显示
        // 是因为查询的都是id  所有要重写这个方法 把汉语名字附上去
        House house = houseService.getById(houseId);
        //放入requet域中
        map.put("house",house);
        //调用CommunitySercvice中根据小区id查询小区信息
        Community community = communityService.getById(house.getCommunityId());
        map.put("community",community);
        //查询房源图片
        List<HouseImage> houseImage1List = houseImageService.getHouseImagesByHouseIdAndType(houseId, 1);
        //查询房产图片
        List<HouseImage> houseImage2List = houseImageService.getHouseImagesByHouseIdAndType(houseId, 2);
        //查询经纪人
        List<HouseBroker> houseBrokerList = houseBrokerService.getHouseBrokerByHouseId(houseId);
        //查询房东
        List<HouseUser> houseUserList = houseUserService.getHouseUserByHouseId(houseId);
        map.put("houseImage1List",houseImage1List);
        map.put("houseImage2List",houseImage2List);
        map.put("houseBrokerList",houseBrokerList);
        map.put("houseUserList",houseUserList);

        return "house/show";
    }

}
