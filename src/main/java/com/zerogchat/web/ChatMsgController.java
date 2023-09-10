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
 * ChatMsgController
 * @author buxia97
 * @date 2023/09/09
 */
@Controller
@RequestMapping(value = "/chatMsg")
public class ChatMsgController {

    @Autowired
    ChatMsgService service;


    /***
     * 发送消息
     */
    @RequestMapping(value = "/sendMsg")
    @ResponseBody
    public String sendMsg(@RequestParam(value = "params", required = false) String  params) {
        ChatMsg insert = null;
        if (StringUtils.isNotBlank(params)) {
            JSONObject object = JSON.parseObject(params);
            insert = object.toJavaObject(ChatMsg.class);
        }

        int rows = service.insert(insert);

        JSONObject response = new JSONObject();
        response.put("code" , rows);
        response.put("msg"  , rows > 0 ? "添加成功" : "添加失败");
        return response.toString();
    }

    /***
     * 删除消息
     */
    @RequestMapping(value = "/msgDelete")
    @ResponseBody
    public int msgDelete(@RequestParam(value = "key", required = false) String  key) {

        return service.delete(key);
    }

    /***
     * 消息列表
     * @param searchParams Bean对象JSON字符串
     * @param page         页码
     * @param limit        每页显示数量
     */
    @RequestMapping(value = "/msgList")
    @ResponseBody
    public String msgList (@RequestParam(value = "searchParams", required = false) String  searchParams,
                            @RequestParam(value = "page"        , required = false, defaultValue = "1") Integer page,
                            @RequestParam(value = "limit"       , required = false, defaultValue = "15") Integer limit) {
        ChatMsg query = new ChatMsg();
        if (StringUtils.isNotBlank(searchParams)) {
            JSONObject object = JSON.parseObject(searchParams);
            query = object.toJavaObject(ChatMsg.class);
        }

        PageList<ChatMsg> pageList = service.selectPage(query, page, limit);
        JSONObject response = new JSONObject();
        response.put("code" , 0);
        response.put("msg"  , "");
        response.put("data" , null != pageList.getList() ? pageList.getList() : new JSONArray());
        response.put("count", pageList.getTotalCount());
        return response.toString();
    }
}
