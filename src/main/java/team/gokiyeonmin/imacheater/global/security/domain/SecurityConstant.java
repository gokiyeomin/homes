package team.gokiyeonmin.imacheater.global.security.domain;

public class SecurityConstant {
    public static final String[] NO_NEED_AUTHENTICATION = {
            // swagger
            "/api-docs.html",
            "/api-docs/**",
            "/swagger-ui/**",
            "/v3/**",

            // h2
            "/h2/**",

            "/auth/**",
    };
}
