/**
 * token标识
 */
const TOKEN = 'Authorization'

/**
 * 请求url
 */
const BASE_URL = 'http://localhost:9090'
// const BASE_URL = 'http://47.119.118.15:9090'


/**
 * SSE请求url
 */
const SSE_URL = 'http://47.119.118.15:9090/sys/subscribe'

/**
 * 语言状态
 */
const LOCAL = 'localStore'

/**
 * 语言标识请求头
 */
const LANGUAGE = 'X-Language'

/**
 * 加密头
 */
const ENCRYPTKEY = 'Encrypt-Key'
/**
 * RSA密钥
 */
const RSA = 'rsa'
/**
 * AES秘钥
 */
const AES = 'aes'

export { TOKEN, BASE_URL, LANGUAGE, LOCAL, ENCRYPTKEY, RSA, AES,SSE_URL }
