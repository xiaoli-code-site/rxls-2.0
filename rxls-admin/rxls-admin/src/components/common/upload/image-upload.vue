<template>
  <div class="upload_box">
    <a-upload
      v-model:file-list="fileList"
      :maxCount="props.maxCount"
      list-type="picture-card"
      :multiple="props.maxCount > 1"
      @preview="handlePreview"
      :show-upload-list="props.maxCount > 1"
      :before-upload="beforeUpload"
      @remove="handleRemove"
    >
      <!-- 上传单张图片 -->
      <div v-if="props.maxCount == 1">
        <img
          class="avatar"
          v-if="imgUrl && !loading"
          :src="imgUrl"
          alt="avatar"
        />
        <div v-else>
          <loading-outlined v-if="loading"></loading-outlined>
          <plus-outlined v-else></plus-outlined>
          <div class="msg">{{ t('common.selectImage') }}</div>
        </div>
      </div>

      <!-- 上传多张图片 -->
      <div v-else-if="fileList.length < props.maxCount">
        <plus-outlined />
        <div class="msg">{{ t('common.selectImage') }}</div>
      </div>
    </a-upload>

    <!-- 上传按钮 -->
    <a-button
      size="small"
      type="primary"
      ghost
      @click="uploadClick"
      v-show="uploadBth && fileList.filter((s) => s.size).length > 0"
    >
      {{ t('common.upload') }}
    </a-button>

    <!-- 图片预览 -->
    <a-modal
      :open="previewVisible"
      :title="previewTitle"
      :footer="null"
      @cancel="handleCancel"
    >
      <img alt="example" style="width: 100%" :src="previewImage" />
    </a-modal>
  </div>
</template>

<!-- 文件上传 -->
<script lang="ts" setup>
import { useI18n } from 'vue-i18n'
import { PlusOutlined, LoadingOutlined } from '@ant-design/icons-vue'
import { message, type UploadProps } from 'ant-design-vue'
import { ReqUploadFile } from '@/api/system/upload'
import { PropType, ref, watch } from 'vue'

/**
 * 国际化状态
 */
const { t } = useI18n()

const emit = defineEmits<{
  /**
   * 上传触发
   * @param 图片路径
   */
  (e: 'onUpload', url: string | string[]): void

  /**
   * 删除时触发
   * @param daat 图片路径
   */
  (e: 'onDelete', data: string | string[]): void
}>()

const props = defineProps({
  /**
   * 图片列表
   * @param url 上传的文件
   */
  imgList: {
    type: Array as PropType<string[]>,
    default: [],
  },
  /**
   * 上传最大数
   */
  maxCount: {
    type: Number,
    default: 1,
  },
})

/**
 * 是否显示上传按钮
 */
const uploadBth = ref(false)

/**
 * 临时回显数组
 */
const fileList = ref<any[]>([])

/**
 * 上传图片的临时路径
 */
const imgUrl = ref(props.imgList[0])

/**
 * 上传状态
 */
const loading = ref<boolean>(false)

/**
 * 监听图片文件
 */
watch(
  () => props.imgList,
  (newValue, _oldValue) => {
    let tempArr = []
    for (let i = 0; i < newValue.length; i++) {
      tempArr.push({
        status: 'done',
        url: newValue[i],
      })
    }
    fileList.value = tempArr
  },
  { immediate: true }
)

/**
 * 文件上传
 */
const beforeUpload: UploadProps['beforeUpload'] = (file: any) => {
  uploadBth.value = true
  if (props.maxCount == 1) {
    fileList.value = [file]
    if (props.maxCount == 1) {
      const temporaryUrl = URL.createObjectURL(file as Blob)
      imgUrl.value = temporaryUrl
    }
    return false
  }
  fileList.value = [...(fileList.value || []), file]

  // 禁用自动上传
  return false
}

/**
 * 监听文件移除
 */
const handleRemove: UploadProps['onRemove'] = (file) => {
  const index = fileList.value.indexOf(file)
  const newFileList = fileList.value.slice()
  newFileList.splice(index, 1)
  fileList.value = newFileList
  emit(
    'onDelete',
    fileList.value.map((s) => s.url)
  )
}

/**
 * 文件上传事件
 */
const uploadClick = () => {
  loading.value = true
  const formData = new FormData()
  fileList.value.forEach((file: any) => {
    formData.append('image_file', file.originFileObj)
  })
  let hide = message.info(t('common.uploading'), 0)
  ReqUploadFile(formData)
    .then((result) => {
      if (props.maxCount > 1) {
        let data = [...props.imgList, ...result.data.data]
        emit('onUpload', data as unknown as string[])
      } else {
        imgUrl.value = result.data.data[0]
        emit('onUpload', result.data.data[0])
      }

      message.info(t('message.uploadSuccess'))
    })
    .finally(() => {
      setTimeout(hide, 10)
      setTimeout(() => {
        loading.value = false
      }, 500)
    })
}

/**
 * 是否打开预览
 */
const previewVisible = ref(false)
/**
 * 当前预览图片
 */
const previewImage = ref('')
/**
 * 预览标题
 */
const previewTitle = ref('')

/**
 * 点击取消预览
 */
const handleCancel = () => {
  previewVisible.value = false
  previewTitle.value = ''
}

/**
 * 点击预览
 */
const handlePreview = async (file: any) => {
  if (!file.url && !file.preview) {
    file.preview = (await getBase64(file.originFileObj)) as string
  }
  previewImage.value = file.url || file.preview
  previewVisible.value = true
  previewTitle.value =
    file.name || file.url.substring(file.url.lastIndexOf('/') + 1)
}

/**
 * base64转换
 */
const getBase64 = (file: File) => {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = (error) => reject(error)
  })
}
</script>

<style lang="scss" scoped>
.upload_box {
  .avatar {
    width: 80px;
    height: 80px;
    border-radius: 6px;
  }

  .msg {
    margin-top: 8px;
  }
}
</style>
