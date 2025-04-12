const app = getApp()
Page({
  data: {
    userInfo: null,
    nickname: '',
    avatarUrl: ''
  },
  editProfile() {
    wx.navigateTo({
      url: '../../pages/edit_profile/edit_profile',
    })
  },
  onShow() {
    this.setData({
      userInfo: getApp().globalData.userInfo
    })
  },







})