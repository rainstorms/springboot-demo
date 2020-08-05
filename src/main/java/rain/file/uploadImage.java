package rain.file;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import rain.util.ProcessUtil;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
public class uploadImage {

    @Autowired FastDFSClient fastDFSClient;

    @SneakyThrows
    public List<Map<String, String>> uploadImage(String base64Image, List<String> sizes) {
        // 将 base64的原图 转化成file 放在本机
        File file = saveImageFile("id", base64Image);
        String imageFullPath = file.getCanonicalPath();

        // 如果size为0 说明不需要裁图 只要上传原图
        if (sizes.size() == 0) sizes.add(null);

        List<Map<String, String>> results = new LinkedList<>();
        for (String size : sizes) {
            // 裁图
            String newFilePath = cropImage(imageFullPath, size);
            // 上传图
            String imageUrl = uploadImage(newFilePath);

            Map<String, String> result = new HashMap<>();
            result.put("imageUrl", imageUrl);
            result.put("size", size);
            results.add(result);
        }

        // 将存放在本机file的临时目录删掉
        String dir = StringUtils.substringBetween(imageFullPath, "/tmp/image/", "/");
        deleteTempImage("/tmp/image/" + dir);

        return results;
    }

    @SneakyThrows
    public File saveImageFile(String id, String base64) {
        String destDir = "/tmp/image/" + id + File.separator;
        String imageExtension = getImageExtension(base64);
        String imageName = id + "." + imageExtension;
        String imageFullPath = destDir + imageName;
        log.info("创建目标文件目录： {}", imageFullPath);
        File imageFile = new File(imageFullPath);

        String imageString = getImageContentString(base64);
        BufferedImage image = decodeToImage(imageString);

        ImageIO.write(image, imageExtension, imageFile);
        log.debug("保存图片成功，地址:{}", imageFile.getCanonicalPath());

        return imageFile;
    }

    public String getImageExtension(String base64) {
        return StringUtils.substringBetween(base64, "data:image/", ";base64");
    }

    public String getImageContentString(String base64) {
        if (base64 == null) return null;

        return base64.trim().replaceFirst("data[:]image[/]([a-z])+;base64,", "");
    }

    @SneakyThrows
    public BufferedImage decodeToImage(String imageString) {
        BufferedImage image;
        byte[] imageByte;
        BASE64Decoder decoder = new BASE64Decoder();
        imageByte = decoder.decodeBuffer(imageString);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
        image = ImageIO.read(bis);
        bis.close();
        return image;
    }

    public void deleteTempImage(String destFileName) {
        String[] cmd = {"rm", "-rf", destFileName};

        ProcessUtil.shell(cmd);
        log.debug("删除 目录文件,{}", destFileName);
    }


    public String cropImage(String destFileName, String size) {
        if (StringUtils.isBlank(size)) return destFileName;

        String newFilePath = destFileName + "." + size;
        String[] cmd = {"convert", destFileName, "-resize", size + "^"
                , "-gravity", "Center", "-crop", size + "+0+0", newFilePath};

        ProcessUtil.shell(cmd);
        log.debug("裁图完成,{}", newFilePath);
        return newFilePath;
    }

    public String uploadImage(String newFilePath) {
        return fastDFSClient.upload(newFilePath);
    }
}
