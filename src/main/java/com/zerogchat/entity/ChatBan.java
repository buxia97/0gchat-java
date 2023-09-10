package com.zerogchat.entity;

import java.io.Serializable;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * ChatBan
 * @author buxia97 2023-09-09
 */
@Data
public class ChatBan implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id  
     */
    private Integer Id;

    /**
     * ip  IP地址
     */
    private String ip;

    /**
     * created  封禁时间
     */
    private Integer created;
}