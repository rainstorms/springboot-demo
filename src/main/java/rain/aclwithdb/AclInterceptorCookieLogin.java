package rain.aclwithdb;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 查找cookie，自动登录。
 */
@Component
public class AclInterceptorCookieLogin implements AclInterceptorSub {

    @Autowired private UserService userService;

    @Override
    public AclSubResult intercept(HttpServletRequest req, HttpServletResponse rsp, Object handler) {
        val authc = AclCookies.readAuthc(req);
        if (authc != null) {
            val user = userService.findUserOptional(authc);
            if (user.isPresent()) AclContext.setUser(user.get());
            else AclCookies.clearAuthc(rsp);
        }

        return AclSubResult.ContinueNext;
    }
}
