/**
 * 分页查询数据
 */
export interface SysData {
  cpu: {
    name: string
    package: string
    core: string
    coreNumber: number
    logic: string
    used: string
    idle: string
  }
  memory: {
    total: string
    available: string
    used: string
    usageRate: string
  }
  swap: {
    total: string
    used: string
    available: string
    usageRate: string
  }
  disk: {
    total: string
    available: string
    used: string
    usageRate: string
  }
  jvm: {
    total: number
    max: number
    free: number
    home: string
    runTime: string
    startTime: string
    usage: number
    used: number
    version: string
  }
  sys: {
    computerIp: string
    computerName: string
    osArch: string
    osName: string
    userDir: string
  }
}
