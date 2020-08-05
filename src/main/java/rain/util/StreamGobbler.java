package rain.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by zhangxs on 2018/7/15.
 */

@Slf4j
public class StreamGobbler extends Thread {
    public enum TYPE {STDOUT, STDERR}

    private final static String separator = System.getProperty("line.separator");
    private InputStream is;
    private TYPE type;
    private String commandLine;
    private String output;

    public StreamGobbler(String commandLine, InputStream is, TYPE type) {
        this.commandLine = commandLine;
        this.is = is;
        this.type = type;
    }

    public String getOutput() {
        return output;
    }

    @Override
    public void run() {
        StringBuilder outputStr = new StringBuilder();
        try (InputStreamReader isr = new InputStreamReader(is);
             BufferedReader br = new BufferedReader(isr)) {

            String line;
            while ((line = br.readLine()) != null) {
                outputStr.append(line).append(separator);

                log.debug("{} info {}", commandLine, line);
            }

            output = outputStr.toString();
        } catch (IOException e) {
            log.error("{} ioexception {}", commandLine, e.getMessage());
        }
    }
}
