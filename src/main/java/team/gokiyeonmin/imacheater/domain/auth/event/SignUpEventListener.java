package team.gokiyeonmin.imacheater.domain.auth.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import team.gokiyeonmin.imacheater.domain.user.service.UserService;

@Slf4j
@Component
@RequiredArgsConstructor
public class SignUpEventListener {

    private final UserService userService;

    @EventListener
    public void createUser(SignUpEvent event) {
        System.out.println("event = " + event);
        userService.createUser(event);
    }
}
