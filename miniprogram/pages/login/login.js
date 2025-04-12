// pages/login/login.js
const defaultAvatarUrl = 'https://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRna42FI242Lcia07jQodd2FJGIYQfG0LAJGFxM4FbnQP6yfMxBgJ0F3YRqJCJ1aPAK2dQagdusBZg/0'

const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    //判断小程序的API，回调，参数，组件等是否在当前版本可用。
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    openid: null,
    sessionkey: null,
    token: null,
    phonenumber: null
  },

  // 授权登录
  login() {
    // 获取临时code
    wx.showLoading()
    wx.login({
      success: res => {
        // console.log(res)
        wx.request({
          url: getApp().globalData.BASE_URL + 'user/weixinUserLogin',
          method: 'POST',
          header: {
            "Content-Type": "application/x-www-form-urlencoded" //用于post
          },
          data: {
            code: res.code,
          },
          success: res => {
            if (res.data.data == null || res.data.data == '') {
              wx.showToast({
                title: '出错了',
                icon: 'error'
              })
              return
            }
            // console.log(res)
            let userInfo = res.data.data
            // 更新storage信息
            wx.setStorage({
              key: "userInfo",
              data: userInfo
            });
            // 设置全局变量
            getApp().globalData.userInfo = userInfo;
            // 修改needNavigate为false
            getApp().globalData.needNavigate = false
            wx.hideLoading()
            // 没有绑定手机
            if (userInfo.phone == null || userInfo.phone == '' ||
              userInfo.realName == null || userInfo.realName == '' ||
              userInfo.schoolJobId == null || userInfo.schoolJobId == '') {
              wx.navigateTo({
                url: '/pages/register/register'
              });
            }
            // 用户信息完整，直接跳转到首页 
            else {
              wx.switchTab({
                url: '/pages/index/index',
              });
            }
          }
        })
      }
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    // 禁止用户返回
    wx.hideHomeButton();
  },
})