package com.guigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.guigu.entity.HouseUser;
import com.guigu.service.HouseUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/houseUser")
public class HouseUserController {
    @Reference
    private HouseUserService houseUserService ;
    //去添加房东的页面
    @RequestMapping("/create")
    public String goAddPage(@RequestParam("houseId") Long houseId , Map map){
      //将房源id当到request域中
        map.put("houseId",houseId);
        return "houseUser/create";
    }
    //添加放到
    @RequestMapping("/save")
    public String save(HouseUser houseUser){
        //调用HouseUserService中添加的方法
        houseUserService.insert(houseUser);
        return "common/successPage";
    }
    //去修改页面
    @RequestMapping("/edit/{id}")
    public String goEditPage(@PathVariable("id") Long id ,Map map){
        //调用HouseUserService中根据id查询的方法
        HouseUser houseUser  = houseUserService.getById(id);
        map.put("houseUser",houseUser);
        return "houseUser/edit";
    }
    //更新操作
    @RequestMapping("/update")
    public String update(HouseUser houseUser){
        houseUserService.update(houseUser);
        return "common/successPage";
    }
    //删除操作
    @RequestMapping("/delete/{houseId}/{houseUserId}")
    public String delete(@PathVariable("houseId") Long houseId , @PathVariable("houseUserId") Long houseUserId){
        houseUserService.delete(houseUserId);
        return "redirect:/house/" +  houseId;
    }
}
