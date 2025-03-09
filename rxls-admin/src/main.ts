import { createApp } from "vue";
import "./style.css";
import App from "./App.vue";
import router from "@/router/index";
import { createPinia } from "pinia";
import i18n from "./local";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
import { iconsHandler } from "./config/icon";
import { initSetup } from "./hook/instruction";
const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);

const app = iconsHandler(createApp(App));

//注册自定义处理器
initSetup(app);

app.use(router);
app.use(i18n);
app.use(pinia);

app.mount("#app");
