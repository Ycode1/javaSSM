package com.guigu.interceptor;

import com.guigu.entity.UserInfo;
import com.guigu.result.Result;
import com.guigu.result.ResultCodeEnum;
import com.guigu.util.WebUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取Session域中的UserInfo对象
        UserInfo userInfo =(UserInfo) request.getSession().getAttribute("user");
        if(null == userInfo){
            //证明还没有登录
            Result<String> result = Result.build("还没有登录", ResultCodeEnum.LOGIN_AUTH);
           //将result响应到前端
            WebUtil.writeJSON(response,result);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
