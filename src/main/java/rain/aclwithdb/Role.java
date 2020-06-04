package rain.aclwithdb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Role {                 // 角色表
    private String id;              // 角色ID
    private String roleName;        // 角色名称
    private String notifyLevel;     // 通知级别
    private DateTime createTime;    // 创建时间
}