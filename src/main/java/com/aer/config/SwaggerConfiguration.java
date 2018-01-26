package com.aer.config;


import com.fasterxml.classmate.TypeResolver;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The Class SwaggerConfiguration.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    /** The type resolver. */
    @Autowired
    private TypeResolver typeResolver;

    /**
     * Docket factory.
     *
     * @return the docket
     */
    @Bean
    public Docket docketFactory() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(metadata())
                //.useDefaultResponseMessages(false)
                .select()
                //.apis(RequestHandlerSelectors.basePackage("com.aer.rest.UserController"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * Metadata.
     *
     * @return the api info
     */
    @Bean
    public ApiInfo metadata() {
        return new ApiInfoBuilder()
                .title("Role Based Access Control")
                .description("Sample of role based access control")
                .version("1.0")
                .build();
    }

    /**
     * Swagger ui config.
     *
     * @return the ui configuration
     */
    @Bean
    public UiConfiguration swaggerUiConfig() {
        return new UiConfiguration(null);
    }

}
