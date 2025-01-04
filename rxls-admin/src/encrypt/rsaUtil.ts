import JSEncrypt from 'jsencrypt'

/**
 * 加密
 * @param data 需要加密的文本
 * @param publicKey 公钥
 */
const rsaEncrypt = (data: string, publicKey: string) => {
  let encryptor = new JSEncrypt() // 创建加密对象实例
  encryptor.setPublicKey(publicKey) //设置公钥
  const encryptText = encryptor.encrypt(data) // 对内容进行加密
  if (encryptText) {
    return encryptText
  }
  return ''
}

/**
 * 解密
 * @param encryptedData 加密文本
 * @param privateKey 私钥
 */
const rsaDecrypt = (encryptedData: string, privateKey: string) => {
  let decrypt = new JSEncrypt() //创建解密对象实例
  decrypt.setPrivateKey(privateKey) //设置秘钥
  const data = decrypt.decrypt(encryptedData)
  if (data) {
    return data
  }
  return '' //解密之前拿公钥加密的内容
}
export { rsaDecrypt, rsaEncrypt }
