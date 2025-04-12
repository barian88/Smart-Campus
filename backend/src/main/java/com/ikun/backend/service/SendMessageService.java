package com.ikun.backend.service;

import com.ikun.backend.entity.Event;
import com.ikun.backend.utils.AjaxResult;

import java.util.Map;

public interface SendMessageService {
    /**
    * @Description:向微信用户发送订阅信息
    * @Date:  2023/3/10 17:01
    **/
    Map<String,Integer> sendWeixinMessage(Event event,String cameraAreaValue,String cameraNameValue);

    /**
     * @Description: 登录注册时发送手机验证码
     * @Date:  2023/3/17 17:08
     **/
    String sendPhoneMessage(String phone);

    /**
    * @Description: 检查手机验证码是否正确
    * @Date:  2023/3/20 8:56
    **/
    boolean checkCaptcha(String phone, String captcha);
}
