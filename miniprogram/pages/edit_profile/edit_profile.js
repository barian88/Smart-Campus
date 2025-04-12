import randomString from '../../utils/random'

var COS = require('../../utils/cos')
const BUCKET_NAME = 'bucket-1305916869'
const REGION = 'ap-beijing'
// 获取应用实例
var cos = new COS({
  SecretId: 'AKIDCwLZk1iIhx77tV4YrztXxElIxZs9PTDu',
  SecretKey: 'iS9oWY0FG8sJfpmW5VlpfHiy1pOmYL4N',
});

Page({
  data: {
    userInfo: null,
    // 是否修改了信息
    altered: false
  },
  // 选择头像
  onChooseAvatar(e) {
    const {
      avatarUrl
    } = e.detail
    this.setAlter()
    this.uploadPic(avatarUrl)
  },
  setAlter() {
    this.setData({
      altered: true
    })
  },
  // 上传图片
  uploadPic(filePath) {
    let that = this
    let suffix = filePath.substring(filePath.lastIndexOf("."))
    let name = 'userAvatar/' + randomString(10) + suffix;
    cos.postObject({
      Bucket: BUCKET_NAME, //对象储存桶的名称
      Region: REGION, //所属地域
      Key: name,
      FilePath: filePath
    }, function (err, data) {
      if (err) {
        wx.showToast({
          title: '上传失败',
          icon: 'error'
        })
        return;
      } else {
        that.setData({
          [`userInfo.avatar`]: 'https://' + data.Location
        })
      }
    })
  },
  // 双向绑定
  bindInput(e) {
    this.setData({
      [`userInfo.nickname`]: e.detail.value
    })
    this.setAlter()
  },
  onLoad() {
    this.setData({
      userInfo: getApp().globalData.userInfo
    })
  },
  onUnload() {
    // 检测到修改
    if (this.data.altered) {
      // 修改globalData
      getApp().globalData.userInfo = this.data.userInfo
      // 修改storage
      wx.setStorage({
        key: "userInfo",
        data: this.data.userInfo
      });
      // 数据库修改
      wx.request({
        url: getApp().globalData.BASE_URL + 'user/updateUserById',
        method: 'POST',
        header: {
          "Content-Type": "application/json"
        },
        data: {
          id: this.data.userInfo.id,
          avatar: this.data.userInfo.avatar,
          nickname: this.data.userInfo.nickname
        }
      })
    }
  }
})