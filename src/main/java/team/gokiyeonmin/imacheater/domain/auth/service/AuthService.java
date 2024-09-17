package team.gokiyeonmin.imacheater.domain.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.gokiyeonmin.imacheater.domain.auth.dto.req.SignInRequest;
import team.gokiyeonmin.imacheater.domain.auth.dto.req.SignUpRequest;
import team.gokiyeonmin.imacheater.domain.auth.dto.res.JwtResponse;
import team.gokiyeonmin.imacheater.domain.auth.event.SignInEvent;
import team.gokiyeonmin.imacheater.domain.auth.event.SignUpEvent;
import team.gokiyeonmin.imacheater.domain.user.entity.User;
import team.gokiyeonmin.imacheater.domain.user.entity.UserRole;
import team.gokiyeonmin.imacheater.global.context.UserContextHolder;
import team.gokiyeonmin.imacheater.global.util.JwtUtil;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public void signUp(SignUpRequest request) {

        publisher.publishEvent(SignUpEvent.from(request));
    }

    @Transactional(readOnly = true)
    public JwtResponse signIn(SignInRequest request) {
        publisher.publishEvent(SignInEvent.from(request));

        User user = UserContextHolder.getAndRemove();
        return jwtUtil.issueToken(user.getUsername(), user.getRoles().stream().map(UserRole::getRole).toList());
    }

}
