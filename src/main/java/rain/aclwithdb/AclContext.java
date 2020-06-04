package rain.aclwithdb;

import com.github.bingoohuang.utils.lang.Collects;
import lombok.val;

import java.util.List;

public class AclContext {
    private static ThreadLocal<User> context = new ThreadLocal<>();


    /**
     * 获得当前登录用户。（没有登录时，返回null)
     *
     * @return FUser对象（未登录时返回null)
     */
    public static User getUser() {
        return context.get();
    }

    /**
     * 是否已经登录。
     *
     * @return true 是
     */
    public static boolean isLogined() {
        return context.get() != null;
    }

    /**
     * 当前登录用户是否拥有指定角色。
     *
     * @param roleIds 角色名称列表。
     * @return 有任一角色。
     */
    public static boolean hasAnyRoles(String... roleIds) {
        val user = context.get();
        if (user == null) return false;

        val userRoles = user.getUserRoleIds();
        return Collects.containsAnyOf(userRoles, roleIds);
    }

    public static void setUser(User user) {
        context.set(user);
    }

    public static void teardown() {
        context.set(null);
    }

    public static String getUnitId() {
        return getUser().getUnitId();
    }

    public static String getUserId() {
        return getUser().getUserId();
    }

    public static List<String> getRoleIds() {
        return getUser().getUserRoleIds();
    }
}
