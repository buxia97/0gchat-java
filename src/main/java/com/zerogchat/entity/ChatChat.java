package com.zerogchat.entity;

import java.io.Serializable;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * ChatChat
 * @author buxia97 2023-09-09
 */
@Data
public class ChatChat implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id  
     */
    private Integer Id;

    /**
     * name  聊天室名称
     */
    private String name;

    /**
     * intro  聊天室简介
     */
    private String intro;

    /**
     * pic  聊天室图标
     */
    private String pic;

    /**
     * status  聊天室状态（0关闭，1开启）
     */
    private Integer status;

    /**
     * 最新消息时间
     */
    private Integer postTime;




}