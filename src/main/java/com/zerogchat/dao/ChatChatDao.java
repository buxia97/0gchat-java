package com.zerogchat.dao;

import com.zerogchat.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * dao层接口
 * ChatChatDao
 * @author buxia97
 * @date 2023/09/09
 */
@Mapper
public interface ChatChatDao {

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
    int batchDelete(List<Object> list);

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
    List<ChatChat> selectPage (@Param("chatChat") ChatChat chatChat, @Param("page") Integer page, @Param("pageSize") Integer pageSize);

    /**
     * [总量查询]
     **/
    int total(ChatChat chatChat);
}
