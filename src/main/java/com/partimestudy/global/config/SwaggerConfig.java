package com.partimestudy.global.config;

import com.partimestudy.global.annotation.ResolvedParam;
import com.partimestudy.global.jwt.SessionInfo;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    @Value("${spring.application.name}")
    private String projectName;


    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title(projectName)
                .version("v0.0.1")
                .description(projectName + "명세서");

        return new OpenAPI()
                .info(info)
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")));
    }

    @Bean
    public OperationCustomizer customizeOperations() {
        return (operation, handlerMethod) -> {
            if (handlerMethod != null) {
                List<Parameter> parameters = operation.getParameters();
                if (parameters != null) {
                    parameters.removeIf(parameter -> isIgnoredParameter(parameter, handlerMethod));
                }
            }
            return operation;
        };
    }

    // ResolvedParam 어노테이션이 붙었으면서, SessionInfo 클래스인 변수가 있다면 스웨거에서 표기하지 않는다.
    private boolean isIgnoredParameter(Parameter parameter, HandlerMethod handlerMethod) {
        return Arrays.stream(handlerMethod.getMethodParameters())
                .filter(methodParameter -> methodParameter.hasParameterAnnotation(ResolvedParam.class))
                .anyMatch(methodParameter ->
                    methodParameter.getParameterType().equals(SessionInfo.class)
                        && parameter.getName().equalsIgnoreCase(SessionInfo.class.getSimpleName())
                );
    }
}