package team.gokiyeonmin.imacheater.domain.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Door {
    NORTH("NORTH"),
    TECHNO("TECHNO"),
    MAIN("MAIN"),
    SOUTH("SOUTH"),
    WEST("WEST");

    private final String name;


    public static Door fromName(String name) {
        return switch (name) {
            case "NORTH" -> NORTH;
            case "TECHNO" -> TECHNO;
            case "MAIN" -> MAIN;
            case "SOUTH" -> SOUTH;
            case "WEST" -> WEST;
            default -> throw new IllegalArgumentException("No such door: " + name);
        };
    }


    public String toGrantedDoor() {
        return "DOOR_" + name;
    }
}
