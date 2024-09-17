package team.gokiyeonmin.imacheater.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.gokiyeonmin.imacheater.domain.auth.event.SignInEvent;
import team.gokiyeonmin.imacheater.domain.auth.event.SignUpEvent;
import team.gokiyeonmin.imacheater.domain.user.dto.res.UserSimpleResponse;
import team.gokiyeonmin.imacheater.domain.user.entity.User;
import team.gokiyeonmin.imacheater.domain.user.repository.UserRepository;
import team.gokiyeonmin.imacheater.global.exception.BusinessException;
import team.gokiyeonmin.imacheater.global.exception.ErrorCode;
import team.gokiyeonmin.imacheater.global.security.domain.CustomUserDetail;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(SignUpEvent event) {
        checkDuplicateUser(event.getUsername(), event.getNickname());

        User user = event.toEntity();
        user.encodePassword(passwordEncoder);

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUser(SignInEvent event) {
        return userRepository.findByUsername(event.getUsername())
                .filter(user -> user.checkPassword(event.getPassword(), passwordEncoder))
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
    }

    @Transactional(readOnly = true)
    public UserSimpleResponse getSimpleUser(CustomUserDetail userDetail) {
        return UserSimpleResponse.fromUserDetail(userDetail);
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
