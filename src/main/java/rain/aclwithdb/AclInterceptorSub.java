package rain.aclwithdb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AclInterceptorSub {
    default boolean support(HttpServletRequest req) {
        return true;
    }

    AclSubResult intercept(HttpServletRequest req, HttpServletResponse rsp, Object handler) throws Exception;
}
