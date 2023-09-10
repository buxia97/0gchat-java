package com.zerogchat.service;

import java.util.Map;
import java.util.List;
import com.zerogchat.entity.*;
import com.zerogchat.common.PageList;

/**
 * 业务层
 * ChatBanService
 * @author buxia97
 * @date 2023/09/09
 */
public interface ChatBanService {

    /**
     * [新增]
     **/
    int insert(ChatBan chatBan);

    /**
     * [批量新增]
     **/
    int batchInsert(List<ChatBan> list);

    /**
     * [更新]
     **/
    int update(ChatBan chatBan);

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
    ChatBan selectByKey(Object key);

    /**
     * [条件查询]
     **/
    List<ChatBan> selectList (ChatBan chatBan);

    /**
     * [分页条件查询]
     **/
    PageList<ChatBan> selectPage (ChatBan chatBan, Integer page, Integer pageSize);

    /**
     * [总量查询]
     **/
    int total(ChatBan chatBan);
}
