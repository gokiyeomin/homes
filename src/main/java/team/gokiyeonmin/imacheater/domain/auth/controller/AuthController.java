package team.gokiyeonmin.imacheater.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.gokiyeonmin.imacheater.domain.auth.dto.req.SignUpRequest;
import team.gokiyeonmin.imacheater.domain.auth.service.AuthService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/signup")
    public ResponseEntity<?> signUp(
            @Valid @RequestBody SignUpRequest request
    ) {
        authService.signUp(request);
        return ResponseEntity.created(URI.create("/auth/signup")).body(null);
    }
}
