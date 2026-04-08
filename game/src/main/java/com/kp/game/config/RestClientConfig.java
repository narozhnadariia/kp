package com.kp.game.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
@Configuration
public class RestClientConfig {

    @Bean
    public RestTemplate restTemplate() {
        //щоб звертатися до REST API.
        return new RestTemplate();
    }
}
