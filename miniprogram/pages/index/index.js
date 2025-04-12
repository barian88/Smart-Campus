// pages/index/index.js
var util = require('../../utils/util')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    date: util.formatTime2(new Date()).slice(5, 10),
    weekday: util.getWeek(),
    // 数据是否被初始化
    dataInit: false,
    cameraNum: '',
    eventNum: '',
    waitEventNum: '',
    event: '',
    pageNum: 1,
    pageSize: 1,
    userInfo: '',
    navigationBarHeight: 0,
    // 推送消息授权可用次数
    subscribeCount: 0
  },
  // 订阅消息
  messageSubscribe() {
    wx.requestSubscribeMessage({
      tmplIds: ['zJgeYwOcLt_AXIz8itotKcmDwxlcwXUjNLcDeUv7be0'],
    }).then(res => {
      if (res.errMsg == "requestSubscribeMessage:ok") {
        // 修改展示数据
        this.setData({
          subscribeCount: this.data.subscribeCount + 1
        })
        // todo 添加数据库
        wx.showLoading()
        wx.request({
          url: getApp().globalData.BASE_URL + '/user/updateSubScribeCount',
          method: 'GET',
          data: {
            id: getApp().globalData.userInfo.id,
            number: 1
          },
          success: res => {
            if (res.data.data == 1) {
              wx.hideLoading()
            }
          }
        })
      }
    })
  },
  // 订阅消息可用数量
  getSubscribeCount() {
    this.setData({
      subscribeCount: getApp().globalData.userInfo.subscribeCount
    })
  },
  //获取最近一条告警事件列表（用户负责）
  // 请求参数：页码，页大小，用户id
  getAlarmList: function (e) {
    let param = {
      pageNum: this.data.pageNum,
      pageSize: this.data.pageSize,
      superintendentId: this.data.userInfo.id
    };
    wx.request({
      url: getApp().globalData.BASE_URL + 'event/getEventByPageByCondition',
      method: 'POST',
      data: param,
      success: res => {
        this.setData({
          event: res.data.data.content[0]
        })
      },
    })
  },
  //负责摄像头个数
  getCameraNum: function (e) {
    let param = {
      id: this.data.userInfo.id,
    };
    wx.request({
      url: getApp().globalData.BASE_URL + 'cameraToUser/getCameraNumByUserId',
      method: 'GET',
      data: param,
      success: res => {
        // console.log('摄像头个数', res.data.data)
        this.setData({
          cameraNum: res.data.data
        })
      },
      fail: err => {
        wx.hideLoading()
        wx.showToast({
          title: '出错了',
          icon: 'error'
        })
      }

    })
  },
  //累计处理事件个数
  // 请求参数：页码，页大小，处理者schoolJobId，事件状态（1-完成）
  getEventNum: function (e) {
    let param = {
      pageNum: this.data.pageNum,
      pageSize: this.data.pageSize,
      eventHandlerJobId: this.data.userInfo.schoolJobId,
      eventStatus: "1"
    };
    wx.request({
      url: getApp().globalData.BASE_URL + 'event/getEventByPageByCondition',
      method: 'POST',
      data: param,
      success: res => {
        // console.log('累计处理事件个数', res.data.data.content.length)
        this.setData({
          eventNum: res.data.data.totalSize
        })

      },
    })
  },
  //等待处理事件个数
  // 请求参数：页码，页大小，用户id，事件状态（0-未完成）
  getWaitEventNum: function (e) {
    let param = {
      pageNum: this.data.pageNum,
      pageSize: this.data.pageSize,
      superintendentId: this.data.userInfo.id,
      eventStatus: '0'
    };
    wx.request({
      url: getApp().globalData.BASE_URL + 'event/getEventByPageByCondition',
      method: 'POST',
      data: param,
      success: res => {
        this.setData({
          waitEventNum: res.data.data.totalSize
        })

      },
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    // 如果需要跳转到login，退出
    if (getApp().globalData.needNavigate) {
      return
    }
    wx.showLoading({})
    setTimeout(() => {
      this.setData({
        userInfo: getApp().globalData.userInfo,
        navigationBarHeight: getApp().globalData.navigationBarHeight,
        dataInit: true
      });
      this.getSubscribeCount();
      this.getAlarmList();
      this.getEventNum();
      this.getWaitEventNum();
      this.getCameraNum();
      wx.hideLoading();
    }, 500)

  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {
    if (this.data.dataInit === false) {
      this.onLoad()
    }
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
    this.setData({
        alarmList: [],
        pageNum: 1
      }),
      this.getAlarmList()
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {
    if (!this.data.hasMoreEvents) {
      wx.showToast({
        title: '没有更多数据了',
        icon: 'none'
      })
      return
    }
    this.setData({
      pageNum: this.data.pageNum + 1
    })
    this.getAlarmList()
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