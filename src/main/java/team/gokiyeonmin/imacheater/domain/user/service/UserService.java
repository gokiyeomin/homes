package team.gokiyeonmin.imacheater.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.gokiyeonmin.imacheater.domain.auth.event.SignUpEvent;
import team.gokiyeonmin.imacheater.domain.user.Role;
import team.gokiyeonmin.imacheater.domain.user.entity.User;
import team.gokiyeonmin.imacheater.domain.user.entity.UserRole;
import team.gokiyeonmin.imacheater.domain.user.repository.UserRepository;
import team.gokiyeonmin.imacheater.domain.user.repository.UserRoleRepository;
import team.gokiyeonmin.imacheater.global.exception.BusinessException;
import team.gokiyeonmin.imacheater.global.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(SignUpEvent event) {
        checkDuplicateUser(event.getUsername(), event.getNickname());

        User user = event.toEntity();
        user.encodePassword(passwordEncoder);

        user = userRepository.save(user);

        UserRole role = UserRole.builder()
                .role(Role.USER)
                .build();

        user.addRole(role);

        userRoleRepository.save(role);
    }

    private void checkDuplicateUser(String username, String nickname) {
        if (userRepository.existsByUsername(username)) {
            throw new BusinessException(ErrorCode.ALREADY_EXISTS_USERNAME);
        }
        if (userRepository.existsByNickname(nickname)) {
            throw new BusinessException(ErrorCode.ALREADY_EXISTS_NICKNAME);
        }
    }
}
