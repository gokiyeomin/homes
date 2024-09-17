package team.gokiyeonmin.imacheater.global.security.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import team.gokiyeonmin.imacheater.domain.user.Role;
import team.gokiyeonmin.imacheater.global.jwt.JwtConstant;
import team.gokiyeonmin.imacheater.global.jwt.JwtUtil;
import team.gokiyeonmin.imacheater.global.security.domain.CustomUserDetails;
import team.gokiyeonmin.imacheater.global.security.domain.SecurityConstant;
import team.gokiyeonmin.imacheater.global.security.service.CustomUserDetailService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
        String token = jwtUtil.extractToken(authorizationHeader);
        Claims claims = jwtUtil.parse(token);

        String username = claims.get(JwtConstant.USERNAME, String.class);
        List<Role> roles = claims.get(JwtConstant.ROLES, List.class);

        CustomUserDetails customUserDetails = customUserDetailService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                customUserDetails,
                null,
                customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(final HttpServletRequest request) throws ServletException {
        return Arrays.stream(SecurityConstant.NO_NEED_AUTHENTICATION)
                .anyMatch(pattern -> antPathMatcher.match(pattern, request.getRequestURI()));
    }

    private String getAuthorizationHeader(final HttpServletRequest request) {
        return request.getHeader(JwtConstant.AUTHORIZATION);
    }
}
