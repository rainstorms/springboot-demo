package rain.sshshell;

import com.google.common.base.Splitter;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
// 进程号 USER      PR  NI    VIRT    RES    SHR    %CPU  %MEM     TIME+ COMMAND
//  1501 rain      20   0  849504  73048  45132 S   0.0   0.5   8:23.38 Xorg
public class ThreadModel {
    // 进程号
    private String pid;
    // 用户
    private String user;
    // 任务的动态调度优先级，取值范围是[-100,39]，值越小优先级越高。
    private String pr;
    // 任务的静态调度优先级，取值范围是[-20,19]，值越小优先级越高。这也基本符合nice值的特点，就是当nice值设定好了之后，除非我们用renice去改它，否则它是不变的。
    private String ni;
    //任务使用虚拟内存大小，单位是kb，包含代码、库以及交换出去的page等，没啥参考价值
    private String virt;
    //任务使用物理内存大小，单位是kb
    private String res;
    // 任务使用共享内存大小，单位是kb
    private String shr;
    private String s;
    private String cpu;
    private String mem;
    private String time;
    private String command;

    public void buildThreadModel(String resultLine) {
        List<String> result = Splitter.on(" ").omitEmptyStrings().trimResults()
                .splitToList(resultLine);

        pid = result.get(0);
        user = result.get(1);
        pr = result.get(2);
        ni = result.get(3);
        virt = result.get(4);
        res = result.get(5);
        shr = result.get(6);
        s = result.get(7);
        cpu = result.get(8);
        mem = result.get(9);
        time = result.get(10);
        command = result.get(11);
    }

}
