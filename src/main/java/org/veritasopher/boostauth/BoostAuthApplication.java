package org.veritasopher.boostauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Entry Point
 *
 * @author Yepeng Ding
 */
@SpringBootApplication
@EnableJpaAuditing
public class BoostAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoostAuthApplication.class, args);
    }

}
