// vite.config.ts
import { defineConfig } from "file:///D:/ProjectCooperation/rx-template/manager-project/node_modules/vite/dist/node/index.js";
import vue from "file:///D:/ProjectCooperation/rx-template/manager-project/node_modules/@vitejs/plugin-vue/dist/index.mjs";
import { resolve } from "path";
import Components from "file:///D:/ProjectCooperation/rx-template/manager-project/node_modules/unplugin-vue-components/dist/vite.js";
import { AntDesignVueResolver } from "file:///D:/ProjectCooperation/rx-template/manager-project/node_modules/unplugin-vue-components/dist/resolvers.js";
var __vite_injected_original_dirname = "D:\\ProjectCooperation\\rx-template\\manager-project";
var vite_config_default = defineConfig({
  // server: {
  //   host: '0.0.0.0', // 允许从任意IP地址访问
  //   port: 9090, // 设置你想要的端口号
  // },
  plugins: [
    vue(),
    Components({
      resolvers: [
        AntDesignVueResolver({
          importStyle: false
          // css in js
        })
      ]
    })
  ],
  resolve: {
    // 设置文件./src路径为 @
    alias: [
      {
        find: "@",
        replacement: resolve(__vite_injected_original_dirname, "./src")
      }
    ]
  },
  css: {
    preprocessorOptions: {
      scss: {
        api: "modern-compiler"
        // or 'modern'
      }
    }
  }
});
export {
  vite_config_default as default
};
//# sourceMappingURL=data:application/json;base64,ewogICJ2ZXJzaW9uIjogMywKICAic291cmNlcyI6IFsidml0ZS5jb25maWcudHMiXSwKICAic291cmNlc0NvbnRlbnQiOiBbImNvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9kaXJuYW1lID0gXCJEOlxcXFxQcm9qZWN0Q29vcGVyYXRpb25cXFxccngtdGVtcGxhdGVcXFxcbWFuYWdlci1wcm9qZWN0XCI7Y29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2ZpbGVuYW1lID0gXCJEOlxcXFxQcm9qZWN0Q29vcGVyYXRpb25cXFxccngtdGVtcGxhdGVcXFxcbWFuYWdlci1wcm9qZWN0XFxcXHZpdGUuY29uZmlnLnRzXCI7Y29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2ltcG9ydF9tZXRhX3VybCA9IFwiZmlsZTovLy9EOi9Qcm9qZWN0Q29vcGVyYXRpb24vcngtdGVtcGxhdGUvbWFuYWdlci1wcm9qZWN0L3ZpdGUuY29uZmlnLnRzXCI7aW1wb3J0IHsgZGVmaW5lQ29uZmlnIH0gZnJvbSAndml0ZSdcclxuaW1wb3J0IHZ1ZSBmcm9tICdAdml0ZWpzL3BsdWdpbi12dWUnXHJcbmltcG9ydCB7IHJlc29sdmUgfSBmcm9tICdwYXRoJ1xyXG5pbXBvcnQgQ29tcG9uZW50cyBmcm9tICd1bnBsdWdpbi12dWUtY29tcG9uZW50cy92aXRlJ1xyXG5pbXBvcnQgeyBBbnREZXNpZ25WdWVSZXNvbHZlciB9IGZyb20gJ3VucGx1Z2luLXZ1ZS1jb21wb25lbnRzL3Jlc29sdmVycydcclxuXHJcbi8vIGh0dHBzOi8vdml0ZWpzLmRldi9jb25maWcvXHJcbmV4cG9ydCBkZWZhdWx0IGRlZmluZUNvbmZpZyh7XHJcbiAgLy8gc2VydmVyOiB7XHJcbiAgLy8gICBob3N0OiAnMC4wLjAuMCcsIC8vIFx1NTE0MVx1OEJCOFx1NEVDRVx1NEVGQlx1NjEwRklQXHU1NzMwXHU1NzQwXHU4QkJGXHU5NUVFXHJcbiAgLy8gICBwb3J0OiA5MDkwLCAvLyBcdThCQkVcdTdGNkVcdTRGNjBcdTYwRjNcdTg5ODFcdTc2ODRcdTdBRUZcdTUzRTNcdTUzRjdcclxuICAvLyB9LFxyXG4gIHBsdWdpbnM6IFtcclxuICAgIHZ1ZSgpLFxyXG4gICAgQ29tcG9uZW50cyh7XHJcbiAgICAgIHJlc29sdmVyczogW1xyXG4gICAgICAgIEFudERlc2lnblZ1ZVJlc29sdmVyKHtcclxuICAgICAgICAgIGltcG9ydFN0eWxlOiBmYWxzZSwgLy8gY3NzIGluIGpzXHJcbiAgICAgICAgfSksXHJcbiAgICAgIF0sXHJcbiAgICB9KSxcclxuICBdLFxyXG4gIHJlc29sdmU6IHtcclxuICAgIC8vIFx1OEJCRVx1N0Y2RVx1NjU4N1x1NEVGNi4vc3JjXHU4REVGXHU1Rjg0XHU0RTNBIEBcclxuICAgIGFsaWFzOiBbXHJcbiAgICAgIHtcclxuICAgICAgICBmaW5kOiAnQCcsXHJcbiAgICAgICAgcmVwbGFjZW1lbnQ6IHJlc29sdmUoX19kaXJuYW1lLCAnLi9zcmMnKSxcclxuICAgICAgfSxcclxuICAgIF0sXHJcbiAgfSxcclxuICBjc3M6IHtcclxuICAgIHByZXByb2Nlc3Nvck9wdGlvbnM6IHtcclxuICAgICAgc2Nzczoge1xyXG4gICAgICAgIGFwaTogJ21vZGVybi1jb21waWxlcicsIC8vIG9yICdtb2Rlcm4nXHJcbiAgICAgIH0sXHJcbiAgICB9LFxyXG4gIH0sXHJcbn0pXHJcbiJdLAogICJtYXBwaW5ncyI6ICI7QUFBNlUsU0FBUyxvQkFBb0I7QUFDMVcsT0FBTyxTQUFTO0FBQ2hCLFNBQVMsZUFBZTtBQUN4QixPQUFPLGdCQUFnQjtBQUN2QixTQUFTLDRCQUE0QjtBQUpyQyxJQUFNLG1DQUFtQztBQU96QyxJQUFPLHNCQUFRLGFBQWE7QUFBQTtBQUFBO0FBQUE7QUFBQTtBQUFBLEVBSzFCLFNBQVM7QUFBQSxJQUNQLElBQUk7QUFBQSxJQUNKLFdBQVc7QUFBQSxNQUNULFdBQVc7QUFBQSxRQUNULHFCQUFxQjtBQUFBLFVBQ25CLGFBQWE7QUFBQTtBQUFBLFFBQ2YsQ0FBQztBQUFBLE1BQ0g7QUFBQSxJQUNGLENBQUM7QUFBQSxFQUNIO0FBQUEsRUFDQSxTQUFTO0FBQUE7QUFBQSxJQUVQLE9BQU87QUFBQSxNQUNMO0FBQUEsUUFDRSxNQUFNO0FBQUEsUUFDTixhQUFhLFFBQVEsa0NBQVcsT0FBTztBQUFBLE1BQ3pDO0FBQUEsSUFDRjtBQUFBLEVBQ0Y7QUFBQSxFQUNBLEtBQUs7QUFBQSxJQUNILHFCQUFxQjtBQUFBLE1BQ25CLE1BQU07QUFBQSxRQUNKLEtBQUs7QUFBQTtBQUFBLE1BQ1A7QUFBQSxJQUNGO0FBQUEsRUFDRjtBQUNGLENBQUM7IiwKICAibmFtZXMiOiBbXQp9Cg==
