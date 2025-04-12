// pages/phone/phone.js
const defaultAvatarUrl = 'https://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRna42FI242Lcia07jQodd2FJGIYQfG0LAJGFxM4FbnQP6yfMxBgJ0F3YRqJCJ1aPAK2dQagdusBZg/0'

var countdown = 60;

Page({

  /**
   * 页面的初始数据
   */
  data: {
    phone: '',
    captcha: '',
    realName: '',
    schoolJobId: '',
    sendMessageTip: '发送验证码',
    // 标志是否可以发送信息
    msgBtnAble: true,
    bindInfo: false,
    bindPhone: true,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

  },

  // input双向绑定
  setParam(e) {
    console.log(e)
    let value = e.detail.value;
    let param = e.currentTarget.dataset.param
    this.setData({
      [param]: value
    })
  },

  //发送验证码
  sendcode: function () {
    let that = this;
    let phone = this.data.phone;
    console.log(phone)
    wx.request({
      url: getApp().globalData.BASE_URL + 'sendMessage/phone', //仅为示例，并非真实的接口地址
      header: {
        'Content-Type': 'application/json'
      },
      data: {
        phone
      },
      method: "GET",
      success: res => {
        if (res.data.msg == 'OK') {
          wx.showToast({
            title: '验证码已发送',
            icon: 'none'
          })
          // 开始倒计时
          this.setCountDown()
        } else if (res.data.msg == '手机号不合法') {
          wx.showToast({
            title: '请输入正确的手机号',
            icon: 'none'
          })
        } else {
          wx.showToast({
            title: '发送失败',
            icon: 'none'
          })
        }
      }
    })
  },
  // 验证验证码是否正确
  checkCode: function () {
    var that = this;
    var phone = this.data.phone;
    var captcha = this.data.captcha
    wx.request({
      url: getApp().globalData.BASE_URL + 'sendMessage/checkCaptcha',
      method: "GET",
      header: {
        'Content-Type': 'application/json'
      },
      data: {
        phone,
        captcha,
      },
      success(res) {
        console.log(res)
        // that.goNext()
        if (res.data.data == true) {
          that.goNext()
        } else {
          wx.showToast({
            title: '验证码错误',
            icon: 'none'
          })
        }
      }
    })
  },
  //60s倒计时实现逻辑
  setCountDown() {
    if (countdown == 0) {
      this.setData({
        msgBtnAble: true,
        sendMessageTip: '发送验证码'
      })
      countdown = 60; //60秒过后button上的文字初始化,计时器初始化;
      return;
    } else {
      this.setData({
        msgBtnAble: false,
        sendMessageTip: countdown + "s后重新发送"
      })
      countdown--;
    }
    setTimeout(() => {
      this.setCountDown()
    }, 1000) //每1000毫秒执行一次
  },

  // 绑定用户信息
  bindUserInfo() {
    wx.request({
      url: getApp().globalData.BASE_URL + 'user/register',
      method: 'POST',
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      data: {
        id: getApp().globalData.userInfo.id,
        phone: this.data.phone,
        realName: this.data.realName,
        schoolJobId: this.data.schoolJobId
      },
      success: res => {
        wx.switchTab({
          url: '../index/index'
        })
        // 更新storage中的信息
        let userInfo = getApp().globalData.userInfo
        userInfo.phone = this.data.phone
        userInfo.realName = this.data.realName
        userInfo.schoolJobId = this.data.schoolJobId
        wx.setStorage({
          key: "userInfo",
          data: userInfo
        });
        // 更新全局变量
        getApp().globalData.userInfo = userInfo;
      },
      fail: res => {
        wx.showToast({
          title: '网络出错',
        })
      }
    })
  },
  // 切换页面内容
  goNext() {
    // 如果验证码正确
    this.setData({
      bindPhone: false
    })
  },
  goBindInfo() {
    this.setData({
      bindInfo: true
    })
  },

  onLoad(options) {},

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})