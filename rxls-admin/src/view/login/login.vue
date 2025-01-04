<template>
  <div
    class="login_box rx_no_select"
    :style="{ backgroundColor: token.colorBgBase }"
  >
    <!-- 登录表单 -->
    <a-form
      autocomplete="off"
      :model="formState"
      name="normal_login"
      class="login_form"
      @finish="onFinish"
    >
      <!-- 设置 -->
      <div class="set_up">
        <!-- 语言 -->
        <a-tooltip @click="localStore.localeHanlder">
          <template #title>
            <span>{{ t('p_login.languageSwitching') }}</span>
          </template>
          <a class="language" v-if="localStore.status == 'zh'">{{
            t('common.zh')
          }}</a>
          <a v-else class="language">
            {{ t('common.en') }}
          </a>
        </a-tooltip>
        <!-- 主题 -->
        <a-tooltip @click="themStore.themeHandler">
          <template #title>
            <span>{{ t('p_login.themeSwitching') }}</span>
          </template>
          <BulbOutlined
            v-if="themStore.theme == 'main'"
            :style="{ fontSize: '17px' }"
          />
          <BulbFilled :style="{ fontSize: '17px' }" v-else />
        </a-tooltip>
      </div>

      <!-- 标题 -->
      <h2 class="title">{{ t('p_login.administration') }}</h2>

      <!-- 口号 -->
      <a-space align="center" style="padding: 15px 0 20px">
        <AlertOutlined class="site_icon" />
        <p class="msg">{{ t('p_login.slogan') }}</p>
      </a-space>

      <!-- 用户名 -->
      <a-form-item
        :label="t('common.username')"
        name="username"
        :rules="[{ required: true, message: t('user.pleaseInputUsername') }]"
      >
        <a-input
          v-model:value="formState.username"
          autocomplete="off"
          :placeholder="t('common.pleaseInput')"
        >
          <template #prefix>
            <UserOutlined class="site-form-item-icon" />
          </template>
        </a-input>
      </a-form-item>

      <!-- 密码 -->
      <a-form-item
        :label="t('common.password')"
        name="password"
        :rules="[{ required: true, message: t('p_login.pleaseInputPassword') }]"
      >
        <a-input-password
          autocomplete="off"
          v-model:value="formState.password"
          :placeholder="t('common.pleaseInput')"
        >
          <template #prefix>
            <LockOutlined />
          </template>
        </a-input-password>
      </a-form-item>

      <!-- 验证码 -->
      <a-form-item
        :label="t('p_login.verifyCode')"
        name="verifyCode"
        :rules="[
          { required: true, message: t('p_login.pleaseInputVerifyCode') },
        ]"
      >
        <a-space align="center">
          <a-input
            v-model:value="formState.verifyCode"
            id="validating"
            autocomplete="off"
            :placeholder="t('common.pleaseInput')"
          />

          <img
            @click="getVerifyCode"
            :src="verifyCodeImage"
            :alt="t('p_login.acquisitionFailed')"
            :title="t('p_login.obtainVerificationCode')"
            class="verification_img"
          />
        </a-space>
      </a-form-item>

      <!-- 登录提交按钮 -->
      <a-form-item>
        <a-button
          :disabled="disabled"
          type="primary"
          html-type="submit"
          class="login_button"
        >
          {{ t('p_login.login') }}
        </a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<!-- 登录页面 -->
<script lang="ts" setup>
import message from 'ant-design-vue/es/message'
import { events } from '@/events/eventBus'
import { useI18n } from 'vue-i18n'
import { reactive, computed, onMounted, ref } from 'vue'
import {
  UserOutlined,
  LockOutlined,
  AlertOutlined,
  BulbOutlined,
  BulbFilled,
} from '@ant-design/icons-vue'
import { ReqVerificationCode } from '@/api/verification'
import { LoginQuery } from '@/api/admin/type'
import { theme } from 'ant-design-vue'
import { useThemeStore } from '@/store/theme'
import { useLocalStore } from '@/store/local'
import { ReqLogin } from '@/api/admin'
import { ReqEncrypt } from '@/api/request'
import { AES, RSA } from '@/config/constant'
import { createAesKey } from '@/encrypt/aesUtil'

/**
 * 当前主题算法
 */
const { token } = theme.useToken()
/**
 * 登录表单参数
 */
const formState = reactive<LoginQuery>({
  username: 'admin',
  password: '1234567',
  verifyCodeKey: '',
  verifyCode: '',
})

/**
 * 主题状态
 */
const themStore = useThemeStore()

/**
 * 语言状态
 */
const localStore = useLocalStore()

/**
 * 国际化状态
 *
 */
const { t } = useI18n()

/**
 * 验证码图片
 */
const verifyCodeImage = ref('')

/**
 * 请求登录
 */
const onFinish = (_values: any) => {
  const hide = message.loading(t('p_login.loggingIn'), 0)
  ReqLogin(formState)
    .then((res) => {
      if (res.data.code == 200 && res.data.data) {
        events.emit('LOGIN', res.data.data)
      }
    })
    .catch(() => {
      getVerifyCode()
    })
    .finally(() => {
      setTimeout(hide, 100)
    })
}

/**
 * 登录按钮是否禁用
 */
const disabled = computed(() => {
  return !(formState.username && formState.password && formState.verifyCode)
})

/**
 * 获取验证码
 */
