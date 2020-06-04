package rain.aclwithdb;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 处理Acl注解权限。
 */
@Component
public class AclInterceptorAcl implements AclInterceptorSub {

    @Override
    public AclSubResult intercept(HttpServletRequest r, HttpServletResponse s, Object h) {
        String servletPath = r.getServletPath();
        if (servletPath.startsWith("/error"))
            return AclSubResult.ContinueNext;

        if (!(h instanceof HandlerMethod)) return AclSubResult.ContinueNext;

        val handlerMethod = (HandlerMethod) h;
        if (handlerMethod.getMethod().getDeclaringClass() == BasicErrorController.class)
            return AclSubResult.ContinueNext;

        if (isAnonymousCanAccess(servletPath)) return AclSubResult.BreakTrue;

        if (!AclContext.isLogined())
            return sendError(r, 419, "Login required for " + h);

        Set<String> canRoles = urlCanBeAccessRoles(servletPath);
        if (CollectionUtils.isEmpty(canRoles)
                || !AclContext.hasAnyRoles(canRoles.toArray(new String[0])))
            return sendError(r, 450, "无资源访问权限");

        return AclSubResult.ContinueNext;
    }

    private static AclSubResult sendError(HttpServletRequest req, int statusCode, String msg) {
        throw new AclException(msg, statusCode);
    }

    private static boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(header);
    }

    @Autowired RoleAndAuthorityService roleAndAuthorityService;


    private boolean isAnonymousCanAccess(String url) {
        List<String> anonymousCanAccessUrlList = roleAndAuthorityService.queryAnonymousCanAccessMenu();
        Set<String> anonymousCanAccessUrlSet = new HashSet<>(anonymousCanAccessUrlList);
        List<String> urls = getUrlSetContainUrls(url, anonymousCanAccessUrlSet);
        return !urls.isEmpty();
    }

    private Set<String> urlCanBeAccessRoles(String url) {
        Map<String, List<String>> urlRolesMap = roleAndAuthorityService.getUrlRolesMap();
        Set<String> urls = urlRolesMap.keySet();
        List<String> keys = getUrlSetContainUrls(url, urls);
        Set<String> allRoles = Sets.newHashSet();
        keys.forEach(s -> {
            List<String> roles = urlRolesMap.get(s);
            allRoles.addAll(roles);
        });

        return allRoles;
    }

    /**
     * 根据正则 找出当前访问的url 能够匹配上的后台的requestMapping
     */
    private List<String> getUrlSetContainUrls(String url, Set<String> urls) {
        List<String> list = Lists.newArrayList();

        for (String s : urls) {
            Pattern r = Pattern.compile("^" + s);
            Matcher m = r.matcher(url);
            if (m.find()) list.add(s);
        }

        return list;
    }
}
