package team.gokiyeonmin.imacheater.domain.auth.event;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import team.gokiyeonmin.imacheater.domain.user.entity.User;

@Getter
@Builder
@RequiredArgsConstructor
public class SignUpEvent {
    private final String username;
    private final String password;
    private final String nickname;
    private final String department;

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .department(department)
                .build();
    }
}
