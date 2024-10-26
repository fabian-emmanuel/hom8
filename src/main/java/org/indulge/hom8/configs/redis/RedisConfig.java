package org.indulge.hom8.configs.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import lombok.RequiredArgsConstructor;
import org.indulge.hom8.hashes.Otp;
import org.indulge.hom8.properties.Redis;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {
    private final Redis redis;

    @Bean @Primary
    public ReactiveRedisConnectionFactory reactiveConnectionFactory() {
        RedisStandaloneConfiguration standaloneConfig = new RedisStandaloneConfiguration(redis.host(), redis.port());
        standaloneConfig.setDatabase(redis.database());

        LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder()
                .clientOptions(ClientOptions.builder()
                        .socketOptions(SocketOptions.builder()
                                .connectTimeout(Duration.ofMillis(5000))
                                .build())
                        .build())
                .build();

        return new LettuceConnectionFactory(standaloneConfig, lettuceClientConfiguration);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.findAndRegisterModules();
        return mapper;
    }


    private <T> ReactiveRedisTemplate<String, T> createRedisTemplate(Class<T> type) {
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<T> valueSerializer = new Jackson2JsonRedisSerializer<>(type);
        valueSerializer.setObjectMapper(objectMapper());
        RedisSerializationContext<String, T> context = RedisSerializationContext
                .<String, T>newSerializationContext()
                .key(keySerializer)
                .value(valueSerializer)
                .hashKey(keySerializer)
                .hashValue(valueSerializer)
                .build();

        return new ReactiveRedisTemplate<>(reactiveConnectionFactory(), context);
    }

    @Bean
    public ReactiveRedisTemplate<String, Otp> reactiveOtpRedisTemplate() {
        return createRedisTemplate(Otp.class);
    }

    @Bean
    public ReactiveHashOperations<String, String, Otp> reactiveOtpHashOperations() {
        return reactiveOtpRedisTemplate().opsForHash();
    }

}

