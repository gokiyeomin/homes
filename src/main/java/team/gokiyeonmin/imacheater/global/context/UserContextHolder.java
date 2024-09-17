package team.gokiyeonmin.imacheater.global.context;

import lombok.extern.slf4j.Slf4j;
import team.gokiyeonmin.imacheater.domain.user.entity.User;

@Slf4j
public class UserContextHolder {
    private final static ThreadLocal<User> userStore = new ThreadLocal<>();

    public static void set(User user) {
        userStore.set(user);
    }

    public static User get() {
        return userStore.get();
    }

    public static void remove() {
        userStore.remove();
    }

    public static User getAndRemove() {
        User user = userStore.get();
        userStore.remove();
        return user;
    }
}
