package com.zerogchat.service;

import java.util.Map;
import java.util.List;
import com.zerogchat.entity.*;
import com.zerogchat.common.PageList;

/**
 * 业务层
 * ChatMsgService
 * @author buxia97
 * @date 2023/09/09
 */
public interface ChatMsgService {

    /**
     * [新增]
     **/
    int insert(ChatMsg chatMsg);

    /**
     * [批量新增]
     **/
    int batchInsert(List<ChatMsg> list);

    /**
     * [更新]
     **/
    int update(ChatMsg chatMsg);

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
    ChatMsg selectByKey(Object key);

    /**
     * [条件查询]
     **/
    List<ChatMsg> selectList (ChatMsg chatMsg);

    /**
     * [时间条件查询]
     **/
    List<ChatMsg> selectTime (Integer chatid,Integer time);

    /**
     * [分页条件查询]
     **/
    PageList<ChatMsg> selectPage (ChatMsg chatMsg, Integer page, Integer pageSize);

    /**
     * [总量查询]
     **/
    int total(ChatMsg chatMsg);
}
