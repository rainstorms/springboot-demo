package rain.sshshell;

import com.google.common.collect.Lists;

import java.util.List;

public class main {
    public static String ip = "10.88.254.222";
    public static String userName = "rain";
    public static String password = "123456";

    public static void main(String[] args) {
        List<Commend> commends = Lists.newArrayList();

        TopCommend topCommend = new TopCommend("-p 1501");
        commends.add(topCommend);

//        FreeCommend freeCommend = new FreeCommend();
//        commends.add(freeCommend);

        Executor executor = new Executor(ip, userName, password);
        executor.execute(commends);
    }
}
