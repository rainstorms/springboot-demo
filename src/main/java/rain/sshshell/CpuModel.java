package rain.sshshell;

import com.google.common.base.Splitter;
import lombok.Data;

import java.util.List;

@Data
// %Cpu(s):  1.9 us,  1.9 sy,  0.0 ni, 96.2 id,  0.0 wa,  0.0 hi,  0.0 si,  0.0 st
public class CpuModel {
    // 表示用户空间程序的cpu使用率（没有通过nice调度）
    private Float us;
    // 表示系统空间的cpu使用率，主要是内核程序。
    private Float sy;
    // 表示用户空间且通过nice调度过的程序的cpu使用率。
    private Float ni;
    // 空闲cpu
    private Float id;
    // cpu运行时在等待io的时间
    private Float wa;
    // cpu处理硬中断的数量
    private Float hi;
    // cpu处理软中断的数量
    private Float si;
    // 被虚拟机偷走的cpu
    private Float st;

    // cpu使用率
    private Float used;

    public void buildCpuModel(String resultLine) {
        List<String> result = Splitter.on(":").omitEmptyStrings().trimResults()
                .splitToList(resultLine);
        List<String> cpuInfos = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(result.get(1));

        for (String cpuInfo : cpuInfos) {
            convertAttribute(cpuInfo);
        }
    }

    private void convertAttribute(String cpuInfo) {
        List<String> cpuItems = Splitter.on(" ").omitEmptyStrings().trimResults().splitToList(cpuInfo);
        String key = cpuItems.get(1);
        float value = Float.parseFloat(cpuItems.get(0));
        switch (key) {
            case "us":
                us = value;
                break;
            case "sy":
                sy = value;
                break;
            case "ni":
                ni = value;
                break;
            case "id":
                id = value;
                used = 100 - value;
                break;
            case "wa":
                wa = value;
                break;
            case "hi":
                hi = value;
                break;
            case "si":
                si = value;
                break;
            case "st":
                st = value;
                break;
        }
    }
}
