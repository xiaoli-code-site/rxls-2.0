<template>
  <div class="home_box">
    <div class="introduce">
      <a-card style="flex: 1">
        <template #title>
          <div class="title">软星猎手2.0</div>
        </template>
        <p>
          我深信，打造一款自己理想的管理基础模板是一件极具意义的事情。在开发的过程中，
          不仅可以满足个人对于系统功能和用户体验的独特追求，还能在这个过程中不断提升自己的技术水平与解决问题的能力。
          因此，我决定投入大量的业余时间，精心构建这样一个管理系统框架。这不仅是对技术的一次挑战，更是一场自我实现的旅程。通过这个项目，
          我可以将自己的创意和技术理念融入其中，打造出一款真正让自己满意的作品。未来，我计划持续并不断优化现有特性，使其更加完善。
          希望这款管理系统能够成为您项目中的得力助手，同时也希望能激励更多人去追求自己心中的理想项目。
        </p>
        <a
          class="gitee"
          href="https://gitee.com/liyaozhonghaoshuai/r-soft-star-workshop"
          target="_blank"
          >👉️👉️👉️ Gitee仓库地址:
          https://gitee.com/liyaozhonghaoshuai/r-soft-star-workshop</a
        >
        <p style="padding: 20px 0 0">大家如果有啥意见，欢迎在Gitte私信我</p>
        <img
          class="logo"
          src="https://www.lyzhnb.top/rx_to_do/icon/rx_logo.png"
        />
      </a-card>
      <a-card style="flex: 1">
        <template #title>
          <div class="title">技术选型</div>
        </template>
        <div class="introduce">
          <a-card title="前端技术" :bordered="false">
            <p>Vue3</p>
            <p>Vite</p>
            <p>Axios</p>
            <p>Ant Design Vue</p>
            <p>Pinia</p>
            <p>Sass</p>
            <p>...</p>
          </a-card>
          <a-card title="后端技术" :bordered="false">
            <p>SpringBoot</p>
            <p>Shiro</p>
            <p>MyBatis</p>
            <p>MyBatis Plus</p>
            <p>MySql</p>
            <p>Redis</p>
            <p>...</p>
          </a-card>
        </div>

        <a-card style="flex: 1" title="更新日志">
          <a-collapse v-model:activeKey="activeKey">
            <a-collapse-panel
              v-for="(item, index) in versionList"
              :key="index"
              :header="item.title"
            >
              <p v-for="(items, indexs) in item.list" :key="indexs">
                {{ items }}
              </p>
            </a-collapse-panel>
          </a-collapse>
        </a-card>
      </a-card>
    </div>
  </div>
</template>

<!-- 首页 -->
<script setup lang="ts">
import { ref } from 'vue'

const activeKey = ref(['1'])

const versionList = ref([
  {
    title: 'v0.0.1',
    list: ['软星管理系统初步搭建完成'],
  },
])
</script>

<style scoped lang="scss">
.home_box {
  width: 100%;

  .introduce {
    display: flex;

    .logo {
      width: 100%;
      height: 400px;
      object-fit: cover;
    }

    .gitee {
      display: flex;
      padding: 20px 0;
    }
  }

  .title {
    font-size: 24px;
  }
}
</style>
