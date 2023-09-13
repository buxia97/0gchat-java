package com.zerogchat.entity;

import java.io.Serializable;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * ChatMsg
 * @author buxia97 2023-09-09
 */
@Data
public class ChatMsg implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id  
     */
    private Integer Id;

    /**
     * userName  用户名称
     */
    private String userName;

    /**
     * ip  IP地址
     */
    private String ip;

    /**
     * type  类型，0图文消息，1加入通知，2系统消息，3卡片消息
     */
    private Integer type;

    /**
     * text  消息内容
     */
    private String text;

    /**
     * url  网址
     */
    private String url;

    /**
     * created  创建时间
     */
    private Integer created;

    /**
     * 所属聊天室
     */
    private Integer chatid;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户标识
     */
    private String userKey;

    /**
     * 回复消息ID
     */
    private Integer reply;


}