package team.gokiyeonmin.imacheater.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("USER"),
    ADMIN("ADMIN");

    private final String name;

    public static Role fromName(String name) {
        return switch (name) {
            case "USER" -> USER;
            case "ADMIN" -> ADMIN;
            default -> throw new IllegalArgumentException("No such role: " + name);
        };
    }

    public Integer getPriority() {
        return switch (this) {
            case USER -> 0;
            case ADMIN -> 1;
        };
    }

    public String toGrantedAuthority() {
        return "ROLE_" + name;
    }
}