const getVerifyCode = () => {
  ReqVerificationCode().then((res) => {
    if (res.data.code == 200) {
      formState.verifyCode = ''
      verifyCodeImage.value = res.data.data.codeImage
      formState.verifyCodeKey = res.data.data.codeKey
    }
  })
}

/**
 * 页面加载
 */
onMounted(() => {
  getVerifyCode()
  init()
})

/**
 * 初始化
 */
const init = () => {
  ReqEncrypt().then((res) => {
    if ((res.data.code = 200)) {
      window.localStorage.setItem(RSA, res.data.data)
      window.localStorage.setItem(AES, createAesKey(16))
    }
  })
}
</script>

<style lang="scss">
.login_box {
  position: relative;
  width: 100%;
  height: 100vh;
  min-width: 500px;
  min-height: 700px;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;

  &::after {
    content: 'Welcome!';
    font-weight: 550;
    font-size: 30px;
    text-align: center;
    line-height: 300px;
    position: absolute;
    bottom: 0;
    left: 0;
    width: 500px;
    height: 500px;
    color: rgb(255, 255, 255);
    background: rgba(61, 136, 249, 0.788);
    border-radius: 50%;
    opacity: 0;
    transform: translateY(50%) translateX(-30%);
    box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
    animation: bottom_anim 0.4s 0.5s ease-in forwards;
  }

  &::before {
    content: 'Hi Hello!';
    font-weight: 550;
    font-size: 30px;
    text-align: center;
    line-height: 750px;
    position: absolute;
    top: 0;
    right: 0;
    color: rgb(255, 255, 255);
    width: 500px;
    height: 500px;
    background: rgba(61, 249, 99, 0.827);
    border-radius: 50%;
    opacity: 0;
    transform: translateY(-60%) translateX(30%);
    box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
    animation: top_anim 0.4s 0.3s ease-in forwards;
  }

  .login_form {
    position: relative;
    z-index: 2;
    text-align: center;
    width: 400px;
    margin-top: -100px;
    background: rgba(255, 255, 255, 0.256);
    padding: 30px 40px 20px;
    border-radius: 10px;
    opacity: 0;
    box-shadow: rgb(38, 57, 77) 0px 20px 30px -10px;
    animation: move_anim 0.7s ease-in forwards;

    .title {
      font-size: 22px;
      font-weight: 520;
    }

    .msg {
      color: rgb(0, 128, 255);
      font-weight: 500;
    }

    .site_icon {
      color: rgb(0, 128, 255);
    }

    &::after {
      content: '';
      position: absolute;
      top: 50%;
      left: 50%;
      width: 200px;
      height: 200px;
      background: rgba(61, 136, 249, 0.68);
      border-radius: 50%;
      transform: translateY(-50%) translateX(-50%);
      box-shadow: rgba(99, 99, 99, 0.2) 0px 2px 8px 0px;
      z-index: -1;
    }

    &::before {
      content: '';
      position: absolute;
      top: 0;
      right: 0;
      width: 100px;
      height: 100px;
      background: rgba(61, 249, 99, 0.372);
      border-radius: 50%;
      transform: translateY(43%) translateX(-100%);
      box-shadow: rgba(99, 99, 99, 0.2) 0px 2px 8px 0px;
      z-index: -1;
    }

    .set_up {
      position: absolute;
      top: 10px;
      right: 10px;

      .language {
        padding-right: 10px;
        font-weight: 550;
      }
    }
  }

  .login_button {
    width: 300px;
    height: 36px;
    margin: 0 auto;
  }

  .forgot {
    display: block;
    width: 100%;
    height: fit-content;
  }

  .verification_img {
    height: 30px;
    cursor: pointer;
    border-radius: 5px;
    font-size: 13px;
  }
}

@keyframes move_anim {
  0% {
    opacity: 0;
    transform: translateY(-200%) scale(0);
  }

  40% {
    opacity: 1;
    transform: translateY(-30%) scale(0.9);
  }

  60% {
    transform: translateY(0) scale(1);
  }

  70% {
    transform: scale(1.2);
  }

  80% {
    transform: scale(1);
  }

  90% {
    transform: scale(1.1);
  }

  100% {
    opacity: 1;
    transform: scale(1);
  }
}

@keyframes top_anim {
  0% {
    opacity: 0;
    transform: scale(0) translateY(-60%) translateX(30%);
  }

  50% {
    opacity: 0.5;
    transform: scale(1.2) translateY(-60%) translateX(30%);
  }

  70% {
    opacity: 0.7;
    transform: scale(1) translateY(-60%) translateX(30%);
  }

  90% {
    opacity: 0.9;
    transform: scale(1.1) translateY(-60%) translateX(30%);
  }

  100% {
    opacity: 1;
    transform: scale(1) translateY(-60%) translateX(30%);
  }
}

@keyframes bottom_anim {
  0% {
    opacity: 0;
    transform: scale(0) translateY(50%) translateX(-30%);
  }

  50% {
    opacity: 0.5;
    transform: scale(1.2) translateY(50%) translateX(-30%);
  }

  70% {
    opacity: 0.7;
    transform: scale(1) translateY(50%) translateX(-30%);
  }

  90% {
    opacity: 0.9;
    transform: scale(1.1) translateY(50%) translateX(-30%);
  }

  100% {
    opacity: 1;
    transform: scale(1) translateY(50%) translateX(-30%);
  }
}
</style>
