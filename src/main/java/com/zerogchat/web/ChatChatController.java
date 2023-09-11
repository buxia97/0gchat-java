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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private ChatMsgService msgService;

    @Autowired
    private ChatConfigsService configsService;

    @Autowired
    private RedisTemplate redisTemplate;

    RedisHelp redisHelp =new RedisHelp();
    ResultAll Result = new ResultAll();
    /***
     * 添加聊天室
     */
    @RequestMapping(value = "/chatAdd")
    @ResponseBody
    public String chatAdd(@RequestParam(value = "params", required = false) String  params,
                          @RequestParam(value = "params", required = false) String  token) {
        try {
            ChatConfigs configs = configsService.selectByKey(0);
            String oldToken = configs.getToken();
            if(!oldToken.equals(token)){
                return Result.getResultJson(0,"密钥不正确",null);
            }
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
        }catch (Exception e){
            e.printStackTrace();
            return Result.getResultJson(0,"程序执行异常，请联系管理员",null);
        }
    }

    /***
     * 编辑聊天室
     */
    @RequestMapping(value = "/chatUpdate")
    @ResponseBody
    public String chatUpdate(@RequestParam(value = "params", required = false) String  params,
                             @RequestParam(value = "params", required = false) String  token) {
        try {
            ChatConfigs configs = configsService.selectByKey(0);
            String oldToken = configs.getToken();
            if(!oldToken.equals(token)){
                return Result.getResultJson(0,"密钥不正确",null);
            }
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
        }catch (Exception e){
            e.printStackTrace();
            return Result.getResultJson(0,"程序执行异常，请联系管理员",null);
        }
    }

    /***
     * 删除聊天室
     */
    @RequestMapping(value = "/chatDelete")
    @ResponseBody
    public String chatDelete(@RequestParam(value = "key", required = false) String  key,
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
        try {
            ChatChat query = new ChatChat();
            String sqlParams = "null";
            if(limit>50){
                limit = 50;
            }
            Integer total = 0;
            List jsonList = new ArrayList();
            if (StringUtils.isNotBlank(searchParams)) {
                JSONObject object = JSON.parseObject(searchParams);
                query = object.toJavaObject(ChatChat.class);
                Map paramsJson = JSONObject.parseObject(JSONObject.toJSONString(query), Map.class);
                sqlParams = paramsJson.toString();
            }
            total = service.total(query);
            List cacheList = redisHelp.getList("chatList_"+page+"_"+limit+"_"+sqlParams,redisTemplate);
            try {
                if (cacheList.size() > 0) {
                    jsonList = cacheList;
                } else {
                    PageList<ChatChat> pageList = service.selectPage(query, page, limit);
                    List<ChatChat> list = pageList.getList();
                    if(jsonList.size() < 1){
                        JSONObject noData = new JSONObject();
                        noData.put("code" , 1);
                        noData.put("msg"  , "");
                        noData.put("data" , new ArrayList());
                        noData.put("count", 0);
                        return noData.toString();
                    }
                    for (int i = 0; i < list.size(); i++) {
                        Map json = JSONObject.parseObject(JSONObject.toJSONString(list.get(i)), Map.class);
                        ChatChat chat = list.get(i);
                        Integer chatid = chat.getId();
                        //获取最新的消息
                        List<ChatMsg> lastMsg = new ArrayList();
                        ChatMsg msgQuery = new ChatMsg();
                        msgQuery.setChatid(chatid);
                        PageList<ChatMsg> msgList = msgService.selectPage(msgQuery, 1, 1);
                        lastMsg = msgList.getList();
                        json.put("lastMsg" , lastMsg);
                        jsonList.add(json);
                    }
                    redisHelp.delete("chatList_"+page+"_"+limit+"_"+sqlParams,redisTemplate);
                    redisHelp.setList("chatList_"+page+"_"+limit+"_"+sqlParams,jsonList,3,redisTemplate);
                }
            }catch (Exception e){
                e.printStackTrace();
                if(cacheList.size()>0){
                    jsonList = cacheList;
                }
            }
            JSONObject response = new JSONObject();
            response.put("code" , 1);
            response.put("msg"  , "");
            response.put("data" , jsonList);
            response.put("count", jsonList.size());
            response.put("total", total);
            return response.toString();
        }catch (Exception e){
            e.printStackTrace();
            return Result.getResultJson(0,"程序执行异常，请联系管理员",null);
        }
    }
}
