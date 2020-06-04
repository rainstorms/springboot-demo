package rain.aclwithdb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private String userId;          // 用户标识
    private String loginName;       // 登录账户
    private String password;        // 登录密码
    private String username;        // 用户姓名, 个人用户
    private String unitId;          // 用户所在单位标识
    private String departmentId;    // 用户所在部门标识
    private String mobile;          // 用户手机号码
    private String state;           // 状态, 正常/已冻结/已注销/(其他异常状态)
    private DateTime createTime;    // 创建时间
    private DateTime updateTime;    // 更新时间
    private String manageRegion;    // 管理区域

    private List<String> userRoleIds; // 员工角色
}
