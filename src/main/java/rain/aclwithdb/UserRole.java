package rain.aclwithdb;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class UserRole {
    private String id;
    private String userId;
    private String roleId;
}
