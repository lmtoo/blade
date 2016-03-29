package cn.accessbright.blade.repository;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // 开启spring-data-jpa的审计功能
public class RepositoryConfiguration {

}
