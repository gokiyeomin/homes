package team.gokiyeonmin.imacheater.global.swagger.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(
        name = "JWT Token",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfig {

    @Value("${server.host-url}")
    private String hostUrl;

    @Bean
    protected OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API 리스트")
                        .description("API 목록입니다.")
                )
                .servers(List.of(
                        new Server().url(hostUrl)
                ));
    }
}
