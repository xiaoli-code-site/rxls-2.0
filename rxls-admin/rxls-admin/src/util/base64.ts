/**
 * Base64编码
 * @param text 文本
 * @returns 
 */
const encodeBase64 = (text: string) => {
  // 将输入字符串转换为二进制数据
  let binary = ''
  for (let i = 0; i < text.length; i++) {
    binary += String.fromCharCode(text.charCodeAt(i))
  }
  // 使用btoa进行Base64编码
  return btoa(binary)
}

export { encodeBase64 }
