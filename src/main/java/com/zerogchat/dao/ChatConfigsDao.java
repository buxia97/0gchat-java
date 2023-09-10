package com.zerogchat.dao;

import com.zerogchat.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * dao层接口
 * ChatConfigsDao
 * @author buxia97
 * @date 2023/09/09
 */
@Mapper
public interface ChatConfigsDao {

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
    int batchDelete(List<Object> list);

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
    List<ChatConfigs> selectPage (@Param("chatConfigs") ChatConfigs chatConfigs, @Param("page") Integer page, @Param("pageSize") Integer pageSize);

    /**
     * [总量查询]
     **/
    int total(ChatConfigs chatConfigs);
}
