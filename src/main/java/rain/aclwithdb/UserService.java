package rain.aclwithdb;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    public Optional<User> findUserOptional(String userId) {
//        User user = userDao.findUser(userId);
//        if (user == null || !user.isNormal()) return Optional.empty();

//        List<String> roleIds = queryUserRoleIds(userId);
//        user.setUserRoleIds(roleIds);
//        return Optional.of(user);
        return null;
    }



}
