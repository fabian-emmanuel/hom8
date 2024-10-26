package org.indulge.hom8;

import org.springframework.boot.SpringApplication;

public class TestHom8Application {

    public static void main(String[] args) {
        SpringApplication.from(Hom8Application::main).with(TestcontainersConfiguration.class).run(args);
    }

}
