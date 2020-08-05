package rain.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * Created by zhangxs on 2018/7/15.
 */
@Slf4j
public class ProcessUtil {

    public static void shell(String[] cmd) {
        ProcessBuilder processBuilder = new ProcessBuilder(cmd);
        String commandLine = StringUtils.join(cmd, ' ');
        Process process;

        try {
            log.debug("start command line {}", commandLine);
            process = processBuilder.start();

            StreamGobbler errorGobbler = new StreamGobbler(commandLine, process.getErrorStream(), StreamGobbler.TYPE.STDERR);
            StreamGobbler outputGobbler = new StreamGobbler(commandLine, process.getInputStream(), StreamGobbler.TYPE.STDOUT);
            errorGobbler.start();
            outputGobbler.start();
            try {
                int r = process.waitFor();
                log.debug("complete command line,result is {}, {}", r == 0 ? "成功" : "失败", commandLine);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
