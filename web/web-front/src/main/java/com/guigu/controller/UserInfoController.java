package com.guigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.guigu.entity.UserInfo;
import com.guigu.result.Result;
import com.guigu.result.ResultCodeEnum;
import com.guigu.service.UserInfoService;
import com.guigu.util.MD5;
import com.guigu.vo.LoginVo;
import com.guigu.vo.RegisterVo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {
    @Reference
    private UserInfoService userInfoService ;
    //发送验证码
    @RequestMapping("/sendCode/{phone}")
    public Result sendCode(@PathVariable("phone")String phone, HttpServletRequest request){
        //设置验证码为8888
        String code = "8888";
        //将验证码放到session中
        request.getSession().setAttribute("code",code);
        //将验证码响应到前端
        return Result.ok(code);
    }
    //注册
    @RequestMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo, HttpSession session){
        //获取手机号、密码、昵称、验证码
        String phone  = registerVo.getPhone();
        String password = registerVo.getPassword();
        String nickName = registerVo.getNickName();
        String code = registerVo.getCode();
        //验证空
        //校验参数
        if(StringUtils.isEmpty(nickName) ||
                StringUtils.isEmpty(phone) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)) {
            //返回参数错误的消息
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }
        //判断验证码
        //从session域中获取验证码
        String sessionCode = (String)session.getAttribute("code");
        //判断验证码是否正确
        if(!code.equals(sessionCode)) {
            return Result.build(null, ResultCodeEnum.CODE_ERROR);
        }
        //调用UserInfoService中的方法判断该手机号是否已经注册
       UserInfo userInfo = userInfoService.getUserInfoByPhone(phone);
        if(userInfo != null){
            //返回手机号已注册的消息
            return Result.build(null,ResultCodeEnum.PHONE_REGISTER_ERROR);
        }
        //创建一个UserInfo对象 然后插入到数据库中
        UserInfo  userInfo1= new UserInfo();
        userInfo1.setNickName(nickName);
        userInfo1.setPhone(phone);
        userInfo1.setPassword(MD5.encrypt(password));
        userInfo1.setStatus(1);
        userInfoService.insert(userInfo1);
        return Result.ok();
    }
    //登录
    @RequestMapping("/login")
    public Result login(@RequestBody LoginVo loginVo ,HttpSession session){
        //获取手机号和密码
        String phone = loginVo.getPhone();
        String password = loginVo.getPassword();
        //验空
        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)){
            return Result.build(null,ResultCodeEnum.PARAM_ERROR);
        }
        //根据手机号查询用户信息
        UserInfo userInfo = userInfoService.getUserInfoByPhone(phone);
        if(userInfo == null){
            // 账号不正确
            return Result.build(null,ResultCodeEnum.ACCOUNT_ERROR);
        }
        //验证密码是否正确
        if(!MD5.encrypt(password).equals(userInfo.getPassword())){
            //密码不正确
            return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
        }
        //判断用户是否被锁定
        if(userInfo.getStatus() == 0){
            //用户锁定
            return  Result.build(null,ResultCodeEnum.ACCOUNT_LOCK_ERROR);
        }
        //登录成功
        //将用户信息放入session域中 目的是后台用户是否登录的状态
        session.setAttribute("user",userInfo);
        //创建一个Map Map中必须设置一个nickName的key 值是用户的昵称
        Map map = new HashMap<>();
        map.put("nickName",userInfo.getNickName());
        map.put("phone",phone);
        return Result.ok(map);
    }
    //登出
    @RequestMapping("/logout")
    public Result logout(HttpSession session){
        //将Session域中的userInfo对象移除
        session.removeAttribute("user");
        return  Result.ok();
    }
}
