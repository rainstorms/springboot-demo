package rain.sshshell;

import com.google.common.base.Splitter;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
// MiB Mem :  15756.9 total,  11112.5 free,   2309.2 used,   2335.2 buff/cache
public class MemoryModel {
    // 物理内存总量
    private Float total;
    // 内存空闲量
    private Float free;
    // 内存使用量
    private Float used;
    // 作为内核缓存的内存量
    private Float buff;

    private Float usedRate;

    public void buildMemoryModel(String resultLine) {
        List<String> result = Splitter.on(":").omitEmptyStrings().trimResults()
                .splitToList(resultLine);
        List<String> cpuInfos = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(result.get(1));

        for (String cpuInfo : cpuInfos) {
            convertAttribute(cpuInfo);
        }

        BigDecimal a = new BigDecimal(used);
        BigDecimal b = new BigDecimal(total);
        BigDecimal divide = a.divide(b, 2, BigDecimal.ROUND_HALF_UP);
        usedRate = divide.floatValue();
    }

    private void convertAttribute(String cpuInfo) {
        List<String> cpuItems = Splitter.on(" ").omitEmptyStrings().trimResults().splitToList(cpuInfo);
        String key = cpuItems.get(1);
        float value = Float.parseFloat(cpuItems.get(0));
        switch (key) {
            case "total":
                total = value;
                break;
            case "free":
                free = value;
                break;
            case "used":
                used = value;
                break;
            case "buff/cache":
                buff = value;
                break;
        }
    }
}
