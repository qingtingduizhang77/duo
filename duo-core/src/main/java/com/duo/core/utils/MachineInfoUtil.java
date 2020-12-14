package com.duo.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.hyperic.sigar.*;

/**
 * @author [ Yonsin ] on [2019/5/24]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Slf4j
public class MachineInfoUtil {

    private static StringBuffer _machineInfo=new StringBuffer("");

    public static void main(String[] args) {
        printMachineInfo();
    }

    public static String printMachineInfo(){

        try {
            infoPrint("-----------------开始-----------------");
            infoPrint("-----------------cpu信息-----------------");
            // cpu信息
            cpu();
            infoPrint("-----------------内存信息-----------------");

            // 内存信息
            memory();
            infoPrint("-----------------操作系统信息-----------------");

            // 操作系统信息
            os();
            infoPrint("-----------------用户信息-----------------");

            // 用户信息
            who();
            infoPrint("-----------------文件系统信息-----------------");

            // 文件系统信息
            file();
            infoPrint("-----------------网络信息-----------------");

            // 网络信息
            net();
            infoPrint("-----------------以太网信息-----------------");
            // 以太网信息
            ethernet();
            infoPrint("-----------------结束-----------------");

        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return _machineInfo.toString();
    }


    private static void memory() throws SigarException {
        Sigar sigar = new Sigar();
        Mem mem = sigar.getMem();
        // 内存总量
        infoPrint("内存总量:    " + mem.getTotal() / 1024L + "K av");
        // 当前内存使用量
        infoPrint("当前内存使用量:    " + mem.getUsed() / 1024L + "K used");
        // 当前内存剩余量
        infoPrint("当前内存剩余量:    " + mem.getFree() / 1024L + "K free");
        Swap swap = sigar.getSwap();
        // 交换区总量
        infoPrint("交换区总量:    " + swap.getTotal() / 1024L + "K av");
        // 当前交换区使用量
        infoPrint("当前交换区使用量:    " + swap.getUsed() / 1024L + "K used");
        // 当前交换区剩余量
        infoPrint("当前交换区剩余量:    " + swap.getFree() / 1024L + "K free");
    }

    private static void cpu() throws SigarException {
        Sigar sigar = new Sigar();
        CpuInfo infos[] = sigar.getCpuInfoList();
        CpuPerc cpuList[] = null;
        cpuList = sigar.getCpuPercList();
        for (int i = 0; i < infos.length; i++) {// 不管是单块CPU还是多CPU都适用
            CpuInfo info = infos[i];
            infoPrint("第" + (i + 1) + "块CPU信息");
            infoPrint("CPU的总量MHz:    " + info.getMhz());// CPU的总量MHz
            infoPrint("CPU生产商:    " + info.getVendor());// 获得CPU的卖主，如：Intel
            infoPrint("CPU类别:    " + info.getModel());// 获得CPU的类别，如：Celeron
            infoPrint("CPU缓存数量:    " + info.getCacheSize());// 缓冲存储器数量
            printCpuPerc(cpuList[i]);
        }
    }

    private static void printCpuPerc(CpuPerc cpu) {
        infoPrint("CPU用户使用率:    " + CpuPerc.format(cpu.getUser()));// 用户使用率
        infoPrint("CPU系统使用率:    " + CpuPerc.format(cpu.getSys()));// 系统使用率
        infoPrint("CPU当前等待率:    " + CpuPerc.format(cpu.getWait()));// 当前等待率
        infoPrint("CPU当前错误率:    " + CpuPerc.format(cpu.getNice()));//
        infoPrint("CPU当前空闲率:    " + CpuPerc.format(cpu.getIdle()));// 当前空闲率
        infoPrint("CPU总的使用率:    " + CpuPerc.format(cpu.getCombined()));// 总的使用率
    }

    private static void os() {
        OperatingSystem OS = OperatingSystem.getInstance();
        // 操作系统内核类型如： 386、486、586等x86
        infoPrint("操作系统:    " + OS.getArch());
        infoPrint("操作系统CpuEndian():    " + OS.getCpuEndian());//
        infoPrint("操作系统DataModel():    " + OS.getDataModel());//
        // 系统描述
        infoPrint("操作系统的描述:    " + OS.getDescription());
        // 操作系统类型
        // infoPrint("OS.getName(): " + OS.getName());
        // infoPrint("OS.getPatchLevel(): " + OS.getPatchLevel());//
        // 操作系统的卖主
        infoPrint("操作系统的卖主:    " + OS.getVendor());
        // 卖主名称
        infoPrint("操作系统的卖主名:    " + OS.getVendorCodeName());
        // 操作系统名称
        infoPrint("操作系统名称:    " + OS.getVendorName());
        // 操作系统卖主类型
        infoPrint("操作系统卖主类型:    " + OS.getVendorVersion());
        // 操作系统的版本号
        infoPrint("操作系统的版本号:    " + OS.getVersion());
    }

    private static void who() throws SigarException {
        Sigar sigar = new Sigar();
        Who who[] = sigar.getWhoList();
        if (who != null   && who.length > 0) {
            for (int i = 0; i < who.length; i++) {
                // infoPrint("当前系统进程表中的用户名" + String.valueOf(i));
                Who _who = who[i];
                infoPrint("用户控制台:    " + _who.getDevice());
                infoPrint("用户host:    " + _who.getHost());
                // infoPrint("getTime(): " + _who.getTime());
                // 当前系统进程表中的用户名
                infoPrint("当前系统进程表中的用户名:    " + _who.getUser());
            }
        }
    }

    private static void file() throws Exception {
        Sigar sigar = new Sigar();
        FileSystem fslist[] = sigar.getFileSystemList();
        for (int i = 0; i <fslist.length; i++) {
            infoPrint("分区的盘符名称" + i);
            FileSystem fs = fslist[i];
            // 分区的盘符名称
            infoPrint("盘符名称:    " + fs.getDevName());
            // 分区的盘符名称
            infoPrint("盘符路径:    " + fs.getDirName());
            infoPrint("盘符标志:    " + fs.getFlags());//
            // 文件系统类型，比如 FAT32、NTFS
            infoPrint("盘符类型:    " + fs.getSysTypeName());
            // 文件系统类型名，比如本地硬盘、光驱、网络文件系统等
            infoPrint("盘符类型名:    " + fs.getTypeName());
            // 文件系统类型
            infoPrint("盘符文件系统类型:    " + fs.getType());
            FileSystemUsage usage = null;
            usage = sigar.getFileSystemUsage(fs.getDirName());
            switch (fs.getType()) {
                case 0: // TYPE_UNKNOWN ：未知
                    break;
                case 1: // TYPE_NONE
                    break;
                case 2: // TYPE_LOCAL_DISK : 本地硬盘
                    // 文件系统总大小
                    infoPrint(fs.getDevName() + "总大小:    " + usage.getTotal() + "KB");
                    // 文件系统剩余大小
                    infoPrint(fs.getDevName() + "剩余大小:    " + usage.getFree() + "KB");
                    // 文件系统可用大小
                    infoPrint(fs.getDevName() + "可用大小:    " + usage.getAvail() + "KB");
                    // 文件系统已经使用量
                    infoPrint(fs.getDevName() + "已经使用量:    " + usage.getUsed() + "KB");
                    double usePercent = usage.getUsePercent() * 100D;
                    // 文件系统资源的利用率
                    infoPrint(fs.getDevName() + "资源的利用率:    " + usePercent + "%");
                    break;
                case 3:// TYPE_NETWORK ：网络
                    break;
                case 4:// TYPE_RAM_DISK ：闪存
                    break;
                case 5:// TYPE_CDROM ：光驱
                    break;
                case 6:// TYPE_SWAP ：页面交换
                    break;
            }
            infoPrint(fs.getDevName() + "读出：    " + usage.getDiskReads());
            infoPrint(fs.getDevName() + "写入：    " + usage.getDiskWrites());
        }
        return;
    }

    private static void net() throws Exception {
        Sigar sigar = new Sigar();
        String ifNames[] = sigar.getNetInterfaceList();
        for (int i = 0; i < ifNames.length; i++) {
            String name = ifNames[i];
            NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
            infoPrint("网络设备名:    " + name);// 网络设备名
            infoPrint("IP地址:    " + ifconfig.getAddress());// IP地址
            infoPrint("子网掩码:    " + ifconfig.getNetmask());// 子网掩码
            if ((ifconfig.getFlags() & 1L) <= 0L) {
                infoPrint("!IFF_UP...skipping getNetInterfaceStat");
                continue;
            }
            NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);
            infoPrint(name + "接收的总包裹数:" + ifstat.getRxPackets());// 接收的总包裹数
            infoPrint(name + "发送的总包裹数:" + ifstat.getTxPackets());// 发送的总包裹数
            infoPrint(name + "接收到的总字节数:" + ifstat.getRxBytes());// 接收到的总字节数
            infoPrint(name + "发送的总字节数:" + ifstat.getTxBytes());// 发送的总字节数
            infoPrint(name + "接收到的错误包数:" + ifstat.getRxErrors());// 接收到的错误包数
            infoPrint(name + "发送数据包时的错误数:" + ifstat.getTxErrors());// 发送数据包时的错误数
            infoPrint(name + "接收时丢弃的包数:" + ifstat.getRxDropped());// 接收时丢弃的包数
            infoPrint(name + "发送时丢弃的包数:" + ifstat.getTxDropped());// 发送时丢弃的包数
        }
    }

    private static void ethernet() throws SigarException {
        Sigar sigar = null;
        sigar = new Sigar();
        String[] ifaces = sigar.getNetInterfaceList();
        for (int i = 0; i < ifaces.length; i++) {
            NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]);
            if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress())
                    || (cfg.getFlags() &  NetFlags.IFF_LOOPBACK) != 0
                    || NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) {
                continue;
            }
            infoPrint(cfg.getName() + "IP地址:" + cfg.getAddress());// IP地址
            infoPrint(cfg.getName() + "网关广播地址:" + cfg.getBroadcast());// 网关广播地址
            infoPrint(cfg.getName() + "网卡MAC地址:" + cfg.getHwaddr());// 网卡MAC地址
            infoPrint(cfg.getName() + "子网掩码:" + cfg.getNetmask());// 子网掩码
            infoPrint(cfg.getName() + "网卡描述信息:" + cfg.getDescription());// 网卡描述信息
            infoPrint(cfg.getName() + "网卡类型" + cfg.getType());//
        }
    }

    private static void infoPrint(String info){
        log.info(info);
        _machineInfo.append(info).append("\n");
    }
}
