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
  TeamOutlined,
  DashboardOutlined,
  ShoppingOutlined,
  ProjectOutlined,
  ShopOutlined,
  FundViewOutlined,
  NotificationOutlined,
  ShareAltOutlined,
  ToolOutlined,
  UngroupOutlined,
} from "@ant-design/icons-vue";
import Icons from "@/components/common/Icons.vue";

// 字体图标
const iconsHandler = (app: any) => {
  app.component("UngroupOutlined", UngroupOutlined);
  app.component("ToolOutlined", ToolOutlined);
  app.component("Icons", Icons);
  app.component("ShareAltOutlined", ShareAltOutlined);
  app.component("FundViewOutlined", FundViewOutlined);
  app.component("NotificationOutlined", NotificationOutlined);
  app.component("ShopOutlined", ShopOutlined);
  app.component("ProjectOutlined", ProjectOutlined);
  app.component("ShoppingOutlined", ShoppingOutlined);
  app.component("DashboardOutlined", DashboardOutlined);
  app.component("TeamOutlined", TeamOutlined);
  app.component("FundProjectionScreenOutlined", FundProjectionScreenOutlined);
  app.component("CodeOutlined", CodeOutlined);
  app.component("HomeOutlined", HomeOutlined);
  app.component("ControlOutlined", ControlOutlined);
  app.component("UserDeleteOutlined", UserDeleteOutlined);
  app.component("UserOutlined", UserOutlined);
  app.component("AccountBookOutlined", AccountBookOutlined);
  app.component("GroupOutlined", GroupOutlined);
  app.component("ProfileOutlined", ProfileOutlined);
  app.component("ColumnHeightOutlined", ColumnHeightOutlined);

  return app;
};

export { iconsHandler };
