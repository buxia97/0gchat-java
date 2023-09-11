package com.zerogchat.common;

//常用数据处理类

import org.springframework.boot.system.ApplicationHome;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class baseFull {
    //数组去重
    public Object[] threeClear(Object[] arr) {
        List list = new ArrayList();
        for (int i = 0; i < arr.length; i++) {
            if (!list.contains(arr[i])) {
                list.add(arr[i]);
            }
        }
        return list.toArray();
    }

    //获取ip地址
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        // ipAddress = this.getRequest().getRemoteAddr();

        return ipAddress;
    }
    //生成随机英文字符串
    public static String createRandomStr(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            stringBuffer.append(str.charAt(number));
        }
        return stringBuffer.toString();
    }

    //随机数
    protected long generateRandomNumber(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("随机数位数必须大于0");
        }
        return (long) (Math.random() * 9 * Math.pow(10, n - 1)) + (long) Math.pow(10, n - 1);
    }

    //判断是否有敏感代码
    public Integer haveCode(String text) {
        try {
            if (text.indexOf("<script>") != -1) {
                return 1;
            }
            if (text.indexOf("eval(") != -1) {
                return 1;
            }
            if (text.indexOf("<iframe>") != -1) {
                return 1;
            }
            if (text.indexOf("<frame>") != -1) {
                return 1;
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }

    }
    //生成lv等级
    public static Integer getLv(Integer num) {
        Integer lv = 0;
        try {
            if (num < 10) {
                lv = 0;
            } else if (num >= 10 && num < 50) {
                lv = 1;
            } else if (num >= 50 && num < 200) {
                lv = 2;
            } else if (num >= 200 && num < 500) {
                lv = 3;
            } else if (num >= 500 && num < 1000) {
                lv = 4;
            } else if (num >= 1000 && num < 2000) {
                lv = 5;
            } else if (num >= 2000 && num < 5000) {
                lv = 6;
            } else if (num >= 5000) {
                lv = 7;
            }
            return lv;

        } catch (Exception e) {
            return 0;
        }
    }
    public static Integer isVideo(String type){
        if(!type.equals(".mp4")&&!type.equals(".MP4")&&!type.equals(".AVI")&&!type.equals(".avi")&&!type.equals(".MKV")&&!type.equals(".mkv")){
            return 0;
        }else{
            return 1;
        }
    }
    //验证字符串是否违规
    public Integer getForbidden(String forbidden, String text){
        Integer isForbidden = 0;
        if(forbidden!=null&&forbidden.length()>0){
            if(forbidden.indexOf(",") != -1){
                String[] strarray=forbidden.split(",");
                for (int i = 0; i < strarray.length; i++){
                    String str = strarray[i];
                    if(text.indexOf(str) != -1){
                        isForbidden = 1;
                    }

                }
            }else{
                if(text.indexOf(forbidden) != -1){
                    isForbidden = 1;
                }
                if(text.equals(forbidden)){
                    isForbidden = 1;
                }
            }
        }
        return  isForbidden;
    }

}
