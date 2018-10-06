package com.filter;

import com.alibaba.fastjson.JSONObject;
import com.jwt.JwtToken;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;


/**
 *
 *
 *
 *
 */
@Component
public class AccessFilter extends ZuulFilter {

    @Value("${gate.ignore.startWith}")
    private String startWith;

    @Autowired
    private RedisUtil redisUtil;


    //过滤请求在路由之前进行
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        final String requestUri = request.getRequestURI().toString();

        // 不进行拦截的地址
        if (isStartWith(requestUri)) {
            return null;
        }

        String value =  request.getHeader("Authorization");
        if((value!=null)&&(value.length()>0)) {
            String redisKey = JwtToken.parseJWT(value);
            if((redisKey!=null)&&(redisKey.length()>0)) {
                Boolean status = redisUtil.hasKey(redisKey);
                if(status.equals(true)){
                    redisUtil.updateExpire(redisKey,120);
                    System.out.println("更新key的过期时间");
                } else if (status.equals(false)){
                    ctx.setSendZuulResponse(false);//过滤该请求
                    ctx.getResponse().setCharacterEncoding("UTF-8");
                    ctx.getResponse().setContentType("application/json;charset=UTF-8");
                    PrintWriter out = null ;
                    System.out.println("会话已超时");
                    try {
                        JSONObject res = new JSONObject();
                        res.put("sessionStatus", "timeout");
                        out = ctx.getResponse().getWriter();
                        out.append(res.toString());
                        return null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }
        } else {
            ctx.setSendZuulResponse(false);//过滤该请求
            ctx.getResponse().setCharacterEncoding("UTF-8");
            ctx.getResponse().setContentType("application/json;charset=UTF-8");
            PrintWriter out1 = null ;
            System.out.println("你还没有登陆");
            try {
                JSONObject res = new JSONObject();
                res.put("sessionStatus", "nologin");
                out1 = ctx.getResponse().getWriter();
                out1.append(res.toString());
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * URI是否以什么打头
     *
     */
    private boolean isStartWith(String requestUri) {
        boolean flag = false;
        for (String s : startWith.split(",")) {
            if (requestUri.startsWith(s)) {
                return true;
            }
        }
        return flag;
    }
}
