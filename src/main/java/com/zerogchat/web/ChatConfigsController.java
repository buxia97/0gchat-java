package com.zerogchat.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zerogchat.entity.*;
import com.zerogchat.common.*;
import com.zerogchat.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 控制层
 * ChatConfigsController
 * @author buxia97
 * @date 2023/09/09
 */
@Controller
@RequestMapping(value = "/chatConfigs")
public class ChatConfigsController {

    @Autowired
    ChatConfigsService service;

    RedisHelp redisHelp =new RedisHelp();
    ResultAll Result = new ResultAll();

    /***
     * 验证密钥
     */
    @RequestMapping(value = "/isToken")
    @ResponseBody
    public String isToken(@RequestParam(value = "token", required = false) String  token) {
        ChatConfigs configs = service.selectByKey(0);
        String oldToken = configs.getToken();
        if(!oldToken.equals(token)){
            return Result.getResultJson(0,"密钥不正确",null);
        }else{
            return Result.getResultJson(1,"欢迎您，管理员！",null);
        }
    }
    /***
     * 配置修改
     * @param params Bean对象JSON字符串
     */
    @RequestMapping(value = "/configsUpdate")
    @ResponseBody
    public String formUpdate(@RequestParam(value = "params", required = false) String  params,
                             @RequestParam(value = "token", required = false) String  token) {
        ChatConfigs update = null;
        if (StringUtils.isNotBlank(params)) {
            JSONObject object = JSON.parseObject(params);
            ChatConfigs configs = service.selectByKey(0);
            String oldToken = configs.getToken();
            if(!oldToken.equals(token)){
                return Result.getResultJson(0,"密钥不正确",null);
            }
            update = object.toJavaObject(ChatConfigs.class);
        }else{
            return Result.getResultJson(0,"请求参数错误！",null);
        }

        int rows = service.update(update);

        JSONObject response = new JSONObject();
        response.put("code" , rows);
        response.put("msg"  , rows > 0 ? "修改成功" : "修改失败");
        return response.toString();
    }
}
