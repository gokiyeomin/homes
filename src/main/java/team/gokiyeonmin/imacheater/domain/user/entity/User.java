package team.gokiyeonmin.imacheater.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import team.gokiyeonmin.imacheater.domain.user.Role;
import team.gokiyeonmin.imacheater.global.exception.BusinessException;
import team.gokiyeonmin.imacheater.global.exception.ErrorCode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"nickname"},
                        name = "uk_nickname"
                ),
                @UniqueConstraint(
                        columnNames = {"username"},
                        name = "uk_username"
                )
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nickname", length = 50, unique = true)
    private String nickname;

    @Column(name = "username", length = 20, unique = true)
    private String username;

    @Column(name = "password", length = 70)
    private String password;

    @Column(name = "department", length = 50)
    private String department;

    /* -------------------------------------------- */
    /* -------------- Relation Column ------------- */
    /* -------------------------------------------- */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserRole> roles = new ArrayList<>();

    /* -------------------------------------------- */
    /* ----------------- Functions ---------------- */
    /* -------------------------------------------- */
    @Builder
    public User(
            String nickname,
            String username,
            String password,
            String department
    ) {
        this.nickname = nickname;
        this.username = username;
        this.password = password;
        this.department = department;
    }

    public void encodePassword(PasswordEncoder encoder) {
        this.password = encoder.encode(this.password);
    }

    public Boolean checkPassword(String plainPassword, PasswordEncoder encoder) {
        return encoder.matches(plainPassword, this.password);
    }

    public void addRole(UserRole role) {
        role.setUser(this);
        this.roles.add(role);
    }

    public Role getHighestRole() {
        return this.roles.stream()
                .map(UserRole::getRole)
                .max(Comparator.comparing(Role::getPriority))
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_ROLE));
    }
}
