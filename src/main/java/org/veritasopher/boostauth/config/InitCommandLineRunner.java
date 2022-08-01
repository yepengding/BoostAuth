package org.veritasopher.boostauth.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.veritasopher.boostauth.core.dictionary.GroupStatus;
import org.veritasopher.boostauth.model.Group;
import org.veritasopher.boostauth.service.GroupService;

import javax.annotation.Resource;
import java.util.Optional;


/**
 * Initialize CommandLineRunner
 *
 * @author Yepeng Ding
 */
@Component
public class InitCommandLineRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(InitCommandLineRunner.class);

    @Resource
    private GroupService groupService;

    @Override
    public void run(String... args) {
        // Create the default group if not exist
        if (groupService.getById(1).isEmpty()) {
            Group group = new Group();
            group.setName("default");
            group.setDescription("Default Group");
            group.setStatus(GroupStatus.NORMAL.getValue());
            groupService.create(group);
            logger.info("Automatically created default group (id: 1)");
        }

        logger.info("Welcome to use BoostAuth.");
    }
}
