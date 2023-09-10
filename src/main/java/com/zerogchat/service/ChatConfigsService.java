package com.zerogchat.service;

import java.util.Map;
import java.util.List;
import com.zerogchat.entity.*;
import com.zerogchat.common.PageList;

/**
 * 业务层
 * ChatConfigsService
 * @author buxia97
 * @date 2023/09/09
 */
public interface ChatConfigsService {

    /**
     * [新增]
     **/
    int insert(ChatConfigs chatConfigs);

    /**
     * [批量新增]
     **/
    int batchInsert(List<ChatConfigs> list);

    /**
     * [更新]
     **/
    int update(ChatConfigs chatConfigs);

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
    ChatConfigs selectByKey(Object key);

    /**
     * [条件查询]
     **/
    List<ChatConfigs> selectList (ChatConfigs chatConfigs);

    /**
     * [分页条件查询]
     **/
    PageList<ChatConfigs> selectPage (ChatConfigs chatConfigs, Integer page, Integer pageSize);

    /**
     * [总量查询]
     **/
    int total(ChatConfigs chatConfigs);
}
