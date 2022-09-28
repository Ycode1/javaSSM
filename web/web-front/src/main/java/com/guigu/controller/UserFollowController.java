package com.guigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.guigu.entity.UserInfo;
import com.guigu.result.Result;
import com.guigu.service.UserFollowService;
import com.guigu.vo.UserFollowVo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/userFollow")
public class UserFollowController  {
    @Reference
    private UserFollowService userFollowService ;
    //关注房源
    @RequestMapping("/auth/follow/{houseId}")
    public Result follow(@PathVariable("houseId") Long houseId , HttpSession session){
        // 获取UserInfo对象
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        //调用UserFollowService中关注房源的方法
        userFollowService.follow(userInfo.getId(),houseId);
        return Result.ok();
    }
    //查询我关注的房源
    @RequestMapping("/auth/list/{pageNum}/{pageSize}")
    public Result findListPage(@PathVariable Integer pageNum,
                               @PathVariable Integer pageSize,
                               HttpSession session) {
        //从Session域中获取UserInfo对象
        UserInfo userInfo = (UserInfo)  session.getAttribute("user");
        //调用userFollowService中分页查询的方法
        PageInfo<UserFollowVo> pageInfo = userFollowService.findPageList(pageNum, pageSize, userInfo.getId());
        return Result.ok(pageInfo);
    }
    @RequestMapping("/auth/cancelFollow/{id}")
    public Result CancelFollow(@PathVariable("id") Long id){
        //调用UserFollowService中取消关注的方法
        userFollowService.cancelFollowed(id);
        return Result.ok();
    }
}
