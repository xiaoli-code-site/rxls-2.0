package cn.rx.common.util.server;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.VirtualMemory;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;
import oshi.util.Util;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 服务器相关信息
 */
public class ServerInfoUtil {

    /**
     * JVM相关信息
     */
    private Jvm jvm = new Jvm();

    /**
     * 服务器相关信息
     */
    private Sys sys = new Sys();

    public void copyInfo(){
        setSysInfo();
        setJvmInfo();
    }



        private final DecimalFormat df = new DecimalFormat("0.00");
        /**
         * 定义GB的计算常量
         */
        private static final int GB = 1024 * 1024 * 1024;
        /**
         * 定义MB的计算常量
         */
        private static final int MB = 1024 * 1024;
        /**
         * 定义KB的计算常量
         */
        private static final int KB = 1024;

        /**
         * 格式化小数
         */
        private static final DecimalFormat DF = new DecimalFormat("0.00");


    /**
     * 设置服务器信息
     */
    private void setSysInfo() {
        Properties props = System.getProperties();
        sys.setComputerName(IpUtils.getHostName());
        sys.setComputerIp(IpUtils.getHostIp());
        sys.setOsName(props.getProperty("os.name"));
        sys.setOsArch(props.getProperty("os.arch"));
        sys.setUserDir(props.getProperty("sysUser.dir"));
    }

    /**
     * 设置Java虚拟机
     */
    private void setJvmInfo(){
        Properties props = System.getProperties();
        jvm.setTotal(Runtime.getRuntime().totalMemory());
        jvm.setMax(Runtime.getRuntime().maxMemory());
        jvm.setFree(Runtime.getRuntime().freeMemory());
        jvm.setVersion(props.getProperty("java.version"));
        jvm.setHome(props.getProperty("java.home"));
    }



    public  Map<String, Object> getServerInfo() {
            Map<String, Object> resultMap = new LinkedHashMap<>(8);
            try {
                copyInfo();
                SystemInfo si = new SystemInfo();
                OperatingSystem os = si.getOperatingSystem();
                HardwareAbstractionLayer hal = si.getHardware();
                // cpu 信息
                resultMap.put("cpu", getCpuInfo(hal.getProcessor()));
                // 内存信息
                resultMap.put("memory", getMemoryInfo(hal.getMemory()));
                // 交换区信息
                resultMap.put("swap", getSwapInfo(hal.getMemory()));
                // 磁盘
                resultMap.put("disk", getDiskInfo(os));
                // jvm
                resultMap.put("jvm", jvm);
                // jvm
                resultMap.put("sys", sys);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultMap;
        }

        /**
         * 获取磁盘信息
         */
        private Map<String, Object> getDiskInfo(OperatingSystem os) {
            Map<String, Object> diskInfo = new LinkedHashMap<>();
            FileSystem fileSystem = os.getFileSystem();
            List<OSFileStore> fsArray = fileSystem.getFileStores();
            String osName = System.getProperty("os.name");
            long available = 0, total = 0;
            for (OSFileStore fs : fsArray) {
                // windows 需要将所有磁盘分区累加，linux 和 mac 直接累加会出现磁盘重复的问题，待修复
                if (osName.toLowerCase().startsWith("win")) {
                    available += fs.getUsableSpace();
                    total += fs.getTotalSpace();
                } else {
                    available = fs.getUsableSpace();
                    total = fs.getTotalSpace();
                    break;
                }
            }
            long used = total - available;
            diskInfo.put("total", total > 0 ? getSize(total) : "?");
            diskInfo.put("available", getSize(available));
            diskInfo.put("used", getSize(used));
            if (total != 0) {
                diskInfo.put("usageRate", df.format(used / (double) total * 100));
            } else {
                diskInfo.put("usageRate", 0);
            }
            return diskInfo;
        }

        /**
         * 文件大小转换
         */
        public static String getSize(long size) {
            String resultSize;
            if (size / GB >= 1) {
                //如果当前Byte的值大于等于1GB
                resultSize = DF.format(size / (float) GB) + "GB   ";
            } else if (size / MB >= 1) {
                //如果当前Byte的值大于等于1MB
                resultSize = DF.format(size / (float) MB) + "MB   ";
            } else if (size / KB >= 1) {
                //如果当前Byte的值大于等于1KB
                resultSize = DF.format(size / (float) KB) + "KB   ";
            } else {
                resultSize = size + "B   ";
            }
            return resultSize;
        }

        /**
         * 获取交换区信息
         */
        private Map<String, Object> getSwapInfo(GlobalMemory memory) {
            Map<String, Object> swapInfo = new LinkedHashMap<>();
            VirtualMemory virtualMemory = memory.getVirtualMemory();
            long total = virtualMemory.getSwapTotal();
            long used = virtualMemory.getSwapUsed();
            swapInfo.put("total", FormatUtil.formatBytes(total));
            swapInfo.put("used", FormatUtil.formatBytes(used));
            swapInfo.put("available", FormatUtil.formatBytes(total - used));
            if (used == 0) {
                swapInfo.put("usageRate", 0);
            } else {
                swapInfo.put("usageRate", df.format(used / (double) total * 100));
            }
            return swapInfo;
        }

        /**
         * 获取内存信息
         */
        private Map<String, Object> getMemoryInfo(GlobalMemory memory) {
            Map<String, Object> memoryInfo = new LinkedHashMap<>();
            memoryInfo.put("total", FormatUtil.formatBytes(memory.getTotal()));
            memoryInfo.put("available", FormatUtil.formatBytes(memory.getAvailable()));
            memoryInfo.put("used", FormatUtil.formatBytes(memory.getTotal() - memory.getAvailable()));
            memoryInfo.put("usageRate", df.format((memory.getTotal() - memory.getAvailable()) / (double) memory.getTotal() * 100));
            return memoryInfo;
        }

        /**
         * 获取Cpu相关信息
         */
        private Map<String, Object> getCpuInfo(CentralProcessor processor) {
            Map<String, Object> cpuInfo = new LinkedHashMap<>();
            cpuInfo.put("name", processor.getProcessorIdentifier().getName());
            cpuInfo.put("package", processor.getPhysicalPackageCount());
            cpuInfo.put("core", processor.getPhysicalProcessorCount());
            cpuInfo.put("coreNumber", processor.getPhysicalProcessorCount());
            cpuInfo.put("logic", processor.getLogicalProcessorCount());
            // CPU信息
            long[] prevTicks = processor.getSystemCpuLoadTicks();
            // 等待1秒...
            Util.sleep(1000);
            long[] ticks = processor.getSystemCpuLoadTicks();
            long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
            long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
            long sys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
            long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
            long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
            long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
            long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
            long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
            long totalCpu = user + nice + sys + idle + iowait + irq + softirq + steal;
            cpuInfo.put("used", df.format(100d * user / totalCpu + 100d * sys / totalCpu));
            cpuInfo.put("idle", df.format(100d * idle / totalCpu));
            return cpuInfo;
        }


}


