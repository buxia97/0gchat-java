package com.zerogchat.dao;

import com.zerogchat.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * dao层接口
 * ChatBanDao
 * @author buxia97
 * @date 2023/09/09
 */
@Mapper
public interface ChatBanDao {

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
    int batchDelete(List<Object> list);

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
    List<ChatBan> selectPage (@Param("chatBan") ChatBan chatBan, @Param("page") Integer page, @Param("pageSize") Integer pageSize);

    /**
     * [总量查询]
     **/
    int total(ChatBan chatBan);
}
