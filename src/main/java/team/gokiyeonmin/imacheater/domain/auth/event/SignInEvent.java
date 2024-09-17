package team.gokiyeonmin.imacheater.domain.auth.event;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import team.gokiyeonmin.imacheater.domain.auth.dto.req.SignInRequest;

@Getter
@Builder
@RequiredArgsConstructor
public class SignInEvent {

    private final String username;
    private final String password;

    public static SignInEvent from(SignInRequest request) {
        return SignInEvent.builder()
                .username(request.username())
                .password(request.password())
                .build();
    }
}
