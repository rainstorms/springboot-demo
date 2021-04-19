package rain.sshshell;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Executor {

    private final String ip;
    private final String userName;
    private final String password;

    public Executor(String ip, String userName, String password) {
        this.ip = ip;
        this.userName = userName;
        this.password = password;
    }

    public void execute(List<Commend> commends) {
        Session session = connect(userName, password, ip);
        if (null == session) return;

        for (Commend commend : commends) {
            List<String> result = remoteExecute(session, commend.getCommendLine());

            Object o = commend.dealResult(result);
            System.out.println(o);
        }

        session.disconnect();
    }

    /**
     * 连接到指定的HOST
     *
     * @return isConnect
     * @throws JSchException JSchException
     */
    private static Session connect(String user, String passwd, String host) {
        Session session;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, 22);
            session.setPassword(passwd);

            session.setConfig("StrictHostKeyChecking", "no");

            session.connect();
        } catch (JSchException e) {
            e.printStackTrace();
            System.out.println("连接失败 !" + user + "--" + passwd + "--" + host);
            return null;
        }
        return session;
    }

    public static List<String> remoteExecute(Session session, String command) {
        List<String> resultLines = new ArrayList<>();
        ChannelExec channel = null;
        try {
            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            InputStream input = channel.getInputStream();
            channel.connect(1000);
            try {
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(input));
                String inputLine = null;
                while ((inputLine = inputReader.readLine()) != null) {
                    resultLines.add(inputLine);
                }
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (Exception e) {
                    }
                }
            }
        } catch (IOException | JSchException e) {
        } finally {
            if (channel != null) {
                try {
                    channel.disconnect();
                } catch (Exception e) {
                }
            }
        }
        return resultLines;
    }


}
