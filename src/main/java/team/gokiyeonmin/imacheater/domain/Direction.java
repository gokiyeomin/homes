package team.gokiyeonmin.imacheater.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Direction {
    NORTH("NORTH"),
    EAST("EAST"),
    SOUTH("SOUTH"),
    WEST("WEST");

    private final String name;

    public static Direction fromName(String name) {
        return switch (name) {
            case "NORTH" -> NORTH;
            case "EAST" -> EAST;
            case "SOUTH" -> SOUTH;
            case "WEST" -> WEST;
            default -> throw new IllegalArgumentException("No such direction: " + name);
        };
    }


    public String toGrantedDirection() {
        return "DIRECTION_" + name;
    }
}
