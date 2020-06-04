package rain.aclwithdb;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoleAndAuthorityService {

    @Autowired RoleAndAuthorityDao roleAndAuthorityDao;

    //    @WestCacheable(manager = "redis", keyer = "simple")
    public List<String> queryAnonymousCanAccessMenu() {
        return roleAndAuthorityDao.queryAnonymousCanAccessMenus();
    }

    //    @WestCacheable(manager = "redis", keyer = "simple")
    public Map<String, List<String>> getUrlRolesMap() {
        List<RoleRequest> allRoles = roleAndAuthorityDao.queryRequests();
        Map<String, List<RoleRequest>> collect = allRoles.stream().collect(Collectors.groupingBy(RoleRequest::getUrl));

        Map<String, List<String>> map = Maps.newHashMap();
        collect.forEach((url, roles) -> {
            List<String> roleNames = roles.stream().map(RoleRequest::getRoleId).collect(Collectors.toList());
            map.put(url, roleNames);
        });

        return map;
    }

}
