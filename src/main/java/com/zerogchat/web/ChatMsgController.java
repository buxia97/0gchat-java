package com.zerogchat.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zerogchat.common.*;
import com.zerogchat.entity.*;
import com.zerogchat.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

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

    @Autowired
    private ChatBanService banService;

    @Autowired
    private ChatChatService chatService;

    @Autowired
    private ChatConfigsService configsService;

    @Autowired
    private RedisTemplate redisTemplate;

    RedisHelp redisHelp =new RedisHelp();
    ResultAll Result = new ResultAll();
    baseFull baseFull = new baseFull();

    /***
     * 发送消息
     */
    @RequestMapping(value = "/sendMsg")
    @ResponseBody
    public String sendMsg(@RequestParam(value = "params", required = false) String  params,
                          HttpServletRequest request) {
        try {
            Long date = System.currentTimeMillis();
            String created = String.valueOf(date).substring(0,10);
            ChatMsg insert = null;
            if (StringUtils.isNotBlank(params)) {
                JSONObject object = JSON.parseObject(params);
                if(object.get("type")==null||object.get("text")==null||object.get("chatid")==null){
                    return Result.getResultJson(0,"请求参数错误！",null);
                }
                Integer type = Integer.parseInt(object.get("type").toString());
                if(type < 0||type > 3){
                    return Result.getResultJson(0,"请求参数错误！",null);
                }
                String  ip = baseFull.getIpAddr(request);
                String isRepeated = redisHelp.getRedis(ip+"_isRepeated",redisTemplate);
                if(isRepeated==null){
                    redisHelp.setRedis(ip+"_isRepeated","1",2,redisTemplate);
                }else{
                    return Result.getResultJson(0,"你的操作太频繁了",null);
                }

                ChatBan ban = new ChatBan();
                ban.setIp(ip);
                List<ChatBan> banList = banService.selectList(ban);
                if(banList.size()>0){
                    return Result.getResultJson(0,"你已被封禁",null);
                }
                insert = object.toJavaObject(ChatMsg.class);
                insert.setIp(ip);

                insert.setCreated(Integer.parseInt(created));
                Integer chatid = insert.getChatid();
                ChatChat chat = chatService.selectByKey(chatid);
                if(chat == null){
                    return Result.getResultJson(0,"聊天室不存在",null);
                }else{
                    if(!chat.getStatus().equals(1)){
                        return Result.getResultJson(0,"聊天室已关闭",null);
                    }
                }
                //违禁词拦截
                String banText = redisHelp.getRedis("banText",redisTemplate);
                if(banText==null){
                    ChatConfigs configs = configsService.selectByKey(1);
                    banText = configs.getBanText();
                    redisHelp.setRedis("banText",configs.getBanText(),86000,redisTemplate);
                }
                String text = insert.getText();
                Integer isForbidden = 0;
                if(banText!=null&&banText.length()>0){
                    if(banText.indexOf(",") != -1){
                        String[] strarray=banText.split(",");
                        for (int i = 0; i < strarray.length; i++){
                            String str = strarray[i];
                            if(text.indexOf(str) != -1){
                                isForbidden = 1;
                            }

                        }
                    }else{
                        if(text.indexOf(banText) != -1){
                            isForbidden = 1;
                        }
                        if(text.equals(banText)){
                            isForbidden = 1;
                        }
                    }
                }
                if(isForbidden.equals(1)){
                    return Result.getResultJson(0,"您的消息包含违禁词",null);
                }

            }else{
                return Result.getResultJson(0,"请求参数错误！",null);
            }
            //查看是否有回复消息
            if(insert.getReply()!=null){
                ChatMsg replyMsg = service.selectByKey(insert.getReply());
                if(replyMsg==null){
                    return Result.getResultJson(0,"回复的消息不存在",null);
                }
            }
            int rows = service.insert(insert);

            //给聊天室添加最新消息时间
            Integer chatid = insert.getChatid();
            ChatChat chat = new ChatChat();
            chat.setId(chatid);
            chat.setPostTime(Integer.parseInt(created));
            chatService.update(chat);
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
     * 发送自定义系统消息
     */
    @RequestMapping(value = "/sendSystemMsg")
    @ResponseBody
    public String sendSystemMsg(@RequestParam(value = "text", required = false) String  text,
                          @RequestParam(value = "chatid", required = false) Integer  chatid,
                          @RequestParam(value = "token", required = false) String  token,
                          HttpServletRequest request) {
        try {
            ChatConfigs configs = configsService.selectByKey(1);
            String oldToken = configs.getToken();
            if(!oldToken.equals(token)){
                return Result.getResultJson(0,"密钥不正确",null);
            }
            ChatChat chat = chatService.selectByKey(chatid);
            if(chat == null){
                return Result.getResultJson(0,"聊天室不存在",null);
            }else{
                if(!chat.getStatus().equals(1)){
                    return Result.getResultJson(0,"聊天室已关闭",null);
                }
            }
            String  ip = baseFull.getIpAddr(request);
            Long date = System.currentTimeMillis();
            String created = String.valueOf(date).substring(0,10);
            ChatMsg msg = new ChatMsg();
            msg.setUserName("系统消息");
            msg.setChatid(chatid);
            msg.setText(text);
            msg.setIp(ip);
            msg.setCreated(Integer.parseInt(created));
            msg.setType(2);
            int rows = service.insert(msg);

            //给聊天室添加最新消息时间
            ChatChat newChat = new ChatChat();
            newChat.setId(chatid);
            newChat.setPostTime(Integer.parseInt(created));
            chatService.update(newChat);
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
     * 删除消息
     */
    @RequestMapping(value = "/msgDelete")
    @ResponseBody
    public String msgDelete(@RequestParam(value = "key", required = false) String  key,
                            @RequestParam(value = "token", required = false) String  token,
                            HttpServletRequest request) {
        try {
            ChatConfigs configs = configsService.selectByKey(1);
            String oldToken = configs.getToken();
            if(!oldToken.equals(token)){
                return Result.getResultJson(0,"密钥不正确",null);
            }

            ChatMsg curMsg = service.selectByKey(key);
            int rows =  service.delete(key);
            //发送系统消息
            String  ip = baseFull.getIpAddr(request);
            Long date = System.currentTimeMillis();
            String created = String.valueOf(date).substring(0,10);
            ChatMsg msg = new ChatMsg();
            msg.setUserName("系统消息");
            msg.setChatid(curMsg.getChatid());
            msg.setText("管理员删除["+curMsg.getUserName()+"]的一条消息");
            msg.setIp(ip);
            msg.setCreated(Integer.parseInt(created));
            msg.setType(2);
            service.insert(msg);
            //给聊天室添加最新消息时间
            ChatChat newChat = new ChatChat();
            newChat.setId(curMsg.getChatid());
            newChat.setPostTime(Integer.parseInt(created));
            chatService.update(newChat);
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
     * 封禁用户
     */
    @RequestMapping(value = "/banUser")
    @ResponseBody
    public String banUser(@RequestParam(value = "ip", required = false) String  ip,
                          @RequestParam(value = "chatid", required = false) Integer  chatid,
                            @RequestParam(value = "token", required = false) String  token,
                          HttpServletRequest request) {
        try {
            ChatConfigs configs = configsService.selectByKey(1);
            String oldToken = configs.getToken();
            if(!oldToken.equals(token)){
                return Result.getResultJson(0,"密钥不正确",null);
            }
            Long date = System.currentTimeMillis();
            String created = String.valueOf(date).substring(0,10);
            ChatBan ban = new ChatBan();
            ban.setIp(ip);
            Integer isBan = banService.total(ban);
            if (isBan > 0){
                return Result.getResultJson(0,"你已封禁该IP",null);
            }
            ban.setCreated(Integer.parseInt(created));
            int rows =  banService.insert(ban);
            //发送系统消息
            ChatChat chat = chatService.selectByKey(chatid);
            if(chat != null){
                String myip = baseFull.getIpAddr(request);
                ChatMsg msg = new ChatMsg();
                msg.setUserName("系统消息");
                msg.setChatid(chatid);
                msg.setText("管理员封禁了IP["+ip+"]的聊天权限");
                msg.setIp(myip);
                msg.setCreated(Integer.parseInt(created));
                msg.setType(2);
                service.insert(msg);
            }

            //给聊天室添加最新消息时间
            ChatChat newChat = new ChatChat();
            newChat.setId(chatid);
            newChat.setPostTime(Integer.parseInt(created));
            chatService.update(newChat);
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
     * 总消息列表
     */
    @RequestMapping(value = "/msgList")
    @ResponseBody
    public String msgList (@RequestParam(value = "searchParams", required = false) String  searchParams,
                            @RequestParam(value = "page"        , required = false, defaultValue = "1") Integer page,
                            @RequestParam(value = "limit"       , required = false, defaultValue = "1000") Integer limit) {
        ChatMsg query = new ChatMsg();
        if (StringUtils.isNotBlank(searchParams)) {
            JSONObject object = JSON.parseObject(searchParams);
            query = object.toJavaObject(ChatMsg.class);
        }
        List jsonList = new ArrayList();
        PageList<ChatMsg> pageList = service.selectPage(query, page, limit);
        List<ChatMsg> list = pageList.getList();
        if(list.size() < 1){

            JSONObject noData = new JSONObject();
            noData.put("code" , 1);
            noData.put("msg"  , "");
            noData.put("data" , new ArrayList());
            noData.put("count", 0);
            return noData.toString();
        }
        for (int i = 0; i < list.size(); i++) {
            Map json = JSONObject.parseObject(JSONObject.toJSONString(list.get(i)), Map.class);
            ChatMsg msg = list.get(i);
            Integer reply = msg.getReply();
            Map replyJSon = new HashMap();
            if (!reply.equals(0)){
                ChatMsg replyMsg = service.selectByKey(reply);
                if(replyMsg==null){
                    replyJSon.put("isDeleted",1);
                    replyJSon.put("text","消息已删除");
                }else{
                    replyJSon.put("isDeleted",0);
                    replyJSon = JSONObject.parseObject(JSONObject.toJSONString(replyMsg), Map.class);
                }
                json.put("replyJSon",replyJSon);
            }

            jsonList.add(json);
        }
        JSONObject response = new JSONObject();
        response.put("code" , 1);
        response.put("msg"  , "");
        response.put("data" , jsonList);
        response.put("count", jsonList.size());
        return response.toString();
    }

    /***
     * 按时间消息列表
     */
    @RequestMapping(value = "/lastMsgs")
    @ResponseBody
    public String lastMsgs (@RequestParam(value = "chatid", required = false) Integer  chatid,
                           @RequestParam(value = "time"        , required = false) Integer time) {
        List jsonList = new ArrayList();
        List<ChatMsg> list = service.selectTime(chatid, time);
        if(list.size() < 1){

            JSONObject noData = new JSONObject();
            noData.put("code" , 1);
            noData.put("msg"  , "");
            noData.put("data" , new ArrayList());
            noData.put("count", 0);
            return noData.toString();
        }
        for (int i = 0; i < list.size(); i++) {
            Map json = JSONObject.parseObject(JSONObject.toJSONString(list.get(i)), Map.class);
            ChatMsg msg = list.get(i);
            Integer reply = msg.getReply();
            Map replyJSon = new HashMap();
            if (!reply.equals(0)){
                ChatMsg replyMsg = service.selectByKey(reply);
                if(replyMsg==null){
                    replyJSon.put("isDeleted",1);
                    replyJSon.put("text","消息已删除");
                }else{
                    replyJSon.put("isDeleted",0);
                    replyJSon = JSONObject.parseObject(JSONObject.toJSONString(replyMsg), Map.class);
                }
                json.put("replyJSon",replyJSon);
            }

            jsonList.add(json);
        }
        JSONObject response = new JSONObject();
        response.put("code" , 1);
        response.put("msg"  , "");
        response.put("data" , jsonList);
        response.put("count", jsonList.size());
        return response.toString();
    }

    /***
     * 导出聊天记录
     *
     */
    @RequestMapping(value = "/chatExcel")
    @ResponseBody
    public void invitationExcel(@RequestParam(value = "chatid" , required = false) Integer chatid, @RequestParam(value = "token", required = false) String  token, HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("聊天记录");
        ChatConfigs configs = configsService.selectByKey(1);
        String oldToken = configs.getToken();
        if(!oldToken.equals(token)){
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment;filename=nodata.xls");
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        }
        ChatChat chat = chatService.selectByKey(chatid);
        if(chat == null){
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment;filename=nodata.xls");
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        }
        ChatMsg query = new ChatMsg();
        query.setChatid(chatid);
        List<ChatMsg> list = service.selectList(query);

        Collections.reverse(list);

        String fileName = "chatExcel-"+chatid + ".xls";//设置要导出的文件的名字
        //新增数据行，并且设置单元格数据

        int rowNum = 1;

        String[] headers = { "ID", "用户名称", "消息内容", "发送时间", "URL地址","IP地址"};
        //headers表示excel表中第一行的表头

        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头

        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        for (ChatMsg msg : list) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(msg.getId());
            row1.createCell(1).setCellValue(msg.getUserName());
            row1.createCell(2).setCellValue(msg.getText());
            row1.createCell(3).setCellValue(msg.getCreated());
            row1.createCell(4).setCellValue(msg.getUrl());
            row1.createCell(5).setCellValue(msg.getIp());
            rowNum++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

}
