package com.ikun.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    int id;
    String userId;
    String tempUserId;
    String openid;
    String realName;
    String phone;
    String schoolJobId;
    String nickname;
    String avatar;
    String ipAddr;
    int roleId;
    int subscribeCount;
}
