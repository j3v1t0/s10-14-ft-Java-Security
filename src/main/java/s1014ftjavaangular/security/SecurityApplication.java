package s1014ftjavaangular.security;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import s1014ftjavaangular.security.infrastructure.persistence.repository.AccountRepositoryAdapter;

import java.util.Map;

@SpringBootApplication
@EnableDiscoveryClient
public class SecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

/*    @Bean
    public EurekaClientConfigBean eurekaInstanceConfig(InetUtils inetUtils) {
        var config = new EurekaClientConfigBean();
        config.setServiceUrl(Map.of("defaultZone", "https://s10-14-ft-eurekaserver.azurewebsites.net/eureka"));

        return config;
    }*/
}
