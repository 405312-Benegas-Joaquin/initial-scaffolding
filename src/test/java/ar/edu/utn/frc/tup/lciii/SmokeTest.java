package ar.edu.utn.frc.tup.lciii;

import ar.edu.utn.frc.tup.lciii.controllers.DummyController;
import ar.edu.utn.frc.tup.lciii.services.DummyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SmokeTest {
    // Controllers
    @Autowired
    private DummyController dummyController;

    // Services
    @Autowired
    private DummyService dummyService;

    // Test
    @Test
    public void contextLoads() throws Exception {
        // Controllers
        assertThat(dummyController).isNotNull();

        // Services
        assertThat(dummyService).isNotNull();
    }
}
