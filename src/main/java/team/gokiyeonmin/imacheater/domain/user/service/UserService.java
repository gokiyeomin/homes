package team.gokiyeonmin.imacheater.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.gokiyeonmin.imacheater.domain.auth.event.SignInEvent;
import team.gokiyeonmin.imacheater.domain.auth.event.SignUpEvent;
import team.gokiyeonmin.imacheater.domain.user.dto.req.UserUpdateRequest;
import team.gokiyeonmin.imacheater.domain.user.dto.res.UserResponse;
import team.gokiyeonmin.imacheater.domain.user.entity.User;
import team.gokiyeonmin.imacheater.domain.user.repository.UserRepository;
import team.gokiyeonmin.imacheater.global.exception.BusinessException;
import team.gokiyeonmin.imacheater.global.exception.ErrorCode;

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
    public UserResponse getSimpleUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        return UserResponse.fromEntity(user);
    }

    @Transactional
    public UserResponse updateSimpleUser(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        user.update(request.nickname());

        return UserResponse.fromEntity(user);
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
