package team.gokiyeonmin.imacheater.domain.auth.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import team.gokiyeonmin.imacheater.domain.user.Role;
import team.gokiyeonmin.imacheater.domain.user.entity.User;
import team.gokiyeonmin.imacheater.domain.user.entity.UserRole;
import team.gokiyeonmin.imacheater.domain.user.service.UserRoleService;
import team.gokiyeonmin.imacheater.domain.user.service.UserService;

@Slf4j
@Component
@RequiredArgsConstructor
public class SignUpEventListener {

    private final UserService userService;
    private final UserRoleService userRoleService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void signUp(SignUpEvent event) {
        User user = userService.createUser(event);
        UserRole userRole = userRoleService.createUserRole(user, Role.USER);

        user.addRole(userRole);
    }
}
