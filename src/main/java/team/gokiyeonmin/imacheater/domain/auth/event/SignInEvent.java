package team.gokiyeonmin.imacheater.domain.auth.event;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class SignInEvent {

    private final String username;
    private final String password;
}
