package com.zerogchat.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zerogchat.entity.*;
import com.zerogchat.common.ApiResult;
import com.zerogchat.common.PageList;
import com.zerogchat.common.ResultCode;
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
 * ChatChatController
 * @author buxia97
 * @date 2023/09/09
 */
@Controller
@RequestMapping(value = "/chatChat")
public class ChatChatController {

    @Autowired
    ChatChatService service;


    /***
     * 添加聊天室
     */
    @RequestMapping(value = "/chatAdd")
    @ResponseBody
    public String chatAdd(@RequestParam(value = "params", required = false) String  params,
                          @RequestParam(value = "params", required = false) String  token) {
        ChatChat insert = null;
        if (StringUtils.isNotBlank(params)) {
            JSONObject object = JSON.parseObject(params);
            insert = object.toJavaObject(ChatChat.class);
        }

        int rows = service.insert(insert);

        JSONObject response = new JSONObject();
        response.put("code" , rows);
        response.put("msg"  , rows > 0 ? "添加成功" : "添加失败");
        return response.toString();
    }

    /***
     * 编辑聊天室
     */
    @RequestMapping(value = "/chatUpdate")
    @ResponseBody
    public String chatUpdate(@RequestParam(value = "params", required = false) String  params) {
        ChatChat update = null;
        if (StringUtils.isNotBlank(params)) {
            JSONObject object = JSON.parseObject(params);
            update = object.toJavaObject(ChatChat.class);
        }

        int rows = service.update(update);

        JSONObject response = new JSONObject();
        response.put("code" , rows);
        response.put("msg"  , rows > 0 ? "修改成功" : "修改失败");
        return response.toString();
    }

    /***
     * 删除聊天室
     */
    @RequestMapping(value = "/chatDelete")
    @ResponseBody
    public int chatDelete(@RequestParam(value = "key", required = false) String  key) {

        return service.delete(key);
    }

    /***
     * 聊天室列表
     * @param searchParams Bean对象JSON字符串
     * @param page         页码
     * @param limit        每页显示数量
     */
    @RequestMapping(value = "/chatList")
    @ResponseBody
    public String chatList (@RequestParam(value = "searchParams", required = false) String  searchParams,
                            @RequestParam(value = "page"        , required = false, defaultValue = "1") Integer page,
                            @RequestParam(value = "limit"       , required = false, defaultValue = "15") Integer limit) {
        ChatChat query = new ChatChat();
        if (StringUtils.isNotBlank(searchParams)) {
            JSONObject object = JSON.parseObject(searchParams);
            query = object.toJavaObject(ChatChat.class);
        }

        PageList<ChatChat> pageList = service.selectPage(query, page, limit);
        JSONObject response = new JSONObject();
        response.put("code" , 0);
        response.put("msg"  , "");
        response.put("data" , null != pageList.getList() ? pageList.getList() : new JSONArray());
        response.put("count", pageList.getTotalCount());
        return response.toString();
    }
}
