package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = {
        "creational",
        "behavioral",
        "service",
        "aop"
})
@EnableAspectJAutoProxy
public class AppConfig {
}