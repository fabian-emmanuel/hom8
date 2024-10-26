package org.indulge.hom8;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class Hom8ApplicationTests {

    @Test
    void contextLoads() {
    }

}
