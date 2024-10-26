package org.indulge.hom8;

import org.indulge.hom8.properties.Api;
import org.indulge.hom8.properties.JWT;
import org.indulge.hom8.properties.OTP;
import org.indulge.hom8.properties.Redis;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableR2dbcAuditing
@EnableR2dbcRepositories
@EnableTransactionManagement
@EnableConfigurationProperties(value = {JWT.class, Api.class, Redis.class, OTP.class})
public class Hom8Application {

    public static void main(String[] args) {
        SpringApplication.run(Hom8Application.class, args);
    }

}
