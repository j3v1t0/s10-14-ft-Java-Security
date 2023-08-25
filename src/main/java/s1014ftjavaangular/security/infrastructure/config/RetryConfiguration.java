package s1014ftjavaangular.security.infrastructure.config;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.Duration;

@Configuration
public class RetryConfiguration {
    @Bean(name = "myRetryConfig") // Usa el mismo nombre definido en YAML
    public Retry myRetryConfig() {
        RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(5)
                .waitDuration(Duration.ofMillis(2000))
                .retryExceptions(IOException.class, RuntimeException.class)
                .build();

        return Retry.of("myRetryConfig", retryConfig);
    }
}
