package rain.sshshell;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
public class TopCommend implements Commend {

    private final String params;

    @Override public String getCommendLine() {
        return "top -b -n 1 " + params;
    }

    /**
     * 结果前 5 行
     * <p>
     * top - 18:57:58 up  8:40,  2 users,  load average: 0.25, 0.19, 0.12
     * 任务: 296 total,   1 running, 295 sleeping,   0 stopped,   0 zombie
     * %Cpu(s):  1.9 us,  1.9 sy,  0.0 ni, 96.2 id,  0.0 wa,  0.0 hi,  0.0 si,  0.0 st
     * MiB Mem :  15756.9 total,  11112.5 free,   2309.2 used,   2335.2 buff/cache
     * MiB Swap:   2048.0 total,   2048.0 free,      0.0 used.  12321.8 avail Mem
     * <p>
     * 进程号 USER      PR  NI    VIRT    RES    SHR    %CPU  %MEM     TIME+ COMMAND
     * 1501 rain      20   0  849504  73048  45132 S   0.0   0.5   8:23.38 Xorg
     * <p>
     * 第1行：系统时间、运行时间、登录终端数、系统负载（三个数值分别为1分钟、5分钟、15分钟内的平均值，数值越小意味着负载越低）。
     * <p>
     * 第2行：进程总数、运行中的进程数、睡眠中的进程数、停止的进程数、僵死的进程数。
     * <p>
     * 第3行：用户占用资源百分比、系统内核占用资源百分比、改变过优先级的进程资源百分比、空闲的资源百分比等。其中数据均为CPU数据并以百分比格式显示，例如“97.1id”意味着有97.1%的CPU处理器资源处于空闲。
     * <p>
     * 第4行：物理内存总量、内存使用量、内存空闲量、作为内核缓存的内存量。
     * <p>
     * 第5行：虚拟内存总量、虚拟内存使用量、虚拟内存空闲量、已被提前加载的内存量。
     *
     * @param resultLines
     * @return
     */
    @Override public HashMap<String, Object> dealResult(List<String> resultLines) {
        HashMap<String, Object> map = Maps.newHashMap();

        CpuModel cpuModel = new CpuModel();
        cpuModel.buildCpuModel(resultLines.get(2));
        map.put("cpu", cpuModel);

        MemoryModel memoryModel = new MemoryModel();
        memoryModel.buildMemoryModel(resultLines.get(3));
        map.put("memory", memoryModel);

        ThreadModel threadModel = new ThreadModel();
        threadModel.buildThreadModel(resultLines.get(7));
        map.put("thread", threadModel);

        return map;
    }

}
