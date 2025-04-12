package com.ikun.backend.controller;

import com.ikun.backend.entity.Event;
import com.ikun.backend.service.SendMessageService;
import com.ikun.backend.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/sendMessage")
@RestController
public class SendMessageController {

    @Autowired
    SendMessageService sendMessageService;

    @PostMapping("weixin")
    public AjaxResult sendWeixinMessage(@RequestBody Event event, String cameraAreaValue,String cameraNameValue){
       if (event == null)
           return AjaxResult.error();
       else
           return AjaxResult.success(sendMessageService.sendWeixinMessage(event,cameraAreaValue,cameraNameValue));
    }

    @GetMapping("/phone")
    public AjaxResult sendPhoneMessage(@RequestParam("phone") String phone){
        if (phone.equals(""))
            return AjaxResult.error();
        else
            return AjaxResult.success(sendMessageService.sendPhoneMessage(phone));
    }

    @GetMapping("/checkCaptcha")
    public AjaxResult checkCaptcha(@RequestParam("phone") String phone,
                                   @RequestParam("captcha") String captcha){

        if (phone.equals("") || captcha.equals(""))
            return AjaxResult.error();
        return AjaxResult.success(sendMessageService.checkCaptcha(phone, captcha));
    }
}
