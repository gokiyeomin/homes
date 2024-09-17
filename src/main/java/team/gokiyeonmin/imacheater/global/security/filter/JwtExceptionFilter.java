package team.gokiyeonmin.imacheater.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import team.gokiyeonmin.imacheater.global.exception.BusinessException;
import team.gokiyeonmin.imacheater.global.exception.ErrorCode;
import team.gokiyeonmin.imacheater.global.exception.ErrorResponse;
import team.gokiyeonmin.imacheater.global.exception.TokenException;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (SecurityException e) {
            setErrorResponse(response, e, ErrorCode.ACCESS_DENIED);
        } catch (TokenException e) {
            setErrorResponse(response, e, e.getErrorCode());
        } catch (BusinessException e) {
            setErrorResponse(response, e, e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
            setErrorResponse(response, e, ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private void setErrorResponse(HttpServletResponse response, Exception e, ErrorCode errorCode) throws IOException {
        log.error("{}: {}, {}", e.getClass().getName(), errorCode.getCode(), errorCode.getMessage());
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(objectMapper.writeValueAsString(ErrorResponse.of(errorCode)));
    }
}
