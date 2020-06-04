package rain.aclwithdb;

import com.github.bingoohuang.utils.servlet.RequestDumper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class AclInterceptor implements HandlerInterceptor {
    @Autowired private AclInterceptorCookieLogin cookieLogin;

    @Autowired private AclInterceptorAcl acl;

    private AclInterceptorSub[] subInterceptors;

    @PostConstruct
    public void init() {
        subInterceptors = new AclInterceptorSub[]{cookieLogin, acl};
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse rsp, Object handler) throws Exception {
        log.info("URL:{}", RequestDumper.getURL(req));

        for (val subInterceptor : subInterceptors) {
            val support = subInterceptor.support(req);
            log.debug("{} support {}", subInterceptor.getClass().getName(), support);
            if (!support) continue;

            switch (subInterceptor.intercept(req, rsp, handler)) {
                case ContinueNext:
                    continue;
                case BreakTrue:
                    return true;
                case BreakFalse:
                    return false;
            }
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        AclContext.teardown();
    }
}
