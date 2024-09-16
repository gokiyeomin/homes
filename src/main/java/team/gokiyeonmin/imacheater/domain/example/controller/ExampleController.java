package team.gokiyeonmin.imacheater.domain.example.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import team.gokiyeonmin.imacheater.domain.example.dto.req.ExampleRequest;
import team.gokiyeonmin.imacheater.domain.example.dto.res.ExampleResponse;

@RestController
public class ExampleController {

    @GetMapping("/api/example")
    public ResponseEntity<ExampleResponse> example(
            @Valid @ModelAttribute ExampleRequest request
    ) {
        return ResponseEntity.ok(new ExampleResponse("Hello, " + request.name() + "!," + request.age()));
    }


}
