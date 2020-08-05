package rain.file;

import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.csource.fastdfs.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

@Slf4j
@Component
/**
 * 使用 FastDFS 的准备工作
 * 1、添加 fast-dfs 依赖 serach.maven 里面下最新的
 *         <dependency>
 *             <groupId>org.csource</groupId>
 *             <artifactId>fastdfs-client-java</artifactId>
 *             <version>1.29-SNAPSHOT</version>
 *             <exclusions>
 *                 <exclusion>
 *                     <groupId>org.slf4j</groupId>
 *                     <artifactId>slf4j-log4j12</artifactId>
 *                 </exclusion>
 *             </exclusions>
 *         </dependency>
 * 2、在resources 下 添加 fdfs_client.conf
 *
 */
public class FastDFSClient {

    private StorageClient1 client;

    @PostConstruct
    public void init() {
        try {
            ClientGlobal.init("fdfs_client.conf");
            TrackerClient trackerClient = new TrackerClient();

            TrackerServer trackerServer = trackerClient.getTrackerServer();
            StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
            client = new StorageClient1(trackerServer, storageServer);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    /**
     * 文件上传
     *
     * @param fileName 文件名称
     * @return 上传成功返回fileId，失败返回null
     */
    public String upload(String fileName) {
        try {
            String suffix = getFileExtension(fileName);
            String fileId = client.upload_file1(fileName, suffix, null);
            log.info("field: {}", fileId);
            return fileId;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return null;
        }
    }

    public String getFileExtension(String fileName) {
        return Splitter.on(".").trimResults().omitEmptyStrings().splitToList(fileName).get(1);
    }

    /**
     * 文件下载
     *
     * @param destDir  要下载到的目录
     * @param fileId   要下载的文件id
     * @param fileName 下载后文件的名称
     * @return
     */
    public File downloadFile(String destDir, String fileId, String fileName) {
        //wKgCFF8VPh6ARuQxAACTPzoN2uc34.jpeg group1/M00/00/01/wKgCFF8VPh6ARuQxAACTPzoN2uc34.jpeg
        String imageFullPath = destDir + fileName;
        log.info("创建目标文件目录： {}", imageFullPath);
        File file = new File(imageFullPath);

        try {
            client.download_file1(fileId, imageFullPath);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return file;
    }
}
