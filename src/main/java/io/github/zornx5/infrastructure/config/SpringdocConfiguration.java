package io.github.zornx5.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring doc 自动配置
 *
 * @author zornx5
 */
@Configuration
public class SpringdocConfiguration {

    @Bean
    public OpenAPI baseApplicationInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("ZornX5 的 Spring Boot 模版项目")
                        .description("一个满足使用习惯的 Spring Boot 的 Gradle 快速构建模版项目")
                        .version("1.0.0")
                );
    }

}
