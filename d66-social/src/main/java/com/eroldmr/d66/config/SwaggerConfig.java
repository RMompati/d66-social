package com.eroldmr.d66.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Mompati 'Patco' Keetile
 */
@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig {

  @Bean
  public Docket d66SocialApi() {
    return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
            .title("D66 Social API")
            .version("0.0.1")
            .description("API for D66 Social Application.")
            .contact(new Contact("Mompati Keetile", "https://github.com/mr-erold", "mmpterold@gmail.com"))
            .license("Apache License Version 2.0")
            .build();
  }
}
