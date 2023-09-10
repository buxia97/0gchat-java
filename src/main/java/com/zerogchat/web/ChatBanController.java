package com.zerogchat.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zerogchat.common.*;
import com.zerogchat.entity.*;
import com.zerogchat.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 控制层
 * ChatBanController
 * @author buxia97
 * @date 2023/09/09
 */
@Controller
@RequestMapping(value = "/chatBan")
public class ChatBanController {

    @Autowired
    ChatBanService service;

    @Autowired
    private ChatChatService chatService;

    @Autowired
    private ChatConfigsService configsService;

    @Autowired
    private RedisTemplate redisTemplate;

    RedisHelp redisHelp =new RedisHelp();
    ResultAll Result = new ResultAll();

    /***
     * 封禁列表
     * @param searchParams Bean对象JSON字符串
     * @param page         页码
     * @param limit        每页显示数量
     */
    @RequestMapping(value = "/banList")
    @ResponseBody
    public String banList (@RequestParam(value = "searchParams", required = false) String  searchParams,
                            @RequestParam(value = "page"        , required = false, defaultValue = "1") Integer page,
                            @RequestParam(value = "limit"       , required = false, defaultValue = "15") Integer limit,
                           @RequestParam(value = "params", required = false) String  token) {
        ChatConfigs configs = configsService.selectByKey(0);
        String oldToken = configs.getToken();
        if(!oldToken.equals(token)){
            return Result.getResultJson(0,"密钥不正确",null);
        }
        ChatBan query = new ChatBan();
        if (StringUtils.isNotBlank(searchParams)) {
            JSONObject object = JSON.parseObject(searchParams);
            query = object.toJavaObject(ChatBan.class);
        }

        PageList<ChatBan> pageList = service.selectPage(query, page, limit);
        JSONObject response = new JSONObject();
        response.put("code" , 0);
        response.put("msg"  , "");
        response.put("data" , null != pageList.getList() ? pageList.getList() : new JSONArray());
        response.put("count", pageList.getTotalCount());
        return response.toString();
    }

    /***
     * 表单删除
     */
    @RequestMapping(value = "/banDelete")
    @ResponseBody
    public String banDelete(@RequestParam(value = "key", required = false) String  key,
                          @RequestParam(value = "params", required = false) String  token) {

        try {
            ChatConfigs configs = configsService.selectByKey(0);
            String oldToken = configs.getToken();
            if(!oldToken.equals(token)){
                return Result.getResultJson(0,"密钥不正确",null);
            }
            int rows =  service.delete(key);
            JSONObject response = new JSONObject();
            response.put("code" , rows);
            response.put("msg"  , rows > 0 ? "操作成功" : "操作失败");
            return response.toString();
        }catch (Exception e){
            e.printStackTrace();
            return Result.getResultJson(0,"程序执行异常，请联系管理员",null);
        }
    }
}
