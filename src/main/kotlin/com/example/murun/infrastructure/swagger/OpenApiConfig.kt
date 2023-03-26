package com.example.murun.infrastructure.swagger

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration
@EnableWebMvc
class OpenApiConfig {
    @Bean
    fun swaggerApi(): Docket = Docket(DocumentationType.OAS_30)
            .apiInfo(swaggerInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.example.murun.web"))
            .paths(PathSelectors.any())
            .build()
            .useDefaultResponseMessages(false)

    private fun swaggerInfo() = ApiInfoBuilder()
            .title("Murun App")
            .description("당신의 러닝 뮤직")
            .version("1.0.0")
            .build()
}
