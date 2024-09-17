package team.gokiyeonmin.imacheater.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.gokiyeonmin.imacheater.domain.auth.dto.req.SignUpRequest;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ApplicationEventPublisher publisher;

    @Transactional
    public void signUp(SignUpRequest request) {

        publisher.publishEvent(request.toEvent());
    }

}
