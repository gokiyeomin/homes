package team.gokiyeonmin.imacheater.global.security.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import team.gokiyeonmin.imacheater.domain.user.Role;
import team.gokiyeonmin.imacheater.global.security.domain.CustomUserDetail;
import team.gokiyeonmin.imacheater.global.security.domain.SecurityConstant;
import team.gokiyeonmin.imacheater.global.security.service.CustomUserDetailService;
import team.gokiyeonmin.imacheater.global.util.JwtUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final CustomUserDetailService customUserDetailService;
    private final JwtUtil jwtUtil;
    private final AntPathMatcher antPathMatcher;

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain
    ) throws ServletException, IOException {
        String authorizationHeader = getAuthorizationHeader(request);
        log.info("JwtAuthenticationFilter Authorization Header: {}", authorizationHeader);
        String token = jwtUtil.extractToken(authorizationHeader);
        Claims claims = jwtUtil.parse(token);

        String username = claims.get(JwtUtil.USERNAME, String.class);
        List<Role> roles = claims.get(JwtUtil.ROLES, List.class);

        CustomUserDetail customUserDetail = customUserDetailService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                customUserDetail,
                null,
                customUserDetail.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(final HttpServletRequest request) throws ServletException {
        return (request.getMethod().equals("GET") && request.getRequestURI().startsWith("/api/items"))
                || Arrays.stream(SecurityConstant.NO_NEED_AUTHENTICATION)
                .anyMatch(pattern -> antPathMatcher.match(pattern, request.getRequestURI()));
    }

    private String getAuthorizationHeader(final HttpServletRequest request) {
        return request.getHeader(JwtUtil.AUTHORIZATION);
    }
}
