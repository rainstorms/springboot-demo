package rain.file;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Slf4j
public class downloadFile {
    /**
     * 前端已新的链接方式打开请求 实现浏览器直接下载
     *
     * @param fileName 下载后的文件名称
     * @param path     源文件地址
     * @param response
     * @return
     */
    public HttpServletResponse download(String fileName, String path, HttpServletResponse response) {
        try (OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
             InputStream fis = new BufferedInputStream(new FileInputStream(path))) {

            // 以流的形式下载文件。
            int available = fis.available();
            byte[] buffer = new byte[available];
            fis.read(buffer);

            // 设置response的Header
            response.addHeader("content-disposition", "attachment;filename*=UTF-8''" + URLEncoder.encode(fileName, "UTF-8"));
            response.addHeader("Content-Length", "" + available);
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("UTF-8");
            toClient.write(buffer);
            toClient.flush();
        } catch (IOException e) {
            log.debug("下载压缩包失败，地址:{}", e.getMessage());
        }
        return response;
    }
}
