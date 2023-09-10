package com.zerogchat.entity;

import java.io.Serializable;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * ChatConfigs
 * @author buxia97 2023-09-09
 */
@Data
public class ChatConfigs implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id  
     */
    private Integer Id;

    /**
     * banText  违禁词列表，英文逗号分割
     */
    private String banText;

    /**
     * rootName  管理员名称
     */
    private String rootName;

    /**
     * logo  管理员or站点logo
     */
    private String logo;

    /**
     * website  官方地址
     */
    private String website;

    /**
     * website  密钥
     */
    private String token;

}