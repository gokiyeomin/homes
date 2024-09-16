package team.gokiyeonmin.imacheater.global.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

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
}
