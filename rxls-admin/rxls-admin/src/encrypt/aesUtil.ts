import CryptoJS from 'crypto-js'

/**
 * 生成指定长度的随机字节数组
 * @param bits 长度
 * @returns Base64编码后的KEY
 */
 const createAesKey = (bits: 16 | 24 | 32) => {
  const bytes = [
    'A',
    'B',
    'C',
    'D',
    'E',
    'F',
    'G',
    'H',
    'I',
    'J',
    'K',
    'l',
    'M',
    'N',
    'O',
    'P',
    'Q',
    'R',
    'S',
    'V',
    'U',
    'W',
    'B',
    'A',
    'Y',
    'T',
    '1',
    '2',
    '3',
    '4',
    '5',
    '6',
    '7',
    '8',
    '9',
    '0',
  ]
  let hexString = ''
  for (let i = 0; i < bits; i++) {
    let index = Math.floor(Math.random() * bytes.length) + 0
    hexString += bytes[index]
  }
  return hexString
}

/**
 * AES加密
 * @param secretKey 加密key
 * @param data 需要加密的内容
 * @returns
 */
const encryptWithAES = (secretKey: string, data: string) => {
  let srcs = CryptoJS.enc.Utf8.parse(data)
  let key = CryptoJS.enc.Utf8.parse(secretKey)
  let encrypted = CryptoJS.AES.encrypt(srcs, key, {
    mode: CryptoJS.mode.ECB,
    padding: CryptoJS.pad.Pkcs7,
  })
  return encrypted.toString()
}

/**
 * AES解密
 * @param secretKey 加密key
 * @param encryptedData 已经加密的文本
 * @returns
 */
const decryptWithAES = (secretKey: string, encryptedData: string) => {
  let key = CryptoJS.enc.Utf8.parse(secretKey)
  var decrypt = CryptoJS.AES.decrypt(encryptedData, key, {
    mode: CryptoJS.mode.ECB,
    padding: CryptoJS.pad.Pkcs7,
  })
  return CryptoJS.enc.Utf8.stringify(decrypt).toString()
}

export { decryptWithAES, encryptWithAES, createAesKey }
