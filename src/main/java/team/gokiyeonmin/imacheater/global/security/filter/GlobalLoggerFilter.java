package team.gokiyeonmin.imacheater.global.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class GlobalLoggerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain
    ) throws ServletException, IOException {
        log.info("HTTP 요청이 들어왔습니다! ({} {} {})",
                request.getHeader("X-FORWARDED-FOR") != null ? request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr(),
                request.getMethod(),
                request.getRequestURI());

        request.setAttribute("REQUEST_START_TIME", System.currentTimeMillis());

        filterChain.doFilter(request, response);

        final Long requestStartTime = (Long) request.getAttribute("REQUEST_START_TIME");
        final Long requestEndTime = System.currentTimeMillis();

        log.info("HTTP 요청이 처리되었습니다! 처리 시간: {}ms. ({} {} {})",
                requestEndTime - requestStartTime,
                request.getHeader("X-FORWARDED-FOR") != null ? request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr(),
                request.getMethod(),
                request.getRequestURI());
    }
}
