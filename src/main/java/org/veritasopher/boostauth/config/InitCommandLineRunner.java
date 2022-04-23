package org.veritasopher.boostauth.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


/**
 * Initialize CommandLineRunner
 *
 * @author Yepeng Ding
 */
@Component
public class InitCommandLineRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(InitCommandLineRunner.class);

    @Override
    public void run(String... args) throws Exception {
        logger.info("Welcome to use BoostAuth.");
    }
}
