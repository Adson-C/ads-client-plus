package com.ads.clien.plus.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    // variavel de authentication
    public static final String AUTHENTICATION = "authentication";
    public static final String PAYMENTINFO = "paymentInfo";
    public static final String SUBSCRIPTIONTYPE = "subscriptionType";
    public static final String USER = "user";
    public static final String USERTYPE = "userType";

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false).groupName("v1").select()
                .apis(RequestHandlerSelectors.basePackage("com.ads.clien.plus.controller"))
                .paths(PathSelectors.any()).build().securitySchemes(List.of(apiKey()))
                .securityContexts(List.of(securityContexts()))
                .apiInfo(apiInfo()).tags(this.authenticationTag(), this.paymentInfoTag()
                        , this.subscriptionTypeTag(), this.userTag(), this.userTypeTag());
    }

    // chaves
    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }
    // securityContexts
    private SecurityContext securityContexts() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }
    // defaultAuth
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference("JWT", authorizationScopes));
    }

    private ApiInfo apiInfo() {
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();

        return apiInfoBuilder
                .title("Adson Clien Plus")
                .description("Api para atender o client Adson Clien Plus")
                .version("1.0.0")
                .license("Adson JavaDeveloper")
                .build();
    }
    // tag authentication
    private Tag authenticationTag() {
        return new Tag(AUTHENTICATION, "Endpoints de autenticação e recuperação de conta");
    }
    private Tag paymentInfoTag() {
        return new Tag(PAYMENTINFO, "Endpoints de pagamento, ");
    }
    private Tag subscriptionTypeTag() {
        return new Tag(SUBSCRIPTIONTYPE, "Endpoints de assinaturas");
    }
    private Tag userTag() {
        return new Tag(USER, "Endpoints de gerenciamento de usuários");
    }

    private Tag userTypeTag() {
        return new Tag(USERTYPE, "Endpoints de tipos de usuário");
    }
}
