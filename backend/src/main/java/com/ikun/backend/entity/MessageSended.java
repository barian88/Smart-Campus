package com.ikun.backend.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
* @Description: 发送订阅模板信息
* @Date:  2023/3/10 16:56
**/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageSended {

   @JsonProperty("touser")
   String openid;
   @JsonProperty("template_id")
   String templateId;
   String page;
   @JsonProperty("miniprogram_state")
   String miniprogramState;
   String lang;
   Map<String,Map<String,String>> data;
}
