package rain.file;

import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.csource.fastdfs.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

@Slf4j
@Component
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
     * <p>
     * //     * @param file 文件
     *
     * @return 上传成功返回id，失败返回null
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
