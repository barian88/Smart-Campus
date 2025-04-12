// pages/message/message.js
var util = require('../../utils/util')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    event: null,
    eventHandlingComment: null,
    eventId: null,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    wx.showLoading({})
    let eventId = options.eventId
    this.setData({
      eventId: eventId
    })
    wx.request({
      url: getApp().globalData.BASE_URL + '/event/getEventById',
      method: 'GET',
      data: {
        eventId: eventId
      },
      success: res => {
        this.setData({
          event: res.data.data
        })
      }
    })
    let q = setInterval(() => {
      // 如果还没有用户信息，什么也不做
      console.log(getApp().globalData.userInfo)
      if (getApp().globalData.userInfo == '' || getApp().globalData.userInfo == null) {
        ;
      } else {
        // 有了用户信息，取消加载，销毁计时器
        wx.hideLoading()
        clearInterval(q)
      }
    }, 200)
  },
  //获取输入的处理意见
  eventHandlingComment: function (e) {
    this.setData({
      eventHandlingComment: e.detail.value
    })
  },
  // 事件处理完成
  eventComplete: function () {
    wx.showLoading();
    wx.request({
      url: getApp().globalData.BASE_URL + 'event/handleEvent',
      method: "POST",
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {
        eventId: this.data.eventId,
        eventHandlerName: getApp().globalData.userInfo.realName,
        eventHandlerJobId: getApp().globalData.userInfo.schoolJobId,
        eventHandlingComment: this.data.eventHandlingComment,
        eventHandlingTime: util.formatTime(new Date()),
      },
      success: res => {
        wx.hideLoading()
        wx.showToast({
          title: '处理成功！',
          icon: 'success',
          success: () => {
            setTimeout(() => {
              wx.switchTab({
                url: '/pages/index/index',
              })
            }, 1500)
          }
        })
      }

    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {},

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