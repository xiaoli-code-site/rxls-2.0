import {
  HomeOutlined,
  ControlOutlined,
  UserDeleteOutlined,
  UserOutlined,
  AccountBookOutlined,
  GroupOutlined,
  ProfileOutlined,
  ColumnHeightOutlined,
  CodeOutlined,
  FundProjectionScreenOutlined,
  TeamOutlined
} from '@ant-design/icons-vue'
import Icons from '@/components/common/Icons.vue'

// 字体图标
const iconsHandler = (app: any) => {
  app.component('Icons', Icons)
  app.component('TeamOutlined', TeamOutlined)
  app.component('FundProjectionScreenOutlined', FundProjectionScreenOutlined)
  app.component('CodeOutlined', CodeOutlined)
  app.component('HomeOutlined', HomeOutlined)
  app.component('ControlOutlined', ControlOutlined)
  app.component('UserDeleteOutlined', UserDeleteOutlined)
  app.component('UserOutlined', UserOutlined)
  app.component('AccountBookOutlined', AccountBookOutlined)
  app.component('GroupOutlined', GroupOutlined)
  app.component('ProfileOutlined', ProfileOutlined)
  app.component('ColumnHeightOutlined', ColumnHeightOutlined)

  return app
}

export { iconsHandler }
