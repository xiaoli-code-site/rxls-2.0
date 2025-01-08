// 英文语言包
export default {
  /**
   * 公共
   */
  common: {
    detail: 'Detail',
    description: 'description', //描述
    close: 'Close', //关闭
    configuration: 'Configuration', //配置
    day: 'Day', //天
    hour: 'Hour', //小时
    minute: 'Minute', //分钟
    currentLocation: 'Current Location: ', //当前位置
    member: 'Member', //会员
    admin: 'Admin', //管理员
    ordinaryUsers: 'OrdinaryUser', //普通用户
    superAdmin: 'SuperAdmin', //超级管理员
    managementSystem: 'Management system', //管理系统
    username: 'Username', //用户名
    password: 'Password', //密码
    zh: 'Chinese', //中文
    en: 'English', //英文
    pleaseCheckInput: 'Please check your input！', //请检查您的输入
    pleaseInput: 'please input', //请输入
    unfold: 'unfold', //展开
    shrink: 'shrink', //收缩
    fail: 'fail', //失败
    all: 'All', //全部
    state: 'State', //状态
    createTime: 'Create Time', //创建时间
    updateTime: 'Update Time', //更新时间
    option: 'Option', //操作
    reset: 'reset', //重置
    search: 'search', //搜索
    normal: 'Normal', //启用
    disable: 'Disable', //禁用
    confirm: 'Confirm', //确认
    cancel: 'Cancel', //取消
    add: 'Add', //添加
    edit: 'Edit', //编辑
    deleteSelected: 'Delete Selected', //批量删除
    exportToData: 'Export Data', //导出数据
    refresh: 'Refresh', //刷新
    id: 'ID', //id
    delete: 'Delete', //删除
    theColumnsThatAreDisplayed: 'The Columns That Are Displayed', //显示的列
    upload: 'Upload', //上传
    selectImage: 'Select image', //选择图片
    uploading: 'uploading...', //上传中
    field: 'field', //字段
    filename: 'filename', //文件名
    customize: 'customize', // 自定义
    language: 'language', //语言
    pages: 'page', //页
    languageSwitching: '语言切换', //语言切换
    theCurrentlySelectedRow: 'currently selected row', //当前选中的行
    theFileNameIsNotEmpty: 'file name is not empty!', //文件名不为空
    theFieldContainsAtLeastOne: 'field contains at least one!', //字符至少包含一个
    contentHeightDisplayAdjustment: 'Content Height display adjustment', //内容长度显示调整
    theFileNameConsistsOfEnglishDigitsChineseCharactersAndUnderscores:
      'file name consists of english、digits、 chinese、 characters and underscores', //文件名由英文数字、中文字符和下划线组成
  },

  /**
   * 顶部导航栏
   */
  header: {
    theme: 'theme', //主题
    themeSwitching: 'Theme switching', //主题切换
    enterFullScreenMode: 'Enter full screen mode', //进入全屏
    fullScreen: 'full screen', //全屏
    logout: 'logout', //退出登录
    language: 'language', //语言
    languageSwitching: 'language switching', //语言切换
    exited: 'exited', //已退出
  },

  /**
   * 日志
   */
  role: {
    roleValueNoEmpty: 'Role value No Empty!', //权限值不为空
    roleNameNoEmpty: 'Role name No Empty!', //角色名称不为空
    roleValueMin: 'The minimum permission value is 1!', //权限值最小为1
    roleValue: 'RoleValue', //权限值
    roleName: 'RoleName', //角色名称
  },

  /**
   * 日志
   */
  oplog: {
    type: 'Type', //类型
    data: 'Data', //数据
    msg: 'Msg', //消息
    code: 'Code', //状态码
    package: 'Package', //包名
    method: 'Method', //方法名
    message: 'Message', //响应信息
    localizedMessage: 'LocalizedMessage', //错误信息信息
    errorInfo: 'Error Info', //错误信息
    query: 'Query', //操作参数
    times: 'Time', //耗时
    nickname: 'Nickname', //操作用户
    error: 'ERROR', //失败
    success: 'SUCCESS', //成功
    address: 'Address', //归属地址
    critical: 'CRITICAL', //核心
    important: 'IMPORTANT', //重要
    general: 'GENERAL', //一般
    user: 'USER', //用户模块
    sys: 'SYSTEM', //系统模块
    secure: 'SECURE', //安全模块
    category: 'Category', //操作类型
    description: 'Message', //操作信息
    model: 'Model', //系统模块
    ip: 'IP', //IP地址
    result: 'Result', //操作结果
    oplog: 'Oplog', //操作日志
  },

  /**
   * 系统信息
   */
  sys: {
    startTime: 'Start Time', //启动时间
    runningTime: 'Running Time', //运行时长
    jvm: 'JVM', //虚拟机
    core: ' CORE', //核心
    logicCPU: 'Logic CPU', //逻辑CPU
    physicalCPU: 'Physical CPU', //物理CPU
    physicalCore: 'Physical Core', //物理核心
    model: 'Model', //型号
    operatingSystem: 'Operating System', //操作系统
    runTime: 'Run time',
    ip: 'IP', //ip地址
    cpu: 'CPU Usage rate', //cpu
    disk: 'Disk Usage rate', //磁盘
    switchboard: 'Switchboard Usage rate', //交换机
    memory: 'Memory Usage rate', //内存
    physicalMemoryCapacity: 'Physical memory capacity', //物理内存容量
    diskStorageCapacity: 'Disk storage capacity', //磁盘存储容量
    exchangeSpaceCapacity: 'Exchange space capacity', //交换空间容量
    systemInformation: 'System Information', //系统信息
    otherInformation: 'Other Information', //其他信息
  },

  /**
   * 用户信息
   */
  user: {
    role: 'Role', //角色
    username: 'Username', //用户名
    pleaseInputUsername: 'Please input your username!', //请输入您的用户名
    sex: 'Sex', //性别
    nickname: 'Nickname', //名称
    girl: 'Girl', //女孩
    boy: 'Boy', //男孩
    phone: 'Phone', //手机
    avatar: 'Avatar', //头像
    roleNoEmpty: 'role no empty!', //角色不为空
    usernameNoEmpty: 'username no empty!', //账号不为空
    nicknameNoEmpty: 'nickname no empty!', //名称不为空
    nicknameFormatError: 'nickname format error!', //名称格式错误
    avatarNoEmpty: 'avatar no empty!', //头像不为空
    phoneNumberFormatError: 'phone number format error!', //手机号格式错误
    phoneNumberNoEmpty: 'phone number no empty!', //手机号不为空
    passwordNoEmpty: 'password no empty!', //密码不为空
    containsOnlyEnglishAndNumbers:
      'Contains only English and numbers, with a minimum length of 5!', //只包含 英文、数字,长度最小为5
    Mustleast6CharactersLettersDigitsUnderscores:
      'mustleast 6 Characters letters digits underscores!', //至少6个字符字母数字下划线
  },

  /**
   * 用户信息
   */
  admin: {
    role: 'Role', //角色
    username: 'Username', //用户名
    pleaseInputUsername: 'Please input your username!', //请输入您的用户名
    sex: 'Sex', //性别
    nickname: 'Nickname', //名称
    girl: 'Girl', //女孩
    boy: 'Boy', //男孩
    phone: 'Phone', //手机
    avatar: 'Avatar', //头像
    roleNoEmpty: 'role no empty!', //角色不为空
    usernameNoEmpty: 'username no empty!', //账号不为空
    nicknameNoEmpty: 'nickname no empty!', //名称不为空
    nicknameFormatError: 'nickname format error!', //名称格式错误
    avatarNoEmpty: 'avatar no empty!', //头像不为空
    phoneNumberFormatError: 'phone number format error!', //手机号格式错误
    phoneNumberNoEmpty: 'phone number no empty!', //手机号不为空
    passwordNoEmpty: 'password no empty!', //密码不为空
    containsOnlyEnglishAndNumbers:
      'Contains only English and numbers, with a minimum length of 5!', //只包含 英文、数字,长度最小为5
    Mustleast6CharactersLettersDigitsUnderscores:
      'mustleast 6 Characters letters digits underscores!', //至少6个字符字母数字下划线
  },

  /**
   * 通用配置
   */
  config: {
    key: 'Key', //键名
    value: 'Value', //键值
    description: 'Description', //描述
  },

  /**
   * 菜单
   */
  menu: {
    task: 'Task Admin', //task
    general: 'General Config', //通用配置
    role: 'Role Admin', //角色管理
    oplog: 'Oplog', //操作日志
    monitor: 'Monitor', //系统监控
    home: 'Home', //首页
    404: '404', //页面不存在
    sysAdmin: 'System Admin', //系统管理
    administrator: 'Administrator', //管理人员
    userAdmin: 'User Admin', //用户管理
    user: 'User Admin', //用户管理
    admin: 'Administrator', //管理人员
    sys: 'System Admin', //系统管理
    onlyOpenCurrentMenu: 'Do you only open the current menu?', //是否只展开当前菜单
  },

  /**
   * 登录页面
   */
  p_login: {
    login: 'Log in', //登录
    verifyCode: 'VerifyCode', //验证码
    slogan: 'Customer centric, efficiency driven!', //口号
    administration: 'Administration', //管理系统
    themeSwitching: 'theme switching', //主题切换
    languageSwitching: 'language switching', //语言切换
    pleaseInputPassword: 'Please input your password!', //请输入您的密码
    pleaseInputVerifyCode: 'Please input your verifyCode!', //请输入您的验证码
    acquisitionFailed: 'Acquisition failed', //获取验证码失败
    obtainVerificationCode: 'Click to obtain verification code', //点击获取验证码
    success: 'login success', //登录成功
    loggingIn: 'logging in...', //登录中
  },

  /**
   * 任务管理页面
   */
  task: {
    testTask:"Test Task",//测试任务
    taskStop: 'Task Stop', //任务暂停
    taskStart: 'Task Start', //任务启动
    fireNow: 'Fire Now', // 立即执行一次
    withNextFireTime: 'WithNext Fire Time', // 不补偿任何误点
    ignoreMisfires: 'Ignore Misfires', // 补偿所有误点
    frequency: 'Frequency', //频率
    cycle: 'Cycle', //周期
    mistakeMode: 'Mistake Mode', //误点模式
    hour: 'Hour', //小时
    minutes: 'Minutes', //分钟
    seconds: 'Seconds', //秒
    pleaseSelectATime: 'please select time', //请选择时间
    normalMode: 'Normal Mode', //普通模式
    cornMode: 'CORN Mode', //Corn模式
    jobCodeNoEmpty: 'job code no empty', //任务编码不为空
    duration: 'Duration', //起止时间
    jobName: 'Job Name', //任务名称
    username: 'Username', //用户名
    expression: 'Expression', //表达式
    corn: 'CORN Mode', //corn表达式
    cornExpression: 'Corn Expression', //corn表达式
    triggerConfig: 'Trigger Config', //触发配置
    groupName: 'Group Name', //分组名称
    jobCode: 'Job Code', //任务编码
    startTime: 'Start Time', //开始时间
    endTime: 'End Time', //结束时间
    unit: 'Unit', //单位
    model: 'Model', //模式
  },

  /**
   * 消息
   */
  message: {
    exportingIrogress: 'exporting in progress...', //导出中
    exportComplete: 'export complete', //导出完成
    unauthorizedOperation: 'unauthorized operation!', //无权操作
    uploadSuccess: 'upload success', //上传成功
    addSuccess: 'add success', //添加成功
    editSuccess: 'edit success', //编辑成功
    committing: 'committing...', //提交中
    youSureProceedThisOperation: 'you Sure proceed yhis operation?', //你确定要执行此操作
    pleaseCheck: 'please check!', //请检查
    quantity: 'quantity', //数量
    deleteSuccess: 'delete success', //删除成功
    error: 'error', //错误
    errorDelete: 'error delete!', //删除失败
    invalidAccounts: 'invalid accounts!', //无效账户
    accountRepeat: 'account repeat!', //账号已存在
    errorEnroll: 'error enroll!', //注册失败
    errorAdd: 'error add!', //添加失败
    uploadMaxSize: 'upload max 5 size!', //上传图片限制5M以内
    uploadType: 'error upload type!', //上传图片类型错误
    errorUpdate: 'error update!', //更新失敗
    errorLogin: 'error login!', //请先登录
    errorAuthorized: 'error authorized!', //授权失败
    errorUser: 'error user!', //用户不存在
    authenticationFailed: 'authentication failed!', //认证失败
    errorCache: 'error cache!', //验证码错误
    systemBusy: 'system busy!', //系统繁忙
    success: 'success', //成功
    fail: 'fail!', //失败
    valueNoEmpty: '值不为空!',
    NetworkAbnormal: 'network abnormal!', //网络异常
    SemanticsWrong: 'semantics wrong!', //语义错误
    ServerAuthenticationFailed: 'server authentication failed!', //服务器身份验证失败
    ServerDeniesAccess: 'server denies access!', //服务器拒绝访问
    RequestAddressIncorrect: 'request address incorrect!', //请求地址不正确
    ServerNoResponding: 'server no responding!', //服务器无响应
    NetworkError: 'network error!', //网络错误
    ok: 'SUCCESS', //成功
    no: 'FAIL', //失败
    codeNoEmpty: 'code no empty!', //code 不为空
    numberQueriesNotEmpty: 'The number of queries is not empty!', //查询个数不为空
    pageNumbersNoEmpty: 'page numbers no empty!', //页码不为空
    illegalPageNumbers: 'illegal page numbers!', //非法页码
    stateNoEmpty: 'state no empty!', //性别不为空
    sexNoEmpty: 'sex no empty!', //性别不为空
    illegalState: 'illegal state!', //非法状态
    illegalId: 'illegal id!', //非法id
    illegalSex: 'illegal sex!', //非法性别
    illegalRole: 'illegal role!', //非法角色
    idNotEmpty: 'id no empty!', //id不为空
    captchaNotEmpty: 'captcha no empty!', //验证码不为空
    roleNoEmpty: 'role no empty!', //角色不为空
    usernameNoEmpty: 'username no empty!', //账号不为空
    nicknameNoEmpty: 'nickname no empty!', //名称不为空
    nicknameFormatError: 'nickname format error!', //名称格式错误
    avatarNoEmpty: 'avatar no empty!', //头像不为空
    phoneNumberFormatError: 'phone number format error!', //手机号格式错误
    phoneNumberNoEmpty: 'phone number no empty!', //手机号不为空
    passwordNoEmpty: 'password no empty!', //密码不为空
    phoneNumberHasBeenUsed: 'phone number has been used!', //手机号已被使用
    errorPassword: 'error password!', //密码错误
    preparingPleaseWait: 'Preparing, please wait...', //准备中,请稍后...
    NetworkReconnection: 'Network Reconnection', //网络重连
  },
}
