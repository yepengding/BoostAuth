package org.veritasopher.boostauth.config;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.veritasopher.boostauth.core.dictionary.GroupStatus;
import org.veritasopher.boostauth.model.Group;
import org.veritasopher.boostauth.service.GroupService;

import javax.annotation.Resource;
import java.util.LinkedHashMap;


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

    @Resource
    private Gson gson;

    @Override
    public void run(String... args) {
        // Create the default group if not exist
        if (groupService.getById(1).isEmpty()) {
            Group group = new Group();
            group.setName("default");
            group.setDescription("Default Group");
            group.setPrivilege(gson.toJson(new LinkedHashMap<>()));
            group.setStatus(GroupStatus.NORMAL.getValue());
            groupService.create(group);
            logger.info("Automatically created default group (id: 1)");
        }

        logger.info("Welcome to use BoostAuth.");
    }
}
