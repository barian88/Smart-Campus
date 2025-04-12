// pages/search/search.js
var util = require('../../utils/util')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    alarmList: [],
    pageNum: 1,
    pageSize: 6,
    hasMoreEvents: true,
    showEventDetail: false,
    event: null,
    eventHandlingTime: '',
    eventHandlerName: null,
    eventHandlingComment: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.getAlarmList()
  },
  // 获取报警事件列表（用户自己负责）
  // 请求参数：页码，页大小，负责人id（用户id）
  getAlarmList: function (e) {
    if (getApp().globalData.userInfo == null) {
      return
    }
    wx.showLoading()
    let param = {
      pageNum: this.data.pageNum,
      pageSize: this.data.pageSize,
      superintendentId: getApp().globalData.userInfo.id
    };
    wx.request({
      url: getApp().globalData.BASE_URL + 'event/getEventByPageByCondition',
      method: 'POST',
      data: param,
      success: res => {
        console.log(res.data.data.content)
        let alarmList = this.data.alarmList.concat(res.data.data.content)
        this.setData({
          alarmList: alarmList
        })
        if (res.data.data.totalPages == this.data.pageNum) {
          wx.hideLoading()
          this.setData({
            hasMoreEvents: false
          })
          return
        }
        wx.hideLoading()
      },
      fail: err => {
        console.log(err)
        wx.hideLoading()
        wx.showToast({
          title: '出错了',
          icon: 'error'
        })
      }

    })
  },
  openEventDetail: function (e) {
    if (this.data.showEventDetail) {
      return
    }
    let event = e.currentTarget.dataset.item
    this.setData({
      event: event,
      showEventDetail: true
    })
  },
  //获取输入的处理意见
  eventHandlingComment: function (e) {
    // console.log(e.detail.value)
    this.setData({
      eventHandlingComment: e.detail.value
    })
  },
  closeEventDetail() {
    this.setData({
      showEventDetail: false
    })
  },
  // 事件处理完成
  complete: function () {
    wx.request({
      url: getApp().globalData.BASE_URL + 'event/handleEvent',
      method: "POST",
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {
        eventId: this.data.event.eventId,
        eventHandlerName: getApp().globalData.userInfo.realName,
        eventHandlerJobId: getApp().globalData.userInfo.schoolJobId,
        eventHandlingComment: this.data.eventHandlingComment,
        eventHandlingTime: util.formatTime(new Date())
      },
      success: () => {
        // 重新加载指定事件
        let index = (this.data.alarmList || []).findIndex(
          (item) => item.eventId === this.data.event.eventId)
        let statusParam = 'alarmList[' + index + '].eventStatus'
        let commentParam = 'alarmList[' + index + '].eventHandlingComment'
        this.setData({
          [statusParam]: true,
          [commentParam]: this.data.eventHandlingComment
        })
        // 显示提示
        wx.showToast({
          title: '处理成功',
          icon: 'success'
        })
        this.closeEventDetail()
      },
      fail(res) {
        wx.showToast({
          title: '网络异常',
          icon: 'none'
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
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})