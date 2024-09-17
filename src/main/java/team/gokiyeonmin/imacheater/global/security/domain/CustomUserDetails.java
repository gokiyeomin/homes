package team.gokiyeonmin.imacheater.global.security.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import team.gokiyeonmin.imacheater.domain.user.Role;
import team.gokiyeonmin.imacheater.domain.user.entity.User;
import team.gokiyeonmin.imacheater.domain.user.entity.UserRole;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    private final CustomUserDetailInfo customUserDetailInfo;

    public CustomUserDetails(User user) {
        this.customUserDetailInfo = new CustomUserDetailInfo(
                user.getUsername(),
                user.getNickname(),
                user.getPassword(),
                user.getDepartment(),
                user.getRoles().stream().map(UserRole::getRole).toList()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return customUserDetailInfo.roles.stream()
                .map(Role::toGrantedAuthority)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getPassword() {
        return customUserDetailInfo.password;
    }

    @Override
    public String getUsername() {
        return customUserDetailInfo.username;
    }

    private record CustomUserDetailInfo(
            String username,
            String nickname,
            String password,
            String department,
            List<Role> roles
    ) {

    }
}
