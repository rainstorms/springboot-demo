package rain.log;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PlatformOperationLog {
    private String id;
    private String userId; //用户
    private String operation; //操作
    private String ip; //ip地址
    private String remark; // 备注（目前记录设备id）
    private DateTime createTime; //操作时间
}
