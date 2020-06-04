package rain.aclwithdb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class Menu {
    private String id;
    private String menuName;
    private String menuRoute;
    private String parentMenuId;
}
