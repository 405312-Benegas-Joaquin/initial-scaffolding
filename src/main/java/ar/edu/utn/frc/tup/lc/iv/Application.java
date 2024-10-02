package ar.edu.utn.frc.tup.lc.iv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("Swagger UI: http://localhost:8080/swagger-ui.html");
        System.out.println("CircuitBreaker: http://localhost:8080/actuator/circuitbreakers");
        System.out.println("----------------------------------------------------------------------------------");
    }
}
