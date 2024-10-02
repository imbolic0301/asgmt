package com.partimestudy.global.config;

import com.partimestudy.global.annotation.ResolvedParam;
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
            // ResolvedParam이 붙은 파라미터를 무시
            if (handlerMethod instanceof HandlerMethod) {
                List<Parameter> parameters = operation.getParameters();
                if (parameters != null) {
                    parameters.removeIf(parameter -> isIgnoredParameter(handlerMethod));
                }
            }
            return operation;
        };
    }

    // 추후 파라미터에 대한 값을 확인할 때 추가
//    private boolean isIgnoredParameter(Parameter parameter, HandlerMethod handlerMethod) {
    private boolean isIgnoredParameter(HandlerMethod handlerMethod) {
        return Arrays.stream(handlerMethod.getMethodParameters())
                .anyMatch(methodParameter -> methodParameter.hasParameterAnnotation(ResolvedParam.class));
    }
}