package com.zerogchat.service;

import java.util.Map;
import java.util.List;
import com.zerogchat.entity.*;
import com.zerogchat.common.PageList;

/**
 * 业务层
 * ChatChatService
 * @author buxia97
 * @date 2023/09/09
 */
public interface ChatChatService {

    /**
     * [新增]
     **/
    int insert(ChatChat chatChat);

    /**
     * [批量新增]
     **/
    int batchInsert(List<ChatChat> list);

    /**
     * [更新]
     **/
    int update(ChatChat chatChat);

    /**
     * [删除]
     **/
    int delete(Object key);

    /**
     * [批量删除]
     **/
    int batchDelete(List<Object> keys);

    /**
     * [主键查询]
     **/
    ChatChat selectByKey(Object key);

    /**
     * [条件查询]
     **/
    List<ChatChat> selectList (ChatChat chatChat);

    /**
     * [分页条件查询]
     **/
    PageList<ChatChat> selectPage (ChatChat chatChat, Integer page, Integer pageSize);

    /**
     * [总量查询]
     **/
    int total(ChatChat chatChat);
}
