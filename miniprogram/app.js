App({
  globalData: {
    userInfo: null,
    id: null,
    needNavigate: false,
    BASE_URL: "https://www.butcher.fun/",
    // BASE_URL: "http://localhost:8003/",
    navigationBarHeight: 0
  },
  // 获取状态栏高度
  getBarHeight() {
    //计算导航栏高度
    const {
      statusBarHeight,
      platform
    } = wx.getSystemInfoSync()
    const {
      top,
      height
    } = wx.getMenuButtonBoundingClientRect()

    // // 状态栏高度
    // wx.setStorageSync('statusBarHeight', statusBarHeight)
    // // 胶囊按钮高度 一般是32 如果获取不到就使用32
    // wx.setStorageSync('menuButtonHeight', height ? height : 32)

    // 判断胶囊按钮信息是否成功获取
    if (top && top !== 0 && height && height !== 0) {
      //获取成功进行计算
      const navigationBarHeight = (top - statusBarHeight) * 2 + height
      // 导航栏高度
      this.globalData.navigationBarHeight = navigationBarHeight
    } else {
      获取失败使用默认的高度
      this.globalData.navigationBarHeight = (platform === 'android' ? 48 : 40)
    }
  },
  checkUserLogin() {
    let userInfo = wx.getStorageSync('userInfo')
    // 没登录就跳转，或者信息不完整
    let needNavigate = (userInfo == '' ||
      userInfo.phone == '' || userInfo.realName == '' || userInfo.schoolJobId == '')
      // 设置全局变量，方便index判断是否需要初始化
    this.globalData.needNavigate = needNavigate
    if (needNavigate) {
      wx.redirectTo({
        url: 'pages/login/login',
      })
    }
    // 登陆过就更新一下信息
    else {
      this.getUserInfo(userInfo.id)
    }
  },
  getUserInfo(id) {
    wx.request({
      url: this.globalData.BASE_URL + '/user/getUserById',
      method: 'GET',
      data: {
        id: id
      },
      success: res => {
        this.globalData.userInfo = res.data.data
        // 修改storage
        wx.setStorage({
          key: "userInfo",
          data: res.data.data
        });
      }
    })
  },
  onLaunch() {
    this.getBarHeight()
    this.checkUserLogin()
  }


})