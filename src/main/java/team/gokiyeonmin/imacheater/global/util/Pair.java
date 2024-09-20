package team.gokiyeonmin.imacheater.global.util;

public class Pair<T, U> {

    private final T first;
    private final U second;

    Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public static <T, U> Pair<T, U> of(T first, U second) {
        return new Pair<>(first, second);
    }

    public T first() {
        return this.first;
    }

    public U second() {
        return this.second;
    }
}
