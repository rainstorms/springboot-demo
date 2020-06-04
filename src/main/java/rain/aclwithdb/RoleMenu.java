package rain.aclwithdb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class RoleMenu {              // 角色下的菜单关系表
    private String id; // 主键ID
    private String roleId; // 角色ID
    private String menuName;         // 菜单
    private String menuId;           // 菜单ID
    private String parentMenuId;

}