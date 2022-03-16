package pl.szczeliniak.kitchenassistant.swagger

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfiguration(
    @Value("\${application.name}")
    private val applicationName: String,
    @Value("\${application.description}")
    private val applicationDescription: String,
    @Value("\${application.version}")
    private val applicationVersion: String,
    @Value("\${application.creator.name}")
    private val applicationCreatorName: String,
    @Value("\${application.creator.url}")
    private val applicationCreatorUrl: String,
    @Value("\${application.creator.email}")
    private val applicationCreatorEmail: String
) {

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
            .securityContexts(listOf(securityContext()))
            .securitySchemes(listOf(apiKey()))
            .select()
            .apis(RequestHandlerSelectors.basePackage("pl.szczeliniak.kitchenassistant"))
            .paths(PathSelectors.any())
            .build()
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfo(
            applicationName,
            applicationDescription,
            applicationVersion,
            null,
            Contact(applicationCreatorName, applicationCreatorUrl, applicationCreatorEmail),
            null,
            null, emptyList()
        )
    }

    private fun apiKey(): ApiKey {
        return ApiKey("JWT", "Authorization", "header")
    }

    private fun securityContext(): SecurityContext? {
        return SecurityContext.builder().securityReferences(defaultAuth()).build()
    }

    private fun defaultAuth(): List<SecurityReference> {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes = arrayOfNulls<AuthorizationScope>(1)
        authorizationScopes[0] = authorizationScope
        return listOf(SecurityReference("JWT", authorizationScopes))
    }


}